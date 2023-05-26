/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Spam;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author yool
 * 정기석 학우의 코드를 재사용 하였습니다.
 */
public interface SpamRepository extends JpaRepository<Spam, Long>{

    @Query("SELECT s FROM spam s WHERE s.inboxId.repositoryName = :repositoryName")
    List<Spam> findByRepositoryName(String repositoryName);

    void deleteByinboxIdRepositoryName(String repositoryName);
    
     List<Spam> findByRecipients(@Param("recipients")String recipients);
    
}
