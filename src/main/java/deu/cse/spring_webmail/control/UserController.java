/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.SessionDTO;
import deu.cse.spring_webmail.dto.SignupForm;
import deu.cse.spring_webmail.model.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/mypage")
    public String passwordCheck(HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userid");
        boolean oauth2Check = false;
         oauth2Check = userService.oauth2Check(userId);
        if(oauth2Check == true){
            return "/mypage";
        }
        return "/password_check";
    }
    @PostMapping("/mypage")
    public String mypage(@RequestParam String password,HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userid");
        boolean check = false;        
        log.info("userid = {},password = {}",userId,password);
        check = userService.passwordCheck(userId, password);       
        if(check == true){
            return "/mypage";
        }
        model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
        return "/password_check";
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
//           rttr.addAttribute("msg", "아이디 중복입니다");
           return "redirect:/signup";
       }
       userService.signUp(user);
       return "redirect:/";
    }  
    @PostMapping("delete-user.do")
    public String deleteUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        SessionDTO user = (SessionDTO)session.getAttribute("user");
        log.info("id = {}",user.getId());              
        userService.deleteUser(user.getId());
        session.invalidate();
        return "redirect:/";
    }
}