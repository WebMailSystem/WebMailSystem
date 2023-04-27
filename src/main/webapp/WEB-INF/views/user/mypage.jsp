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
        <script>
            function checkButton(){
                if(!confirm("회원탈퇴 하시겠습니까?")){
                    return false;
                }else{
                    return true;
                }
            }
        </script>
        
        
    </head>
    <body>
        <%@include file="../header.jspf"%>
     
        <div id="sidebar">
            <jsp:include page="../sidebar_menu.jsp" />
        </div>
        <div class ="main">
        <c:if test="${empty oauth2Check}">
        <div><a href="/webmail/change-password" style="all:none"><input type="button" value="비밀번호 변경"></a></div>
        </c:if>
        <br>
        <br>
        <br>
        <br>
        <c:if test = "${!empty oauth2Check}"><span style="color: red">소셜 사용자는 탈퇴후 소셜에서도 연결을 끊어야 합니다.</span></c:if>
        <form action ="delete-user.do" method="POST" onsubmit="return checkButton()">
            <input type="submit" value="회원탈퇴" style="color: red">
        </form>       
        <%@include file="../footer.jspf"%> 
    </body>
</html>
