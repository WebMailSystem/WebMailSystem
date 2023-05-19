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
-->

<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>휴지통 화면</title>
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


        <!-- 메시지 삭제 링크를 누르면 바로 삭제되어 실수할 수 있음. 해결 방법은? -->
        <div id="main">
            <h2> -----휴지통-----</h2>
            <table>
                <caption>휴지통 메일 리스트</caption>
                <tr>
                    <th> No.</th>
                    <th> 보낸사람</th>
                    <th> 제목</th>
                    <th> 보낸날짜</th>
                    <th> 복구</th>
                    <th> 삭제</th>
                </tr>
                <% int i =  (Integer) request.getAttribute("count");%>
            <c:forEach var = "recyclebinDTO" items = "${lists}">            
                <tr>
                    <td id ="no"><%= i%></td>
                    <c:set var="sender" value="${fn:substringBefore(recyclebinDTO.sender, '@')}" />
                    <td id="sender">${sender}</td>
                    <td id ="subject"><a href="recyclebin/${recyclebinDTO.id}">${recyclebinDTO.title}</a></td>
                    <td id ="date">${recyclebinDTO.date}</td>
                    <td id ="restore"><a href="recyclebin/restore/${recyclebinDTO.id}">복구</a> </td>
                    <td id ="delete"><a href="recyclebin/delete/${recyclebinDTO.id}">삭제</a> </td>
                </tr>
                <% i--; %>
            </c:forEach>
            </table>
        </div>

        <%@include file="footer.jspf"%>
    </body>
</html>
