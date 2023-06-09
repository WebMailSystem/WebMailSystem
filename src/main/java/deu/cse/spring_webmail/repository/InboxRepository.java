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
    
    void deleteByIdRepositoryName(String repositoryName);
    
    List<Inbox> findByIdRepositoryNameAndSenderContains(String repositoryName,String sender);
    
    List<Inbox> findByIdRepositoryName(String user);
    
    @Query("SELECT i FROM inbox i WHERE i.id.repositoryName = :repositoryName AND i.messageBody LIKE CONCAT('%','Subject:','%',:contents,'%')")
    List<Inbox> findInboxByRepositoryNameAndMessageBodyContaining(@Param("repositoryName")String repositoryName,@Param("contents") String contents);
    
    @Query("SELECT i FROM inbox i WHERE i.id.repositoryName = :repositoryName AND (i.messageBody LIKE CONCAT('%','Subject:','%',:keyword,'%') OR i.sender LIKE CONCAT('%',:keyword,'%'))")
    List<Inbox> findByRepositoryNameAndSenderContainsOrMessageBodyContaining(@Param("repositoryName")String repositoryName,@Param("keyword")String keyword);
       
    @Query("SELECT i FROM inbox i WHERE i.id.repositoryName = :repositoryName AND i.sender LIKE CONCAT(:sender,'%') AND i.messageBody LIKE CONCAT('%','Message-ID: ','%',:messageId,'%','Subject: ','%')")
    Inbox findByRepositoryNameAndSenderAndMessageBody(@Param("repositoryName")String repositoryName,@Param("sender")String sender,@Param("messageId")String messageId);
    
    @Query("SELECT i.favorite FROM inbox i where i.id.repositoryName = :repositoryName")
    boolean isFavorite(@Param("repositoryName")String repositoryName);
    
    List<Inbox> findByIdRepositoryNameAndFavorite(String repositoryName,boolean favorite);
}
