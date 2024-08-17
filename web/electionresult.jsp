<%-- 
    Document   : electionresult
    Created on : 1 Jul, 2023, 5:40:00 PM
    Author     : Dell
--%>

<%@page import="evoting.dto.CandidateDetailDTO , java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        response.sendRedirect("accessdenied.html");
        return;
    }
    LinkedHashMap<CandidateDetailDTO,Integer> result=(LinkedHashMap)request.getAttribute("result");
    System.out.println("IN Election jsp");
    int count=(int)request.getAttribute("votecount");
    System.out.println("total no of votes:"+count);
    System.out.println("Total no of candiate"+result.size());
    StringBuffer displayBlock=new StringBuffer("");
    displayBlock.append("<table>");
    displayBlock.append("<tr><th>CandidateId</th><th>Candidate Name</th><th>City</th><th>Party</th><th>Symbol</th><th>Vote Count</th><th>Voting %</th></tr>");
    for(Map.Entry<CandidateDetailDTO,Integer> ci:result.entrySet())
    {
        displayBlock.append("<tr>");
        displayBlock.append("<td>"+ci.getKey().getCandidateid()+"</td>");
        displayBlock.append("<td>"+ci.getKey().getCandidatename()+"</td>");
        displayBlock.append("<td>"+ci.getKey().getCity()+"</td>");
        displayBlock.append("<td>"+ci.getKey().getParty()+"</td>");
        displayBlock.append("<td><img src='data:image/jpg;base64,"+ci.getKey().getSymbol()+"' /></td>");
        displayBlock.append("<td>"+ci.getValue()+"</td>");
        displayBlock.append("<td>"+(ci.getValue()/(float)count)*100+"</td>");
        displayBlock.append("</tr>");
//        System.out.println("IN Election jsp");
    }
    displayBlock.append("</table>");
    out.println(displayBlock);
%>
