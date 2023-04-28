<%-- 
    Document   : index
    Created on : 2022. 6. 10., 오후 2:19:43
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>비밀번호 확인</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>
      

        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>
        <div id="passwordcheck_form">
            <!--<form method="POST" action="login.do?menu=<%= CommandType.LOGIN %>">-->
            <p>마이페이지 이동을 위해 비밀번호를 입력하세요</p>
            <form method="POST">
                <c:if test="${!empty errorMessage}">
                    <div id="errorMessage">
                         <p style="color: red">
                             ${errorMessage}
                     </p>
                    </div>
                </c:if>
             
                암&nbsp;&nbsp;&nbsp;호: <input type="password" name="password" size="20"> <br /> <br />
                <input type="submit" value="입력" name="B1">&nbsp;&nbsp;&nbsp;
                
            </form>
        </div>


        <%@include file="../footer.jspf"%>
    </body>
</html>
