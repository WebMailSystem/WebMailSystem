/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.InboxId;
import deu.cse.spring_webmail.entity.Recyclebin;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author 정기석
 */
public interface RecyclebinRepository extends JpaRepository<Recyclebin, Long>{
    
    @Query("SELECT r FROM recyclebin r WHERE r.inboxId.repositoryName = :repositoryName")
    List<Recyclebin> findByRepositoryName(String repositoryName);
    
    void deleteByinboxIdRepositoryName(String repositoryName);
}
