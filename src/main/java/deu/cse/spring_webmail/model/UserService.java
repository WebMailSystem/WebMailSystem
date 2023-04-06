/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.SignupDTO;
import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.repository.UsersRepository;
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
    
    public void signUp(SignupDTO userDTO){
        String password = passwordEncoder.encode(userDTO.getPassword());
        Users user= Users.builder().username(userDTO.getUsername())
                .pwdHash(password)
                .pwdAlgorithm("BCrypt")
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
