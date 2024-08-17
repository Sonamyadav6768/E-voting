<%-- 
    Document   : adminupdatecandidate
    Created on : 2 Jul, 2023, 1:53:33 AM
    Author     : Dell
--%>

<%@page import="org.json.JSONObject, evoting.dto.CandidateDetailDTO , java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
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
    if(result.equalsIgnoreCase("candidateList"))
    {
        ArrayList<String> idList=(ArrayList<String>)request.getAttribute("cid");
        displayBlock.append("<option value=''> Choose id </option>");
        for(String id:idList)
        {
            displayBlock.append("<option value='"+id+"'>"+id+"</option>");
        }
        JSONObject json=new JSONObject();
        json.put("cid", displayBlock.toString());
            out.println(json);
    }
    else
    {
        CandidateDetailDTO cd=(CandidateDetailDTO)request.getAttribute("cid");
        String img="<img src='data:image/jpg;base64,"+cd.getSymbol()+"' style='width:300px;height:200px;'/>";
//        displayBlock.append("<table>");
//        displayBlock.append("<tr><th>User id : </th><td>"+cd.getUserid()+"</td></tr>");
//        displayBlock.append("<tr><th>Candidate Name : </th><td>"+cd.getCandidatename()+"</td></tr>");
//        displayBlock.append("<tr><th>City : </th><td>"+cd.getCity()+"</td></tr>");
//        displayBlock.append("<tr><th>Party : </th><td>"+cd.getParty()+"</td></tr>");
//        displayBlock.append("<tr><th>Symbol : </th><td>"+img+"</td></tr>");
//        displayBlock.append("</table>");
        ArrayList<String> city=(ArrayList)request.getAttribute("city");
        StringBuffer sb=new StringBuffer();
        for(String c: city)
        {
            sb.append("<option value='"+c+"'>"+c+"</option>");
        }
        JSONObject json=new JSONObject();
        json.put("cid", cd.getCandidateid());
        json.put("userid",cd.getUserid());
        json.put("name", cd.getCandidatename());
        json.put("city",sb.toString());
        json.put("party", cd.getParty());
        json.put("symbol",img);
        out.println(json);
    }
%>