/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.SignupForm;
import deu.cse.spring_webmail.model.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 회원가입과 로그인 처리
 * @author 정기석
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
 
    private final UserService userService;
    
    @GetMapping("/signup")
    public String signUp(){
        log.info("이동이 되는지만 확인");
        return "/sign_up";
    }
    
    @PostMapping("/sign_up.do")
    public String signUpDo(SignupForm user,RedirectAttributes rttr){
        log.info("SignDTO ={}",user.getUsername());
       boolean check = userService.check(user.getUsername());
       if(check == true){
//           rttr.addAttribute("msg", "아이디 중복입니다");
           return "redirect:/signup";
       }
       userService.signUp(user);
       return "redirect:/";
    }
}
