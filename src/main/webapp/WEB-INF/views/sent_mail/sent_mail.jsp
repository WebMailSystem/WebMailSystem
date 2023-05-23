<%-- 
    Document   : sent_mail
    Created on : 2023. 5. 22., 오후 7:27:59
    Author     : yool
--%>

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.util.Base64.Encoder"%>
<%@page import="java.util.Base64.Decoder"%>
<%@page import="java.util.Base64"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.io.IOException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<html lang="ko">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>송신 메일함 화면</title>
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
            <jsp:include page="../sidebar_menu.jsp" />
        </div>

        <%!
            public static String blobToString(final byte[] blob) throws Exception {
                return new String(blob);
            }

            public static byte[] convertObjectToBytes(Object obj) throws IOException {
                ByteArrayOutputStream boas = new ByteArrayOutputStream();
                try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
                    ois.writeObject(obj);
                    return boas.toByteArray();
                }
            }
        %>
        <br> <br>
        <h2> -----송신 메일함-----</h2>
        <form  method="POST" >
            <table border="1" summary="송신 메일함">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>받는 사람</th>
                        <th>제목</th>
                        <th>보낸 날짜</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="row" items="${dataRows}">
                        <tr>
                            <td class="autoInc"></td>
                            <td>${fn:substring((fn:substring(row.getId(), 56, 86)), 0, fn:length((fn:substring(row.getId(), 56, 86)))-1)}</td>
                            <c:set var="mbody" value="${row.getMessageBody()}" />
                            <%
                                Object mbody = pageContext.getAttribute("mbody");
                                byte[] mbody2 = convertObjectToBytes(mbody);
                                String mbody3 = blobToString(mbody2);
                                System.out.println(mbody3);

                                int a = mbody3.indexOf("Subject: ");
                                int b = mbody3.indexOf("MIME-Version:");

                                String str = mbody3.substring(a + 9, b);
                                
                            %>
                            <td><%= str %></a> </td>
                            <td>${row.getLastUpdated()}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form>


        <div id="main">
            ${messageList}
        </div>

        <%@include file="../footer.jspf"%>
    </body>


    <script>
        function autoIncrement(startnum) {
            var init = startnum;
            var td_list = document.getElementsByClassName("autoInc");
            for (var i = 0; i < td_list.length; i++) {
                init++;
                td_list[i].innerHTML = "&nbsp" + init;
            }
        }
        ;
        autoIncrement(0);
    </script>
</html>