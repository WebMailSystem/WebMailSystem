/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.unittest;
import deu.cse.spring_webmail.model.SHAPasswordAlgorithm;
import deu.cse.spring_webmail.repository.AddrsRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 *
 * @author 정기석
 */
@Slf4j
@SpringBootTest
public class SHAAlgorithmTest {
       
    @Autowired SHAPasswordAlgorithm SHAPasswordAlgorithm;
    @Autowired AddrsRepository AddrsRepository;
    
    @Test
    void sha(){
        String a = SHAPasswordAlgorithm.createPwdHash();
        log.info("a = {}",a);
    }
    
    @Test
    void 이메일자르기테스트(){
        String email = "abce@naver.com";
        String result = email.substring(0,email.lastIndexOf("@"));
        log.info("result = {}",result);
    }
    
    
    @Test
    void shaTest(){
        try {
            //given
            String password = "admin";
            String algorithem = "SHA";
            //when
            String result = digestString(password, algorithem);
             //then
            log.info("result = {}",result);
        } catch (NoSuchAlgorithmException ex) {
            log.error("exception = {}",ex);
                
        } catch (UnsupportedEncodingException ex) {
            log.error("exception = {}",ex);
        }
       
    }
    private String digestString(String pass, String algorithm ) throws NoSuchAlgorithmException, UnsupportedEncodingException  {

        MessageDigest md;
        ByteArrayOutputStream bos;

        try {
            md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(pass.getBytes("iso-8859-1"));
            bos = new ByteArrayOutputStream();
            OutputStream encodedStream = MimeUtility.encode(bos, "base64");
            encodedStream.write(digest);
            return bos.toString("iso-8859-1");
        } catch (IOException ioe) {
            throw new RuntimeException("Fatal error: " + ioe);
        } catch (MessagingException me) {
            throw new RuntimeException("Fatal error: " + me);
        }
    }
}
