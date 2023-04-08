/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeUtility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 정기석
 */
@Getter
@Builder
@Slf4j
public class OauthAttributes {
    
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String email;
    
    public static OauthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes){
        Map<String, Object> kakaAccount = (Map<String, Object>) attributes.get("kakao_account");
        return OauthAttributes.builder()
                .email((String) kakaAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
             
    }
    
    public Users toEntity(){
        String pwdHash = createPwdHash();
        this.email = email.substring(0,email.lastIndexOf("@"));
        return Users.builder()
                .username(email)
                .pwdHash(pwdHash)
                .pwdAlgorithm("SHA")
                .useForwarding(0)
                .forwardDestination(null)
                .useAlias(0)
                .alias(null)
                .role(Role.USER).build();
    }
    
   private String createPwdHash(){
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
