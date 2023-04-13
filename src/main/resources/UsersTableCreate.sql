/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  정기석
 * Created: 2023. 4. 6.
 */
/* 기존 user테이블을 없애고 밑에 존재하는 명령어를 실행하여 users테이블을 생성하시면 됩니다.*/
CREATE TABLE users (
id bigint auto_increment
,username VARCHAR(64) unique not null
,password varchar(255)
, pwdHash VARCHAR(50)
, pwdAlgorithm VARCHAR(20)
, useForwarding INTEGER
, forwardDestination VARCHAR(255)
, useAlias INTEGER
, alias VARCHAR(255)
, role VARCHAR(255)
, PRIMARY KEY(id)
)