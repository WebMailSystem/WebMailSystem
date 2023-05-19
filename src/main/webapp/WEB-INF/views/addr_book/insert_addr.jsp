<%-- 
    Document   : insert_addr
    Created on : 2023. 5. 9., 오후 12:34:34
    Author     : 권성율
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주소록 추가 화면</title>
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
    <form action="insertAddr.do" method="POST" name="insert" onsubmit="return validateForm()" required>
        <table border="0" summary="주소록 추가">
            <th colspan="2">주소록 추가</th>
            <tbody>
                <tr>
                    <td>이름</td>
                    <td><input type="text" name="nick" size="20" /></td>
                </tr>
                <tr>
                    <td>이메일</td>
                    <td><input type="text" name="email" size="20" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                <input type="submit" value="추가" />
                <input type="reset" value="초기화" />
            </td>
            </tr>
            </tbody>
        </table>
    </form>

    <div id="main">
        ${messageList}
    </div>

    <%@include file="../footer.jspf"%>
</body>
</html>

<script>
    function validateForm() {
        var x = document.forms["insert"]["nick"].value;
        var y = document.forms["insert"]["email"].value;
        if (x == "") {
            alert("이름을 입력해주세요.");
            return false;
        }
        if (y == "") {
            alert("이메일을 입력해주세요.");
            return false;
        }
    }
</script>