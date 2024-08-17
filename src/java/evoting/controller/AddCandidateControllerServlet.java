/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Dell
 */
public class AddCandidateControllerServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd=null;
        PrintWriter out=response.getWriter();
        HttpSession sess=request.getSession();
        String userid=(String)sess.getAttribute("userid");
        if(userid==null)
        {
            sess.invalidate();
            response.sendRedirect("accessdenied.html");
        }
        String candidate=(String)request.getParameter("id");
        String usid=(String)request.getParameter("uid");
        if(candidate!=null && candidate.equalsIgnoreCase("getid"))
        {
            System.out.println("Peek");
            try{
                String id=CandidateDao.getNewId();
                System.out.println(id);
                out.println(id);
                return;
            }
            catch(SQLException sqle)
            {
                sqle.printStackTrace();
                rd=request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception ", sqle);
                rd.forward(request, response);
            }
        }
        else if (usid!=null)
        {
            try{
                boolean result=CandidateDao.userExist(usid);
                if(result)
                {
                    JSONObject j=new JSONObject();
                    j.put("username","exist");
                    out.println(j);
                    return;
                }
                String name=CandidateDao.getUserNameById(usid);
                ArrayList<String>city=CandidateDao.getCity();
                JSONObject json=new JSONObject();
                StringBuffer sb=new StringBuffer();
                for(String c: city)
                {
                    sb.append("<option value='"+c+"'>"+c+"</option>");
                }
                System.out.println(sb);
                if(name==null)
                   name="wrong"; 
                json.put("username", name);
                json.put("cities", sb.toString());
                out.println(json);
            }
            catch(SQLException sqle)
            {
                rd=request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception", sqle);
                rd.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
