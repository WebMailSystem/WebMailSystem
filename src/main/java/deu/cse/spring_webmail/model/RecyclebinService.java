/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.entity.Recyclebin;
import deu.cse.spring_webmail.repository.InboxRepository;
import deu.cse.spring_webmail.repository.RecyclebinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 정기석
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RecyclebinService {
    private final InboxRepository inboxRepository;
    private final RecyclebinRepository recyclebinRepository;
    
    @Transactional
    public void moveInboxToRecyclebin(String repositoryName, String sender, String subject){
        Inbox inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, subject);
         log.info("inbox info ={}",inbox.getId().getMessageName());
        Recyclebin recyclebin = Recyclebin.builder().id(inbox.getId())
                .lastUpdated(inbox.getLastUpdated())
                .errorMessage(inbox.getErrorMessage())
                .messageAttributes(inbox.getMessageAttributes())
                .messageBody(inbox.getMessageBody())
                .messageState(inbox.getMessageState())
                .sender(inbox.getSender())
                .recipients(inbox.getRecipients())
                .remoteAddr(inbox.getRemoteAddr())
                .remoteHost(inbox.getRemoteHost()).build();
        recyclebinRepository.save(recyclebin);        
    }
}
