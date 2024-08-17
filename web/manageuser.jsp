<%-- 
    Document   : manageuser
    Created on : 2 Jul, 2023, 8:25:10 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jsscript/useroption.js"></script>
        <script type="text/javascript" src="jsscript/jquery.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
         <link href="stylesheet/admin.css" rel="stylesheet">
         <!--<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <title>Manage User Page</title>
    </head>
    <body>
       <%
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
                session.invalidate();
                response.sendRedirect("accessdenied.html");
                return;
            }
            StringBuffer displayBlock=new StringBuffer("<nav><div id='head'><img src=''><p>Vote for Change</p></div>"+
        "<ul><li> <a href='#'>Home</a> </li><li><a href='#'>Voters</a></li><li><a href='#'>Candidates</a></li><li><a href='#'>Results</a></li></ul>"+
        "<div id='btn'><button><a href='logout.jsp'>Logout</a></button></nav>");
            
            displayBlock.append("<div id='box'>");
            displayBlock.append("<div id='dv1' onclick='showuserform()'>"
                    +"<img src='images/show.png' height='300px' width='300px' />"
                    +"<br />"
                    +"<h3>Show User</h3>"
                    +"</div>");
            displayBlock.append("<div id='dv2' onclick='removeuserform()'>"
                    +"<img src='images/delete.jpg' height='300px' width='300px' />"
                    +"<br />"
                    +"<h3>Remove User</h3>"
                    +"</div>");
            displayBlock.append("</div>");
            displayBlock.append("<br/><br/><div align='center' id='result'></div>");
            out.println(displayBlock);
            
        %>
    </body>
</html>
