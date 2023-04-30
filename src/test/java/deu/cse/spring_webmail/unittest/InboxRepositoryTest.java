/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.unittest;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.model.MessageFormatter;
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
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author 정기석
 */
@SpringBootTest
@Slf4j
public class InboxRepositoryTest {
    
    @Autowired InboxRepository repository;
    
    
    @Test
    void 인박스테스트() throws MessagingException{
//        List<Inbox> inboxs = repository.findBySenderContains("t");
        List<Inbox> inboxs = repository.findByIdRepositoryNameAndSenderContains("test","test");
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
         MessageFormatter formatter = new MessageFormatter("test");  //3.5
         String result = formatter.getMessageTable(messages);
         log.info("result = {} ",result);
    }
    
    
}
