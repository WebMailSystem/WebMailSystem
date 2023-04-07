/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.SignupForm;
import deu.cse.spring_webmail.model.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
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
        return "/sign_up";
    }
    
    @PostMapping("/signup.do")
    public String signUpDo(@Valid SignupForm user,BindingResult result,RedirectAttributes rttr,Model model){
        
        if(result.hasErrors()){
            List<ObjectError> errors =result.getAllErrors();
            model.addAttribute("errors",errors);
            return "/sign_up";
        }
        log.info("SignDTO ={}",user.getUsername());
       boolean check = userService.check(user.getUsername());
       if(check == true){
           log.info("이거 실행은 되는거지?33");
//           rttr.addAttribute("msg", "아이디 중복입니다");
           return "redirect:/signup";
       }
       log.info("이거 실행은 되는거지?44");
       userService.signUp(user);
       return "redirect:/";
    }
}
