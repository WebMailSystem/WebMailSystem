<%-- 
    Document   : change_password
    Created on : 2023. 5. 24., 오전 5:27:14
    Author     : 정민수
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="deu.cse.spring_webmail.control.CommandType"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>관리자 비밀번호 변경 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
    </head>
    <body>
        <%@include file="../header.jspf"%>
     
        <div id="sidebar">
            <jsp:include page="./sidebar_admin_menu.jsp" />
        </div>
        <div class ="main">
            <c:if test="${empty oauth2Check}">
                
                <div id="passwordcheck_form">                                
                    <form method="POST" action="./adminPasswordChange.do">
                        <c:if test="${!empty errorMessage}">
                            <div id="errorMessage">
                                 <p style="color: red">
                                     ${errorMessage}
                             </p>
                            </div>
                        </c:if>

                        기&nbsp;&nbsp;존&nbsp;&nbsp;암&nbsp;&nbsp;호: <input type="password" name="old_password" size="20" required /> <br /> <br />                                                
                        변&nbsp;&nbsp;경&nbsp;&nbsp;암&nbsp;&nbsp;호: <input type="password" name="new_password" size="20" required /> <br /> <br />                        
                        
                        <input type="submit" value="비밀번호 변경">&nbsp;&nbsp;&nbsp;
                    </form>
                </div>
            </c:if>
        </div>
        <br>
        <br>
        <br>
        <br>
        <%@include file="../footer.jspf"%> 
    </body>
</html>

