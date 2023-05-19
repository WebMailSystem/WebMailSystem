<%-- 
    Document   : sidebar_menu
    Created on : 2022. 6. 10., 오후 3:25:30
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>웹메일 시스템 메뉴</title>
    </head>
    <body>
        <br> <br>
        
        <span style="color: indigo"> <strong>사용자: <%= session.getAttribute("userid") %> </strong> </span> <br>

        <p> <a href="main_menu"> 메일 읽기 </a> </p>
        <p> <a href="write_mail"> 메일 쓰기 </a> </p>
        <p> <a href="/webmail/favorite"> 즐겨찾기 </a> </p>
        <p> <a href="addr_book"> 주소록 </a> </p>
        <p><a href="/webmail/recyclebin">휴지통</a></p>        
        <p><a href="/webmail/mypage">마이페이지</a></p>        
        <p><a href="/webmail/logout">로그아웃</a></p>
        
    </body>
</html>
