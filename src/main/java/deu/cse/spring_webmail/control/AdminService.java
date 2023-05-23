/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.repository.UsersRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 정민수
 */
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;    
    
    @Transactional
    public void changePassword (Long userId, String oldPassword, String newPassword) {
        
       Users user = usersRepository.findById(userId).get();             
       
       String changePassword = passwordEncoder.encode(newPassword);
       log.info("changePassword = {}",changePassword);
       user.changePassword(changePassword);       
    }
    
    public boolean passwordCheck (String userName, String password) {
        Users user = usersRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("사용자 정보 없음"));             
        return passwordEncoder.matches(password,user.getPassword());
    }
}
