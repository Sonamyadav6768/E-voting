<%-- 
    Document   : logout
    Created on : 1 Jul, 2023, 7:01:47 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    session.invalidate();
    response.sendRedirect("login.html");
%>
