<%-- 
    Document   : show_message.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>휴지통 메일 보기 화면</title>
        <link type="text/css" rel="stylesheet" href="../css/main_style.css" />
    </head> 
    <body>
        <%@include file="../header.jspf"%>
 
        <div id="msgBody">
            ${msg}
        </div>
        <hr>
        <a href="/webmail/recyclebin"> 이전 메뉴로 </a>
        <%@include file="../footer.jspf"%>
    </body>
</html>
