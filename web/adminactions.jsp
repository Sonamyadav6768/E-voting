<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jsscript/adminoption.js"></script>
        <script type="text/javascript" src="jsscript/jquery.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
         <link href="stylesheet/admin.css" rel="stylesheet">
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <title>Admin Action Page</title>
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
        "<div id='btn'><button><a href='logout.jsp;'>Logout</a></button></nav>");
            
            displayBlock.append("<div id='box'>");
            displayBlock.append("<div id='dv1' onclick='manageuser()'>"
                    +"<img src='images/muser.png' height='300px' width='300px' />"
                    +"<br />"
                    +"<h3>Manage User</h3>"
                    +"</div>");
            displayBlock.append("<div id='dv2' onclick='managecandidate()'>"
                    +"<img src='images/ManageCandLists.jpg' height='300px' width='300px' />"
                    +"<br />"
                    +"<h3>Manage Candidate</h3>"
                    +"</div>");
            displayBlock.append("<div id='dv3' onclick='election()'>"
                    +"<img src='images/resultgraph.jpg' height='300px' width='300px' />"
                    +"<br />"
                    +"<h3>Election Result</h3>"
                    +"</div>");
            displayBlock.append("</div>");
            displayBlock.append("<br/><br/><div align='center' id='button'></div>");
            displayBlock.append("<br/><br/><div align='center' id='result'></div>");
            out.println(displayBlock);
            
        %>
    </body>
</html>
