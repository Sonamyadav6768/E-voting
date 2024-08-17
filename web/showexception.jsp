
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Exception ex=(Exception)request.getAttribute("exception");
    System.out.println("Exception is : "+ex);
    out.println("Some Exception occurred ");
%>
