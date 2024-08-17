
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
    }
    out.println("success");
%>
