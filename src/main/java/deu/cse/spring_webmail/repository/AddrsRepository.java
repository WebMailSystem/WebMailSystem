/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Addrs;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author yool
 */
public interface AddrsRepository extends JpaRepository<Addrs, Long>{    
    
    //로그인한 ID의 주소록 정보 조회
    List<Addrs> findByUsername(@Param("username") String username);  
    
}