/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.SpamDTO;
import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.entity.Spam;
import deu.cse.spring_webmail.repository.InboxRepository;
import deu.cse.spring_webmail.repository.SpamRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author yool
 * 정기석 학우의 코드를 재사용 하였습니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SpamService {

    private final InboxRepository inboxRepository;
    private final SpamRepository spamRepository;

    @Transactional
    public void moveInboxToSpam(String repositoryName, String sender, String subject, String[] messagdId, String date) {
        String id = messagdId[0];
        Inbox inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, id);
        log.info("inbox info ={}", inbox.getId().getMessageName());
        Spam spam = Spam.builder().inboxId(inbox.getId())
                .lastUpdated(inbox.getLastUpdated())
                .errorMessage(inbox.getErrorMessage())
                .messageAttributes(inbox.getMessageAttributes())
                .messageBody(inbox.getMessageBody())
                .messageState(inbox.getMessageState())
                .sender(inbox.getSender())
                .recipients(inbox.getRecipients())
                .remoteAddr(inbox.getRemoteAddr())
                .remoteHost(inbox.getRemoteHost())
                .date(date)
                .title(subject).build();
        spamRepository.save(spam);
    }

    public List<SpamDTO> findByRepositoryName(String userId) {
        List<Spam> lists = spamRepository.findByRepositoryName(userId);
        return lists.stream().map(spam -> entityToDto(spam)).collect(Collectors.toList());
    }

    private SpamDTO entityToDto(Spam spam) {
        return SpamDTO.builder().id(spam.getId())
                .date(spam.getDate())
                .sender(spam.getSender())
                .title(spam.getTitle()).build();
    }

    @Transactional
    public void deleteMail(Long spamId) {
        spamRepository.deleteById(spamId);
    }

    @Transactional
    public void restoreMail(Long spamId) {
        Spam spam = spamRepository.findById(spamId).get();
        Inbox inbox = Inbox.builder().id(spam.getInboxId())
                .errorMessage(spam.getErrorMessage())
                .lastUpdated(spam.getLastUpdated())
                .messageAttributes(spam.getMessageAttributes())
                .messageBody(spam.getMessageBody())
                .messageState(spam.getMessageState())
                .recipients(spam.getRecipients())
                .remoteAddr(spam.getRemoteAddr())
                .remoteHost(spam.getRemoteHost())
                .sender(spam.getSender()).build();
        inboxRepository.save(inbox);
        spamRepository.delete(spam);
    }

    public String getMessage(Long spamId, HttpServletRequest request) {
        Spam spam = spamRepository.findById(spamId).get();
        InputStream inputStream = new ByteArrayInputStream(spam.getMessageBody());
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), inputStream);
        } catch (MessagingException ex) {
            log.error("getMessage error!");
        }
        MessageFormatter formatter = new MessageFormatter(spam.getInboxId().getRepositoryName());  //3.5
        formatter.setRequest(request);
        return formatter.getMessage(mimeMessage);
    }

}
