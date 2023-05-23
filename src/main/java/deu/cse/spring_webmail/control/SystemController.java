/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.control;

import deu.cse.spring_webmail.dto.SessionDTO;
import deu.cse.spring_webmail.entity.Role;
import deu.cse.spring_webmail.entity.Users;
import deu.cse.spring_webmail.model.InboxService;
import deu.cse.spring_webmail.model.Pop3Agent;
import deu.cse.spring_webmail.model.UserAdminAgent;
import deu.cse.spring_webmail.model.UserService;
import jakarta.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 초기 화면과 관리자 기능(사용자 추가, 삭제)에 대한 제어기
 * ss
 * @author skylo
 */
@Controller
@PropertySource("classpath:/system.properties")
@Slf4j
@RequiredArgsConstructor
public class SystemController {

    @Autowired
    private ServletContext ctx;
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private final InboxService inboxService;    
    private final AdminService adminService;

    @Value("${root.id}")
    private String ROOT_ID;
    @Value("${root.password}")
    private String ROOT_PASSWORD;
    @Value("${admin.id}")
    private String ADMINISTRATOR;  //  = "admin";
    @Value("${james.control.port}")
    private Integer JAMES_CONTROL_PORT;
    @Value("${james.host}")
    private String JAMES_HOST;

    @GetMapping("/")
    public String index(@RequestParam(required = false,name = "errormessage")String errorMessage,Model model) {              
        log.info("session = {}",session.getAttribute("userid"));
        SessionDTO sessionDTO = (SessionDTO)session.getAttribute("user");
        if(sessionDTO != null && sessionDTO.getRole().equals(Role.USER)){
            return "redirect:/main_menu";
        }
        log.debug("index() called...");
        session.setAttribute("host", JAMES_HOST);
        session.setAttribute("debug", "false");
        model.addAttribute("errorMessage", errorMessage);
        return "/index";
    }
 
    protected boolean isAdmin(String userid) {
        boolean status = false;

        if (userid.equals(this.ADMINISTRATOR)) {
            status = true;
        }

        return status;
    }

