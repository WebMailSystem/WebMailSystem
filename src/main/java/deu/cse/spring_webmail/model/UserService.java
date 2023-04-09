/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.SignupForm;
import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.repository.UsersRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 정기석
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    
    public void signUp(SignupForm userDTO){
        String password = passwordEncoder.encode(userDTO.getPassword());
        String pwdHash = createPwdHash();
        Users user= Users.builder().username(userDTO.getUsername())
                .pwdHash(pwdHash)
                .password(password)
                .pwdAlgorithm("SHA")
                .useForwarding(0)
                .forwardDestination(null)
                .useAlias(0)
                .alias(null)
                .role(Role.USER).build();
        usersRepository.save(user);
    }
    public boolean check(String username){
        return usersRepository.existsByUsername(username);
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
    public boolean passwordCheck(String username,String password){
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));             
        return passwordEncoder.matches(password,user.getPassword());
    }
    public boolean oauth2Check(String username){
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));       
        if(user.getPassword().equals("1234")){
            return true;
        } 
        return false;
    }

    
    
}
