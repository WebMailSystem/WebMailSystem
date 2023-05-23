/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package deu.cse.spring_webmail.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 *
 * 로그인이 성공적으로 되었을 경우, 사용자가 admin인지 일반 user인지 확인하여 알맞은 view를 보여주는 역할
 * 
 * @author 정민수
 */
@Slf4j
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("잘 넘어옴");
        log.info(request.getParameter("username"));
        
        String check = request.getParameter("username"); // 관리자인지 아닌지 체크하기 위한 변수
        
        if(check.equals("admin"))
            response.sendRedirect("admin_menu");
        else        
            response.sendRedirect("main_menu");
    }
    
}
