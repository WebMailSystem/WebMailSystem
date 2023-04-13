/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import lombok.Getter;

/**
 *
 * @author 정기석
 */
@Getter
public class SessionDTO {
    
    private Long id;
    private String username;
    private String pwdHash;
    private Role role;
    private String pwdAlgorithm ;
    private int useForwarding;
    private String forwardDestination ;  
    private int useAlias;
    private String alias ;
    
    
    public SessionDTO(Users user){
        this.id = user.getId();
       this.username = user.getUsername();
        this.pwdHash = user.getPwdHash();
        this.role = user.getRole();
        this.pwdAlgorithm = user.getPwdAlgorithm();
        this.useForwarding = user.getUseForwarding();
        this.useAlias = user.getUseAlias();
        this.alias = user.getAlias();
        this.forwardDestination = user.getForwardDestination();
    }
}
