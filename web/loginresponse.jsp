<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String result=(String)request.getAttribute("result");
    String userid=(String)request.getAttribute("userid");
    
    //Login kr gaya h
    if(result!=null && userid!=null)
    {
        HttpSession sess=request.getSession();
        sess.setAttribute("userid", userid);
        String url="";
        if(result.equalsIgnoreCase("admin"))
            url="AdminControllerServlet;jsessionid="+sess.getId();
        else
            url="VotingControllerServlet;jsessionid="+sess.getId();
        out.println(url);
    }
    else
        out.println("error");
%>