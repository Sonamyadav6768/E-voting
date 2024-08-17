<%-- 
    Document   : userremove
    Created on : 2 Jul, 2023, 9:22:45 PM
    Author     : Dell
--%>

<%@page import="org.json.JSONObject, evoting.dto.UserInfo , java.util.*"  contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
        return;
    }
    
    String result=(String)request.getAttribute("result");
    StringBuffer displayBlock=new StringBuffer();
    if(result.equalsIgnoreCase("userList"))
    {
        ArrayList<String> idList=(ArrayList<String>)request.getAttribute("userList");
        displayBlock.append("<option value> Choose id </option>");
        for(String id:idList)
        {
            displayBlock.append("<option value='"+id+"'>"+id+"</option>");
        }
        JSONObject json=new JSONObject();
        json.put("uid", displayBlock.toString());
            out.println(json);
    }
    else
    {
        UserInfo u=(UserInfo)request.getAttribute("userDetail");
        displayBlock.append("<table>");
        displayBlock.append("<tr><th>User name : </th><td>"+u.getUsername()+"</td></tr>");
        displayBlock.append("<tr><th>Email : </th><td>"+u.getEmailid()+"</td></tr>");
        displayBlock.append("<tr><th>Mobile No. : </th><td>"+u.getMobile()+"</td></tr>");
        displayBlock.append("<tr><th>Address : </th><td>"+u.getAddress()+"</td></tr>");
        displayBlock.append("<tr><th>Citys : </th><td>"+u.getCity()+"</td></tr>");
        displayBlock.append("</table>");
        displayBlock.append("</br></br>");
        displayBlock.append("<button id='delbtn' onclick='removeUser()'>Delete</button>");
        
        JSONObject json=new JSONObject();
        json.put("details",displayBlock.toString());
            out.println(json);
    }
%>
