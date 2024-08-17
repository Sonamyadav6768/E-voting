<%@page import="evoting.dto.PartyResultDTO , java.util.*" contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        response.sendRedirect("accessdenied.html");
        return;
    }
    LinkedHashMap<PartyResultDTO,Integer> result=(LinkedHashMap)request.getAttribute("result");
    System.out.println("IN Election jsp");
    int count=(int)request.getAttribute("votecount");
    System.out.println("total no of votes:"+count);
    System.out.println("Total no of candiate"+result.size());
    StringBuffer displayBlock=new StringBuffer("");
    displayBlock.append("<table>");
    displayBlock.append("<tr><th>Party</th><th>Symbol</th><th>Vote Count</th><th>Voting %</th></tr>");
    System.out.println("total no of votes:"+count);
    System.out.println("Total no of candiate"+result.size());
    for(Map.Entry<PartyResultDTO,Integer> ci:result.entrySet())
    {
        displayBlock.append("<tr>");
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
