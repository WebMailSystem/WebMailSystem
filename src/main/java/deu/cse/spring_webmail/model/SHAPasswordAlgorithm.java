/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 정기석
 */
@Slf4j
public class SHAPasswordAlgorithm {
    
    public String createPwdHash(){
        String result = "";
        try {
            result = digestString("1234","SHA");
            return result;
        } catch (NoSuchAlgorithmException ex) {
            log.error("exception = {}",ex);
        } catch (UnsupportedEncodingException ex) {
            log.error("exception = {}",ex);         
        }
        return result;
    }
    
    private String digestString(String pass, String algorithm ) throws NoSuchAlgorithmException, UnsupportedEncodingException  {

        MessageDigest md;
        ByteArrayOutputStream bos;

        try {
            md = MessageDigest.getInstance(algorithm);
            log.info("md = {}",md.toString());
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
