/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 *
 * @author 정기석
 */
@Entity(name = "inbox")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inbox {
    
    @EmbeddedId
    private InboxId id;

    @Column(name = "message_state")
    private String messageState;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "sender")
    private String sender;

    @Column(name = "recipients")
    private String recipients;

    @Column(name = "remote_host")
    private String remoteHost;

    @Column(name = "remote_addr")
    private String remoteAddr;

    @Lob
    @Column(name = "message_body")
    private byte[] messageBody;

    @Lob
    @Column(name = "message_attributes")
    private byte[] messageAttributes;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
    
    @Column(name = "favorite")
    private boolean favorite;

    // Getters and setters omitted for brevity
    public void addFavorite(){
        this.favorite = true;
    }
    public void deleteFavorite(){
        this.favorite = false;
    }
}
