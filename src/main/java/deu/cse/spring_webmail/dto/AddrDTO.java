/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import deu.cse.spring_webmail.entity.Addrs;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author 권성율
 */
@Getter
@Setter
@Slf4j
public class AddrDTO {

    Long id;
    String userName;    //로그인한 사용자 id
    String addrEmail;   //상대방 id
    String nick;        //상대방 id 별명

    public AddrDTO(String userName, String addrEmail, String nick) {
        this.userName = userName;
        this.addrEmail = addrEmail;
        this.nick = nick;
    }
    
    public Addrs toEntity(){
        return Addrs.builder()
                .username(userName)
                .addremail(addrEmail)
                .nick(nick)
                .build();
    }
}
