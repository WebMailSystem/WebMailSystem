/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 정기석
 */
@Getter
@Builder
@Slf4j
public class OauthAttributes {
    
    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String email;   
    private String pwdHash;  
    
    public static OauthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes,String sha){
        Map<String, Object> kakaAccount = (Map<String, Object>) attributes.get("kakao_account");
        return OauthAttributes.builder()
                .email((String) kakaAccount.get("email"))
                .pwdHash(sha)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
             
    }
    
    public Users toEntity(){        
        this.email = email.substring(0,email.lastIndexOf("@"));
        return Users.builder()
                .username(email)
                .password("1234")
                .pwdHash(pwdHash)
                .pwdAlgorithm("SHA")
                .useForwarding(0)
                .forwardDestination(null)
                .useAlias(0)
                .alias(null)
                .role(Role.USER).build();
    }  
}
