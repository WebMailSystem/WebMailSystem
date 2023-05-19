/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 * @author 정기석
 */
@Entity(name = "recyclebin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recyclebin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; 
    
    @Embedded
    private InboxId inboxId;

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
    
    @Column(name = "datetime")
    private String date;
    
    @Column(name = "title")
    private String title;
    // Getters and setters omitted for brevity
}
