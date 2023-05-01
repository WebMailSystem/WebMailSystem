/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.InboxId;
import deu.cse.spring_webmail.entity.Recyclebin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 정기석
 */
public interface RecyclebinRepository extends JpaRepository<Recyclebin, InboxId>{
    
}
