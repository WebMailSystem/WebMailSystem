/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.SpamDTO;
import deu.cse.spring_webmail.entity.Spam;
import deu.cse.spring_webmail.model.Pop3Agent;
import deu.cse.spring_webmail.model.SpamService;
import deu.cse.spring_webmail.repository.SpamRepository;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author yool 정기석 학우의 코드를 재사용 하였습니다.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class SpamController {

    @Autowired
    SpamRepository repository;
    private final SpamService spamService;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;

    @GetMapping("/spam")
    public String spam(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userid");
        List<SpamDTO> lists = spamService.findByRepositoryName(userId);
        model.addAttribute("lists", lists);
        model.addAttribute("count", lists.size());
        return "spam";
    }

    @GetMapping("/spam/delete/{spamId}")
    public String deleteMail(@PathVariable("spamId") Long spamId) {
        spamService.deleteMail(spamId);
        return "redirect:/spam";

    }

    @GetMapping("/spam/restore/{spamId}")
    public String restoreMail(@PathVariable("spamId") Long spamId) {
        spamService.restoreMail(spamId);
        return "redirect:/spam";
    }

    @GetMapping("/spam/{spamId}")
    public String getMessage(@PathVariable("spamId") Long spamId, Model model, HttpServletRequest request) {

        String msg = spamService.getMessage(spamId, request);
        model.addAttribute("msg", msg);

        return "/read_mail/spam_show_message";
    }

    @PostMapping("/spam_mail.do")
    public String spamMail(@RequestParam(value = "deleteMultiple", required = false) String checkboxValue, RedirectAttributes attrs, @RequestParam(value = "spamId") String send) {
        // 체크박스 null 체크 해야함
        try {
            if (!checkboxValue.isEmpty()) {
                int[] indexs = Stream.of(checkboxValue.split(",")).mapToInt(Integer::parseInt).toArray();

                String host = (String) session.getAttribute("host");
                String userid = (String) session.getAttribute("userid");
                String password = (String) session.getAttribute("password");
                Pop3Agent pop3 = new Pop3Agent(host, userid, password);
                pop3.setRequest(request);
                for (int index : indexs) {
                    boolean deleteSuccessful = pop3.spamMessage(index, true, spamService, send);
                    if (deleteSuccessful) {
                        attrs.addFlashAttribute("msg", "스팸 처리를 성공하였습니다.");
                    } else {
                        attrs.addFlashAttribute("msg", "스팸 처리에 실패하였습니다.");
                    }
                }
            }
            else{
            }
        } catch (NullPointerException e) {
            attrs.addFlashAttribute("msg", "선택된 메일이 없습니다.");
        }
        return "redirect:main_menu";
    }
}
