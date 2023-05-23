/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Inbox;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author yool
 */
public interface SentRepository extends JpaRepository<Inbox, String>{    
    
    //Sender 조회
    List<Inbox> findBySender(@Param("sender") String sender);  
    
}