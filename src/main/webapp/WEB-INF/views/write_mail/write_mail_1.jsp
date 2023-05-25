<%-- 
    Document   : write_mail.jsp
    Author     : jongmin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>메일 쓰기 화면</title>
        <link type="text/css" rel="stylesheet" href="css/main_style.css" />
        <style>
            .ck-editor__editable {
                height: 400px;
            }
        </style>
    </head>
    <body>
        <%@include file="../header.jspf"%>

        <div id="sidebar">
            <jsp:include page="../sidebar_previous_menu.jsp" />
        </div>

        <div id="main">
            <form enctype="multipart/form-data" method="POST" action="write_mail.do" >
                <table summary="주소록 기능에서 바로 메일쓰기">
                    <tr><th scope="col" colspan="2">메일 쓰기</th></tr>
                    <tr>
                        <td> 수신 </td>
                        <td> <input type="text" name="to" size="80" value="${sender}"  
                                    <!--    value=<%=request.getParameter("recv") == null ? "" : request.getParameter("recv")%>  -->
                        </td>            
                        </td>
                    </tr>
                    <tr>
                        <td>참조</td>
                        <td> <input type="text" name="cc" size="80">  </td>
                    </tr>
                    <tr>
                        <td> 메일 제목 </td>
                        <td> <input type="text" name="subj" size="80" 
                                    value="${!empty param['sender'] ? "RE: " += sessionScope['subject'] : ''}" >  </td>
                    </tr>
                    <tr>
                        <td colspan="2">본  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 문</td>
                    </tr>
                    <tr>  <%-- TextArea    --%>
                        <td colspan="2">
                            <textarea rows="15" name="body" cols="80" id="editor">${!empty param['sender'] ?
                                                                                    "



                                                                                    ----
                                                                                    " += sessionScope['body'] : ''}</textarea> 
                        </td>
                    </tr>
                    <tr>
                        <td>첨부 파일</td>
                        <td> <input type="file" name="file1"  size="80">  </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="메일 보내기">
                            <input type="reset" value="다시 입력">
                        </td>
                    </tr>
                </table>
            </form>
        </div>

        <%@include file="../footer.jspf"%>        
        <script src="https://cdn.ckeditor.com/ckeditor5/37.0.1/classic/ckeditor.js" integrity="sha512-ICu1H+8v4PhUt4ABYxy3cQA+uLij3HExUA7c4KZLgG2BTK1OzwWdbK5d1nqlEp4kBbVBszJcug+zCTV77mCWNg==" crossorigin="anonymous"></script>
        <script src="https://cdn.ckeditor.com/ckeditor5/34.0.0/classic/translations/ko.js" integrity="sha512-b8e0sUAMKXH7dVSSfQkBufDFGjaUcY/lpxMZijaW8nX5CeFmfvU9XroLnXt/pfRjWwKrDWt0pqEobetBpvqohQ==" crossorigin="anonymous"></script>
        <script>
            ClassicEditor.create(document.querySelector('#editor'), {
                toolbar: {
                    items: [
                        'heading',
                        '|', 'bold', 'italic',
                        '|', 'insertTable', 'bulletedList', 'numberedList',
                        '|', , 'undo', 'redo',
                    ]
                },
                language: "ko"

            });
        </script>
    </body>
</html>