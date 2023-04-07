<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>회원가입 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="header.jspf"%>

        <div id="login_form">
            <form method="POST" action="/webmail/signup.do">
                <c:if test = "${!empty errors}">
                    <c:forEach items="${errors}" var="error">
                        <p style = "color: red">${error.getDefaultMessage()}</p>
                    </c:forEach>
                </c:if>
                사용자: <input type="text" name="username" size="20" autofocus> <br />
                암&nbsp;&nbsp;&nbsp;호: <input type="password" name="password" size="20"> <br /> <br />                
                <input type="submit" value="회원가입" name="B1">&nbsp;&nbsp;&nbsp;     
                <input type="reset" value="다시 입력" name="B2">&nbsp;&nbsp;&nbsp;
             
            </form>
        </div>


        <%@include file="footer.jspf"%>
    </body>
</html>
