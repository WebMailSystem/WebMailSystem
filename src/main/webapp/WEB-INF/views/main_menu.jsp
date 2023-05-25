<%-- 
    Document   : main_menu
    Created on : 2022. 6. 10., 오후 3:15:45
    Author     : skylo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<!-- 제어기에서 처리하면 로직 관련 소스 코드 제거 가능!
<jsp:useBean id="pop3" scope="page" class="deu.cse.spring_webmail.model.Pop3Agent" />
<%
            response.setHeader("Cache-Control","no-cache");
            response.setHeader("Pragma","no-cache");
            pop3.setHost((String) session.getAttribute("host"));
            pop3.setUserid((String) session.getAttribute("userid"));
            pop3.setPassword((String) session.getAttribute("password"));
%>
-->

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>주메뉴 화면</title>
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
            <div>
              <form action="" method="POST">
                <select name ="searchType">
                    <option value="none" selected>=== 선택 ===</option>
                    <option value="sender">송신자</option>
                    <option value="contents">내용</option>
                    <option value="all">내용 + 송신자</option>                    
                </select>
                <input type="text" name = "keyword"/>
                <input type ="submit" value="검색"/>
                </form>
                <br>
            </div>
            ${messageList}
        </div>
        
        <!-- 페이징 처리 버튼 동적으로 만드는 코드 -->
        <c:if test="${totalPages > 0}">
                <div class="pagination">
                    <ol>
                        <!-- 페이지 번호 링크 -->
                        <c:forEach begin="1" end="${totalPages}" var="page">
                            <li>
                                <span class="page-link" onclick="changePage(${page})">${page}</span>
                            </li>
                        </c:forEach>    
                    </ol>
                </div>
        </c:if>
        
        <script>
                var table = document.getElementById('mailTable');
                var rowsPerPage = ${recordsPerPage}; // 보여줄 페이지 수 
                var currentPage = 1;
                function showPage(pageNumber) {
                    var rows = table.rows;
                    
                    for (var i = 1; i < rows.length-1; i++) {
                        if (i >= (pageNumber - 1) * rowsPerPage + 1 && i <= pageNumber * rowsPerPage) {
                            rows[i].classList.remove('hidden');
                        } else {
                            rows[i].classList.add('hidden');
                        }
                    }                    
                }
                function changePage(pageNumber) {
                    if (pageNumber < 1 || pageNumber > Math.ceil((table.rows.length - 1) / rowsPerPage)) {
                        return;
                    }
                    currentPage = pageNumber;
                    showPage(currentPage);
                    var pageLinks = document.getElementsByClassName('page-link');
                    for (var i = 0; i < pageLinks.length; i++) {
                        pageLinks[i].classList.remove('active');
                    }
                    pageLinks[currentPage - 1].classList.add('active');
                }
                showPage(currentPage);
            </script>
        
        <%@include file="footer.jspf"%>
    </body>
</html>
