<%@page import="evoting.dto.UserInfo , java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        response.sendRedirect("accessdenied.html");
        return;
    }
    ArrayList<UserInfo> userList=(ArrayList)request.getAttribute("userList");
    StringBuffer displayBlock=new StringBuffer("");
    if(userList.size()==0)
    {
        displayBlock.append("No user Registered!!!!");
    }
    else
    {
        displayBlock.append("<table>");
        displayBlock.append("<tr><th>User Id</th><th>User Name</th><th>Address</th><th>City</th><th>Email</th><th>Mobile No.</th></tr>");
        for(UserInfo ui:userList)
        {
            displayBlock.append("<tr>");
            displayBlock.append("<td>"+ui.getUserid()+"</td>");
            displayBlock.append("<td>"+ui.getUsername()+"</td>");
            displayBlock.append("<td>"+ui.getAddress()+"</td>");
            displayBlock.append("<td>"+ui.getCity()+"</td>");
            displayBlock.append("<td>"+ui.getEmailid()+"</td>");
            displayBlock.append("<td>"+ui.getMobile()+"</td>");
            displayBlock.append("</tr>");
        }
        displayBlock.append("</table>");
    }
    out.println(displayBlock);
%>
