<%-- 
    Document   : admin_menu.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.model.UserAdminAgent"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>사용자 관리 메뉴</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>
        </script>
    </head>
    <body>
        <jsp:include page="../header.jspf" />

        <div id="sidebar">
            <jsp:include page="sidebar_admin_menu.jsp" />
        </div>
        
        
        <div id="main">
            <h2> 메일 사용자 목록 </h2>
            
            <ul>
                <c:forEach items="${userList}" var="user">
                    <li> ${user} </li>
                </c:forEach>
            </ul>
        </div>

        <jsp:include page="../footer.jspf" />
    </body>
</html>
