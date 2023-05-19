/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.AddrDTO;
import deu.cse.spring_webmail.entity.Addrs;
import deu.cse.spring_webmail.repository.AddrsRepository;
import java.util.List;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 주소록
 *
 * @author 권성율
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class AddrController {

    @Autowired
    AddrsRepository repository;

    @GetMapping("/addr_book")
    public String addrBook(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        List<Addrs> dataRows = repository.findByUsername((String) session.getAttribute("userid"));
        model.addAttribute("dataRows", dataRows);

        return "/addr_book/addr_book";
    }

    @GetMapping("/insert_addr")
    public String insertAddr(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return "/addr_book/insert_addr";
    }

    @PostMapping("/insertAddr.do")
    public String insertAddressBook(HttpServletRequest request, @RequestParam("nick") String nick, @RequestParam("email") String email, RedirectAttributes attrs) {
        HttpSession session = request.getSession();

        System.out.println(repository.findByUsername((String) session.getAttribute("userid")));

        String str;

        try {
            AddrDTO dto = new AddrDTO((String) session.getAttribute("userid"), email, nick);
            Addrs entity = new Addrs();
            entity = dto.toEntity();
            repository.save(entity);

            attrs.addFlashAttribute("msg", "주소록이 성공적으로 추가되었습니다.");
            str = "redirect:/addr_book";
        } catch (Exception ex) {
            log.error("주소록 추가시. 발생 오류: {}", ex.getMessage());

            attrs.addFlashAttribute("msg", "이미 추가하였거나 존재하지 않는 이메일입니다.");
            str = "redirect:/insert_addr";
        }
        return str;
    }

    @PostMapping("/deleteAddr.do")
    public String deleteMailDo(@RequestParam("delete_addr") String id, RedirectAttributes attrs) {
        log.debug("delete_mail.do: msgid = {}", id);

        long[] indexes = Stream.of(id.split(",")).mapToLong(Long::parseLong).toArray();
        
        for (long index : indexes) {
            Addrs addr = repository.findById(index).orElseThrow(() -> new IllegalArgumentException("체크 안 되어있음"));
            repository.delete(addr);
        }
        
        attrs.addFlashAttribute("msg", "주소록이 삭제되었습니다.");

        return "redirect:/addr_book";
    }
}
