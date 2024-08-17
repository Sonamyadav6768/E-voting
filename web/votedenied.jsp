<%@page import="evoting.dto.CandidateInfo" contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jsscript/vote.js"></script>
        <!--<script type="text/javascript" src="jsscript/jquery.js"></script>-->
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
         <link href="stylesheet/showcandidate.css" rel="stylesheet">
         <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <title>Already Voted</title>
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
            CandidateInfo ci=(CandidateInfo)request.getAttribute("candidate");
            StringBuffer displayBlock=new StringBuffer("<nav><div id='head'><img src=''><p>Vote for Change</p></div>"+
        "<ul><li> <a href='#'>Home</a> </li><li><a href='#'>Voters</a></li><li><a href='#'>Candidates</a></li><li><a href='#'>Results</a></li></ul>"+
        "<div id='btn'><button><a href='logout.jsp'>Logout</a></button></nav>");
            
                displayBlock.append("<div id='votecast'>");
                displayBlock.append("<div><img src='data:image/jpg;base64,"+ci.getSymbol()+"'/></div>");
                displayBlock.append("<div id='candidatedetail'>");
                displayBlock.append("<table>");
                displayBlock.append("<tr><td>CandidateId : </td><td>"+ci.getCandidateid()+"</td></tr>");
                displayBlock.append("<tr><td>Candidate Name: </td><td>"+ci.getCandidatename()+"</td></tr>");
                displayBlock.append("<tr><td>Candidate Party: </td><td>"+ci.getParty()+"</td></tr>");
                displayBlock.append("</table></div>");
                out.println(displayBlock);
        %>
    </body>
</html>
