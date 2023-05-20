<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>


<jsp:useBean id="pop3" scope="page" class="deu.cse.spring_webmail.model.Pop3Agent" />
<%
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Pragma","no-cache");
            pop3.setHost((String) session.getAttribute("host"));
            pop3.setUserid((String) session.getAttribute("userid"));
            pop3.setPassword((String) session.getAttribute("password"));
%>


<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>즐겨찾기 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>
        </script>
    </head>
    <body>
        <%@include file="header.jspf"%>

        <div id="sidebar">
            <jsp:include page="sidebar_menu.jsp" />
        </div>        
        <div id="main">
            ${messageList}
        </div>
        <%@include file="footer.jspf"%>
    </body>
</html>
