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
    private final SHAPasswordAlgorithm passwordAlgorithm;           
    
    public void signUp(SignupForm userDTO){
        String password = passwordEncoder.encode(userDTO.getPassword());
        String pwdHash = passwordAlgorithm.createPwdHash();
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
     
}
