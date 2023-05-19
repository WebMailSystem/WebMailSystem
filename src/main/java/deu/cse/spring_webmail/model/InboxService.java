/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.repository.InboxRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 정기석
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InboxService {
    private final InboxRepository inboxRepository;
        
    
    public String search(String userName,String type,String keyword) throws MessagingException{
        
        List<Inbox> inboxs = checkSearchType(userName, type, keyword);
        List<Message> messageList = new ArrayList<>();
        for(Inbox inbox : inboxs){
            log.info("inbox sender = {}",inbox.getSender());
            InputStream inputStream = new ByteArrayInputStream(inbox.getMessageBody());
            MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), inputStream);
            messageList.add(mimeMessage);
        }
        Message[] messages = new Message[messageList.size()];
        int size = 0;
        for(Message temp : messageList){
            messages[size++] = temp;
        }
         MessageFormatter formatter = new MessageFormatter(userName);  //3.5
         return formatter.getMessageTable(messages);                
    }
    private List<Inbox> checkSearchType(String userName,String type,String keyword){
        if(type.equals("sender")){
            return inboxRepository.findByIdRepositoryNameAndSenderContains(userName,keyword);
        }else if(type.equals("contents")){
            return inboxRepository.findInboxByRepositoryNameAndMessageBodyContaining(userName,keyword);
        }else{
            return inboxRepository.findByRepositoryNameAndSenderContainsOrMessageBodyContaining(userName, keyword);
        }
    }
    
    public String favorites(String userName) throws MessagingException{
        var inboxs = inboxRepository.findByIdRepositoryNameAndFavorite(userName, true);
        List<Message> messageList = new ArrayList<>();
          for(Inbox inbox : inboxs){
            log.info("inbox sender = {}",inbox.getSender());
            InputStream inputStream = new ByteArrayInputStream(inbox.getMessageBody());
            MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()), inputStream);
            messageList.add(mimeMessage);
        }
        Message[] messages = new Message[messageList.size()];
        int size = 0;
        for(Message temp : messageList){
            messages[size++] = temp;
        }
         MessageFormatter formatter = new MessageFormatter(userName);  //3.5
         return formatter.getFavoriteMessageTable(messages);                        
    }
    @Transactional
    public void addFavorite(String repositoryName, String sender, String[] messagdId){       
       var inbox = findInbox(repositoryName, sender,messagdId);        
       log.info("변경 전 inbox = {}",inbox.isFavorite());
       inbox.addFavorite();
       log.info("변경 후 inbox = {}",inbox.isFavorite());
    }
    @Transactional
    public void deleteFavorite(String repositoryName, String sender,String messageId){
        log.info("messageId = {}",messageId);
       var inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, messageId);
       log.info("변경 전 inbox = {}",inbox.isFavorite());
       inbox.deleteFavorite();
       log.info("변경 후 inbox = {}",inbox.isFavorite());
    }
    private Inbox findInbox(String repositoryName, String sender, String[] messageId){
       String id = messageId[0];
       log.info("@@id = {}",id);
       return inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, id);
    }
    public Message getMessage(String repositoryName,String messageId,String sender) throws MessagingException{
        var inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, messageId);
          InputStream inputStream = new ByteArrayInputStream(inbox.getMessageBody());
          return new MimeMessage(Session.getDefaultInstance(new Properties()), inputStream);                
    }
}
