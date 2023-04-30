/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.entity.InboxId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 정기석
 */
public interface InboxRepository extends JpaRepository<Inbox, InboxId>{
    
    List<Inbox> findByIdRepositoryNameAndSenderContains(String repositoryName,String sender);
}
