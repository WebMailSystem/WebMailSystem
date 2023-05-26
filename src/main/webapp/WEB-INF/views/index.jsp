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
        <title>로그인 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="header.jspf"%>
      

        
        <div id="login_form">
            <form method="POST" action="/webmail/login.do">
                <c:if test="${!empty errorMessage}">
                    <div id="errorMessage">
                         <p style="color: red">
                             ${errorMessage}
                     </p>
                    </div>
                </c:if>
                사용자: <input type="text" name="username" size="20" autofocus> <br />
                암&nbsp;&nbsp;&nbsp;호: <input type="password" name="password" size="20"> <br /> <br />
                <input type="submit" value="로그인" name="B1">&nbsp;&nbsp;&nbsp;
                <input type="reset" value="다시 입력" name="B2">&nbsp;&nbsp;&nbsp;
                <div style="margin-top: 10px;"><a href="/webmail/oauth2/authorization/kakao" role = "button" style="all:none;"><img src="css/kakao_login_medium_wide.png"></a></div>
                <div><a href="/webmail/signup" style="all:none"><input type="button" value="회원가입"></a></div>
            </form>
        </div>


        <%@include file="footer.jspf"%>
    </body>
</html>
