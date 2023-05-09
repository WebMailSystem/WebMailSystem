/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.RecyclebinDTO;
import deu.cse.spring_webmail.model.RecyclebinService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author 정기석
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class RecyclebinController {
    
    private final RecyclebinService recyclebinService;
    
    @GetMapping("/recyclebin")
    public String recyclebin(HttpServletRequest request,Model model){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userid");        
        List<RecyclebinDTO> lists = recyclebinService.findByRepositoryName(userId);
         model.addAttribute("lists",lists);
         model.addAttribute("count",lists.size());
        return "recyclebin";
    }
    @GetMapping("/recyclebin/delete/{recyclebinId}")
    public String deleteMail(@PathVariable("recyclebinId") Long recyclebinId){
        recyclebinService.deleteMail(recyclebinId);
        return "redirect:/recyclebin";
                
    }
    @GetMapping("/recyclebin/restore/{recyclebinId}")
    public String restoreMail(@PathVariable("recyclebinId") Long recyclebinId){
        recyclebinService.restoreMail(recyclebinId);
        return "redirect:/recyclebin";
    }
    @GetMapping("/recyclebin/{recyclebinId}")
    public String getMessage(@PathVariable("recyclebinId")Long recyclebinId,Model model,HttpServletRequest request){
        
        String msg = recyclebinService.getMessage(recyclebinId,request);
        model.addAttribute("msg", msg);
      
        return "/read_mail/recyclebin_show_message";
    }
}
