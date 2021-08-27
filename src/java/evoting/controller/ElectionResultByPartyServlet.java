/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;


import evoting.dao.VoteDAO;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ghazala
 */
public class ElectionResultByPartyServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            RequestDispatcher rd=null;
            HttpSession session=request.getSession();
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
              session.invalidate();
              response.sendRedirect("accessdenied.html");
              return;
            }
            try{
                Map<String,Integer>result=VoteDAO.getPartyResult();
               // System.out.println(result);
                Set s=result.entrySet(); 
                Iterator it=s.iterator();
                LinkedHashMap<String,String>vote=new LinkedHashMap<>();
                while(it.hasNext()){
                 Map.Entry <String,Integer> e=(Map.Entry)it.next();
                 String img=VoteDAO.getSymbol(e.getKey());
                 vote.put(e.getKey(),img);
               }
               int voteCount=VoteDAO.getVoteCount();
               // System.out.println(vote);
               request.setAttribute("result",result);
               request.setAttribute("party",vote);
               request.setAttribute("vote",voteCount);
               rd=request.getRequestDispatcher("showpartyresult.jsp");
               //rd.forward(request, response);
            }
             catch(Exception ex)
             {
                ex.printStackTrace();
                request.setAttribute("Exception", ex);
                rd=request.getRequestDispatcher("showexception.jsp");
            }
            finally
            {   if(rd!=null)
                rd.forward(request, response);
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
