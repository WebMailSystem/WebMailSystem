<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>마이페이지 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>
     
        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>
        <div class ="main">
        <div><a href="/webmail/change-password" style="all:none"><input type="button" value="비밀번호 변경"></a></div>
        <br>
        <br>
        <br>
        <br>
        <form action ="delete-user.do" method="POST">
            <input type="submit" value="회원탈퇴" style="color: red">
        </form>       
        <%@include file="../footer.jspf"%> 
    </body>
</html>
