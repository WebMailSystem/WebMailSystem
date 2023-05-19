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

CREATE TABLE recyclebin (
            id bigint auto_increment,
            message_name varchar (200) NOT NULL,
            repository_name varchar (100) NOT NULL,
            message_state varchar (30) NOT NULL ,
            error_message varchar (200) NULL ,
            sender varchar (255) NULL ,
            recipients text NOT NULL ,
            remote_host varchar (255) NOT NULL ,
            remote_addr varchar (20) NOT NULL ,
            message_body longblob NOT NULL ,
            message_attributes longblob NULL ,
            last_updated datetime NOT NULL,
            datetime varchar(60) NOT NULL,
            title varchar(60) NOT NULL,
            PRIMARY KEY (id)
        )


alter table inbox add favorite tinyint(1) default false;