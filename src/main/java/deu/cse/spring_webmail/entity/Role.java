/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author 정기석
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    USER("ROLE_USER"),ADMIN("ROLE_ADMIN");
    private final String value;
}