    @GetMapping("/main_menu")
    public String mainmenu(Model model) {
        Pop3Agent pop3 = new Pop3Agent();
        log.info("host = {},id = {},password = {}",(String) session.getAttribute("host"),(String) session.getAttribute("userid"),
                (String) session.getAttribute("password"));
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));
        String messageList = pop3.getMessageList();
        log.info("messageList = {}",messageList);
        model.addAttribute("messageList", messageList);
        return "main_menu";
    }    
    @PostMapping("/main_menu")
    public String searchMainMenu(@RequestParam("searchType") String searchType,@RequestParam("keyword")String keyword,Model model){
        log.info("type = {}, keyword = {}",searchType,keyword);
         String messageList = "";
        try{
        messageList = inboxService.search((String)session.getAttribute("userid"), searchType, keyword);
        }catch(Exception e){
            log.error("e",e);
        }
        model.addAttribute("messageList", messageList);
        return "main_menu";
    }

    @GetMapping("/admin_menu")
    public String adminMenu(Model model, RedirectAttributes attrs) {
        log.debug("root.id = {}, root.password = {}, admin.id = {}",
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
        
        log.info("session = {}",session.getAttribute("userid"));
        
        // 스프링 시큐리티 사용하기 전 관리자 체크 방식        
//        String nowUser = session.getAttribute("userid").toString();                
        
//        if(!isAdmin(nowUser)) {
//            log.info("관리자 권한 없는데 유저 추가하려고 함");
//            attrs.addFlashAttribute("msg", String.format("관리자 권한이 없습니다."));
//            return "redirect:/main_menu";
//        }
        
        model.addAttribute("userList", getUserList());
        return "admin/admin_menu";
    }

    @GetMapping("/add_user")
    public String addUser() {
        return "admin/add_user";
    }

    @PostMapping("/add_user.do")
    public String addUserDo(@RequestParam String id, @RequestParam String password,
            RedirectAttributes attrs) {
        log.debug("add_user.do: id = {}, password = {}, port = {}",
                id, password, JAMES_CONTROL_PORT);
        
        log.info("session = {}",session.getAttribute("userid"));
        
        // 스프링 시큐리티 사용하기 전 관리자 체크 방식        
//        String nowUser = session.getAttribute("userid").toString();                
        
//        if(!isAdmin(nowUser)) {
//            log.info("관리자 권한 없는데 유저 추가하려고 함");
//            attrs.addFlashAttribute("msg", String.format("관리자 권한이 없습니다."));
//            return "redirect:/main_menu";
//        }
        
        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
            
            if (agent.addUser(id, password)) {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 성공하였습니다.", id));
            } else {
                attrs.addFlashAttribute("msg", String.format("사용자(%s) 추가를 실패하였습니다.", id));
            }
        } catch (Exception ex) {
            log.error("add_user.do: 시스템 접속에 실패했습니다. 예외 = {}", ex.getMessage());
        }

        return "redirect:/admin_menu";
    }

    @GetMapping("/delete_user")
    public String deleteUser(Model model) {
        log.debug("delete_user called");
        
        log.info("session = {}",session.getAttribute("userid"));
        
        // 스프링 시큐리티 사용하기 전 관리자 체크 방식        
//        String nowUser = session.getAttribute("userid").toString();                
        
//        if(!isAdmin(nowUser)) {
//            log.info("관리자 권한 없는데 유저 추가하려고 함");
//            attrs.addFlashAttribute("msg", String.format("관리자 권한이 없습니다."));
//            return "redirect:/main_menu";
//        }
        
        model.addAttribute("userList", getUserList());
        return "admin/delete_user";
    }

    /**
     *
     * @param selectedUsers <input type=checkbox> 필드의 선택된 이메일 ID. 자료형: String[]
     * @param attrs
     * @return
     */
    @PostMapping("delete_user.do")
    public String deleteUserDo(@RequestParam String[] selectedUsers, RedirectAttributes attrs) {
        log.debug("delete_user.do: selectedUser = {}", List.of(selectedUsers));

        
        log.info("session = {}",session.getAttribute("userid"));
        
        // 스프링 시큐리티 사용하기 전 관리자 체크 방식        
//        String nowUser = session.getAttribute("userid").toString();                
        
//        if(!isAdmin(nowUser)) {
//            log.info("관리자 권한 없는데 유저 추가하려고 함");
//            attrs.addFlashAttribute("msg", String.format("관리자 권한이 없습니다."));
//            return "redirect:/main_menu";
//        }

        try {
            String cwd = ctx.getRealPath(".");
            UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                    ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
            agent.deleteUsers(selectedUsers);  // 수정!!!
        } catch (Exception ex) {
            log.error("delete_user.do : 예외 = {}", ex);
        }

        return "redirect:/admin_menu";
    }

    private List<String> getUserList() {
        String cwd = ctx.getRealPath(".");
        UserAdminAgent agent = new UserAdminAgent(JAMES_HOST, JAMES_CONTROL_PORT, cwd,
                ROOT_ID, ROOT_PASSWORD, ADMINISTRATOR);
        List<String> userList = agent.getUserList();
        log.debug("userList = {}", userList);

        //(주의) root.id와 같이 '.'을 넣으면 안 됨.
        userList.sort((e1, e2) -> e1.compareTo(e2));
        return userList;
    }

    @GetMapping("/img_test")
    public String imgTest() {
        return "img_test/img_test";
    }

    /**
     * https://34codefactory.wordpress.com/2019/06/16/how-to-display-image-in-jsp-using-spring-code-factory/
     * 
     * @param imageName
     * @return 
     */
    @RequestMapping(value = "/get_image/{imageName}")
    @ResponseBody
    public byte[] getImage(@PathVariable String imageName) {
        try {
            String folderPath = ctx.getRealPath("/WEB-INF/views/img_test/img");
            return getImageBytes(folderPath, imageName);
        } catch (Exception e) {
            log.error("/get_image 예외: {}", e.getMessage());
        }
        return new byte[0];
    }

    private byte[] getImageBytes(String folderPath, String imageName) {
        ByteArrayOutputStream byteArrayOutputStream;
        BufferedImage bufferedImage;
        byte[] imageInByte;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedImage = ImageIO.read(new File(folderPath + File.separator + imageName) );
            String format = imageName.substring(imageName.lastIndexOf(".") + 1);
            ImageIO.write(bufferedImage, format, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return imageInByte;
        } catch (FileNotFoundException e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        } catch (Exception e) {
            log.error("getImageBytes 예외: {}", e.getMessage());
        }
        return null;
    }
    
    @GetMapping("/favorite")
    public String favorite(Model model,HttpSession session){
        String messageList = "";
        var username = (String)session.getAttribute("userid");
        try{
        messageList = inboxService.favorites(username);
        }catch(Exception e){
            log.error("e",e);
        }
        model.addAttribute("messageList", messageList);                                
        return "favorite";
    }
    @GetMapping("add-favorite.do")
    public String addFavorite(@RequestParam("msgid") int msgid){       
        Pop3Agent pop3 = new Pop3Agent();       
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));
        pop3.setRequest(request);
        try {
            pop3.addFavorite(msgid,inboxService);
        } catch (MessagingException ex) {
            Logger.getLogger(SystemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:/favorite";
    }
    @GetMapping("delete-favorite.do")
    public String deleteFavorite(@RequestParam("messageId") String msgid,@RequestParam("sender") String sender){        
        Pop3Agent pop3 = new Pop3Agent();       
        pop3.setHost((String) session.getAttribute("host"));
        pop3.setUserid((String) session.getAttribute("userid"));
        pop3.setPassword((String) session.getAttribute("password"));
        pop3.setRequest(request);
        int semicolonIndex = msgid.indexOf(";");        
        String result = msgid.substring(semicolonIndex + 1);                           
        pop3.deleteFavorite(result,inboxService,sender);
        
        return "redirect:/favorite";  
    }
    
    @GetMapping("change_pw")
    public String changeAdminPw(RedirectAttributes attrs) {       
        
        return "/admin/change_password";
    }
    
    @PostMapping("adminPasswordChange.do")
    public String changeAdminPwDo(RedirectAttributes attrs) {        
        String old = request.getParameter("old_password");
        String newPw = request.getParameter("new_password");
        log.info("{}, {}", old, newPw);
                
        String userName = session.getAttribute("userid").toString();
        
        // 기존 비밀번호와 동일한지 체크하기
        if (!adminService.passwordCheck(userName, old)) {
            log.info("기존 비밀번호가 일치하지 않습니다.");
            attrs.addFlashAttribute("msg", String.format("기존 비밀번호가 일치하지 않습니다."));
            
            return "redirect:/change_pw";
        }   
        
        // 변경할 비밀번호가 기존 비밀번호와 동일할 경우
        if (adminService.passwordCheck(userName, newPw)) {
            log.info("기존 비밀번호와 변경할 비밀번호가 동일합니다.");
            attrs.addFlashAttribute("msg", String.format("기존 비밀번호와 변경할 비밀번호가 동일합니다."));
            
            return "redirect:/change_pw";
        }
        
        // 비밀번호 변경
        SessionDTO user =(SessionDTO)session.getAttribute("user");
        adminService.changePassword(user.getId(), old, newPw);
        attrs.addFlashAttribute("msg", String.format("비밀번호 변경이 완료되었습니다."));
        return "redirect:/admin_menu";
    }
}
