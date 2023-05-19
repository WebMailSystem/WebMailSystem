/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import deu.cse.spring_webmail.dto.OauthAttributes;
import deu.cse.spring_webmail.dto.SessionDTO;
import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.repository.UsersRepository;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;


/**
 *
 * @author 정기석
 */

@RequiredArgsConstructor
@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
    private final UsersRepository usersRepository;
    private final HttpSession session;
    private final HttpServletRequest request;
    private final SHAPasswordAlgorithm passwordAlgorithm;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       log.info("실행여부 확인");
       OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(userRequest);
       
       String registrationId = userRequest.getClientRegistration().getRegistrationId();
       String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
       
       String sha = passwordAlgorithm.createPwdHash();
       
        OauthAttributes attributes = OauthAttributes.ofKakao("id", oAuth2User.getAttributes(),sha);
        Users user = saveOrUpdate(attributes);
            session.setAttribute("user", new SessionDTO(user));            
            session.setAttribute("userid",user.getUsername());
            session.setAttribute("host",request.getSession().getAttribute("host"));
            session.setAttribute("password","1234");            
            session.setAttribute("access_token", userRequest.getAccessToken().getTokenValue());
        log.info("userinfo = {}",user.getUsername());
        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole())),
                attributes.getAttributes(), userNameAttributeName);
    }
    
    private Users saveOrUpdate(OauthAttributes attributes){
        String email = attributes.getEmail();
        email = email.substring(0,email.lastIndexOf("@"));
        Users user = usersRepository.findByUsername(email).orElse(attributes.toEntity());
        return usersRepository.save(user);
    }
    
}
