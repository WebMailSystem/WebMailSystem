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

/**
 *
 * @author 정기석
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final InboxRepository inboxRepository;
    
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
        }else{
            return inboxRepository.findInboxByRepositoryNameAndMessageBodyContaining(username,keyword);
        }
    }
}
