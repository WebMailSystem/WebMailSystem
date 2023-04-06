/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.repository;

import deu.cse.spring_webmail.entity.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 정기석
 */
public interface UsersRepository extends JpaRepository<Users, Long>{
    
    Optional<Users> findByUsername(String username);
    
    boolean existsByUsername(String username);
    
}
