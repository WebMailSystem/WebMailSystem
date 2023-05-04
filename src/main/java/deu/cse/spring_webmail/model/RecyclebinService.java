/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.RecyclebinDTO;
import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.entity.Recyclebin;
import deu.cse.spring_webmail.repository.InboxRepository;
import deu.cse.spring_webmail.repository.RecyclebinRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    public void moveInboxToRecyclebin(String repositoryName, String sender, String subject,String body, String date){
        Inbox inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, subject, body);
         log.info("inbox info ={}",inbox.getId().getMessageName());
        Recyclebin recyclebin = Recyclebin.builder().inboxId(inbox.getId())
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
        recyclebinRepository.save(recyclebin);        
    }
    
    public List<RecyclebinDTO> findByRepositoryName(String userId){
        List<Recyclebin> lists =recyclebinRepository.findByRepositoryName(userId);
        return lists.stream().map(recyclebin -> entityToDto(recyclebin)).collect(Collectors.toList());        
    }
    
    private RecyclebinDTO entityToDto(Recyclebin recyclebin){
        return RecyclebinDTO.builder().id(recyclebin.getId())
                .date(recyclebin.getDate())
                .sender(recyclebin.getSender())
                .title(recyclebin.getTitle()).build();       
    }
    @Transactional
    public void deleteMail(Long recyclebinId){
        recyclebinRepository.deleteById(recyclebinId);
    }
    
}
