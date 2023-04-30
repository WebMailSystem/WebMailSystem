/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.entity.InboxId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author 정기석
 */
public interface InboxRepository extends JpaRepository<Inbox, InboxId>{
    
    List<Inbox> findByIdRepositoryNameAndSenderContains(String repositoryName,String sender);
    
    @Query("SELECT i FROM inbox i WHERE i.id.repositoryName = :repositoryName AND i.messageBody LIKE CONCAT('%','Subject:','%',:title,'%')")
    List<Inbox> findInboxByRepositoryNameAndMessageBodyContaining(@Param("repositoryName")String repositoryName,@Param("title") String title);
}
