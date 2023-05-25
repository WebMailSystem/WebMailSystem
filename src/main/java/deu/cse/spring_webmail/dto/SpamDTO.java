/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.dto;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author yool
 * 정기석 학우의 코드를 재사용 하였습니다.
 */
@Data
@Builder
public class SpamDTO {

    private Long id;
    private String sender;
    private String title;
    private String date;
}
