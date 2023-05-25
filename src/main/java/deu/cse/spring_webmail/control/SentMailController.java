/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.entity.Inbox;
import deu.cse.spring_webmail.repository.SentRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

/**
 *
 * @author yool
 */

@Controller
@Slf4j
@RequiredArgsConstructor
public class SentMailController {

    @Autowired
    SentRepository repository;

    @GetMapping("/sent_mail")
    public String sentMail(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String sender = (String) session.getAttribute("userid");
        sender = sender.concat("@localhost");

        List<Inbox> dataRows = repository.findBySender(sender);      
        
        System.out.println(dataRows.get(0).getId());
        model.addAttribute("dataRows", dataRows);
         
        return "sent_mail/sent_mail";
    }
}