/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author 정기석
 */
@Getter
@Setter
public class SignupForm {
    
    @NotEmpty(message = "ID를 입력해주세요")
    private String username;
    
    @NotEmpty(message = "Password를 입력해주세요")
    @Length(min = 3,max = 10,message = "비밀번호가 너무 짧습니다.")
    private String password;
    
}
