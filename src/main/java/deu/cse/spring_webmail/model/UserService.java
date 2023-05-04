/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.SignupForm;
import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.repository.InboxRepository;
import deu.cse.spring_webmail.repository.RecyclebinRepository;
import deu.cse.spring_webmail.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService {
    
    private final UsersRepository usersRepository;
    private final InboxRepository inboxRepository;
    private final RecyclebinRepository recyclebinRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SHAPasswordAlgorithm passwordAlgorithm;           
    
    @Transactional
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
    @Transactional
    public void deleteUser(Long userId,String username){
        usersRepository.deleteById(userId);
        inboxRepository.deleteByIdRepositoryName(username);
        recyclebinRepository.deleteByinboxIdRepositoryName(username);                
    }
    @Transactional
    public void changePassword(Long userId,String password){
       Users user = usersRepository.findById(userId).get();
       String changePassword = passwordEncoder.encode(password);
       log.info("changePassword = {}",changePassword);
       user.changePassword(changePassword);
       
    }   
    public boolean passwordCheck(String username,String password){
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));             
        return passwordEncoder.matches(password,user.getPassword());
    }
    public boolean oauth2Check(String username){
        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));               
        return user.getPassword().equals("1234");
    }
    
    

}
