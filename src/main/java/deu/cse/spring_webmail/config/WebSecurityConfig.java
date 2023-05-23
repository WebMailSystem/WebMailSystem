/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.config;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;
import deu.cse.spring_webmail.model.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 *
 * @author 정기석
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    
    private final AuthenticationFailureHandler customFailureHandler;
    private final CustomOAuth2UserService customUserService;
           
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();        
        http.authorizeRequests().antMatchers("/","/css/**","/signup","/signup.do","/error").permitAll()
                .antMatchers("/admin_menu").hasAnyRole("ADMIN")
                .anyRequest().authenticated()                              
                .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/")
                .loginProcessingUrl("/login.do")                        
                .failureHandler(customFailureHandler)                
                .successHandler(new CustomSuccessHandler())                
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling().accessDeniedPage("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()                
                .userService(customUserService);                        
                return http.build();
                
    }

    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }
}
