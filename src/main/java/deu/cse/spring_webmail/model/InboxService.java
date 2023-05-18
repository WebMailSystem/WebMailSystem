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
    
    public Inbox findById(){
        return null;
    }
    
    public String search(String username,String type,String keyword) throws MessagingException{
        
        List<Inbox> inboxs = checkSearchType(username, type, keyword);
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
         MessageFormatter formatter = new MessageFormatter(username);  //3.5
         return formatter.getMessageTable(messages);                
    }
    private List<Inbox> checkSearchType(String username,String type,String keyword){
        if(type.equals("sender")){
            return inboxRepository.findByIdRepositoryNameAndSenderContains(username,keyword);
        }else if(type.equals("contents")){
            return inboxRepository.findInboxByRepositoryNameAndMessageBodyContaining(username,keyword);
        }else{
            return inboxRepository.findByRepositoryNameAndSenderContainsOrMessageBodyContaining(username, keyword);
        }
    }
    
    public String favorites(String username) throws MessagingException{
        var inboxs = inboxRepository.findByIdRepositoryNameAndFavorite(username, true);
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
         MessageFormatter formatter = new MessageFormatter(username);  //3.5
         return formatter.getFavoriteMessageTable(messages);                        
    }
    @Transactional
    public void addFavorite(String repositoryName, String sender, String subject,String[] messagdId){
       String id = messagdId[0];
       Inbox inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, id);
       inbox.addFavorite();
    }
    @Transactional
    public void deleteFavorite(String repositoryName, String sender, String subject,String[] messagdId){
       String id = messagdId[0];
       Inbox inbox = inboxRepository.findByRepositoryNameAndSenderAndMessageBody(repositoryName, sender, id);
       inbox.deleteFavorite();
    }
}
