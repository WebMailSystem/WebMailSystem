/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.entity;

import com.sun.istack.NotNull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

/**
 *
 * @author 정기석
 */
@Entity(name = "users")
@Getter
@Builder
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class Users {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(nullable = false,unique = true,name = "username")
    private String username;
    
    @Column(name = "pwdHash")
    private String pwdHash;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "pwdAlgorithm")
    private String pwdAlgorithm ;
    
    @Column(name = "useForwarding")
    private int useForwarding;
    
    @Column(name = "forwardDestination")
    private String forwardDestination ;
    
    @Column(name = "useAlias")
    private int useAlias;
    
    @Column(name = "alias")
    private String alias ;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role ;
    
}
