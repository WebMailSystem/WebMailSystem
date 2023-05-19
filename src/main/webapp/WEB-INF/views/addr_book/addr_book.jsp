<%-- 
    Document   : addr_book.jsp
    Author     : 권성율
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <script>
            <c:if test="${!empty msg}">
            alert("${msg}");
            </c:if>
        </script>
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_previous_menu.jsp" />
        </div>

        <br> <br>
        <p> <a href="insert_addr"> 주소록 추가 </a> </p>

        <form  method="POST" onsubmit="return checkButton()">
            <table border="1">
                <thead>
                    <tr>
                        <th>선택</th>
                        <th>인덱스</th>
                        <th>이름</th>
                        <th>이메일</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${dataRows}">
                        <tr>
                            <td><input type="checkbox" name="delete_addr" value="${row.id}"></td>
                            <td>${row.id}</td>
                            <td>${row.nick}</td>
                            <td>${row.addremail}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <input type="submit" value="주소록 삭제" onclick="javascript: form.action='deleteAddr.do';" >
            <input type="submit" value="메일 쓰기" onclick="javascript: form.action='write_mail2'; form.method='GET';" >
        </form>



        <div id="main">
            ${messageList}
        </div>




        <%@include file="../footer.jspf"%>
    </body>

    <script>

        function checkButton() {
            if (!confirm("주소록을 삭제하시겠습니까")) {
                return false;
            } else {
                return true;
            }
        }
    </script>

</html>


