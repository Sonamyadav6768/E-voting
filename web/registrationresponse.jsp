<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
boolean result=(boolean)request.getAttribute("result");
boolean userFound=(boolean)request.getAttribute("userFound");
System.out.println(result+" "+userFound);
if(userFound)
    out.println("uap");
else if(result)
    out.println("success");
else
    out.println("failure");
%>
