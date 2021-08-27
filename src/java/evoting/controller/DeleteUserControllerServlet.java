/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.UserDetailsDAO;
import evoting.dto.UserDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class DeleteUserControllerServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            RequestDispatcher rd=null;
        HttpSession session=request.getSession();
        String userid=(String)session.getAttribute("userid");
        if(userid==null)
        {
          session.invalidate();
          response.sendRedirect("accessdenied.html");
           return;
        }
        String data=(String)request.getParameter("data");
        String delete=(String)request.getParameter("delete");
        System.out.println("Data value : "+ data);
        System.out.println( "Delete value : "+delete);
        
        try
        {
           if(data!=null&&data.equals("uid"))
           {
             ArrayList<String> adharid=UserDetailsDAO.getAllUserId();
             request.setAttribute("adharid", adharid);
             request.setAttribute("result", "UserList");
              rd=request.getRequestDispatcher("deleteuser.jsp");
           }
           else if(delete!=null )
           {
              // System.out.println(" fro else if Candidate id fetch from Adminoprion.js"+delete);
                boolean result=UserDetailsDAO.deleteUser(delete);
               //System.out.println(result);
               if(result==true)
                  rd=request.getRequestDispatcher("success.jsp");
               else
                   rd=request.getRequestDispatcher("failure.jsp");
              
           }
           else
           {
            UserDetails user=UserDetailsDAO.showUserDetails(data);
            request.setAttribute("result", "details");
            request.setAttribute("user", user);
             rd=request.getRequestDispatcher("deleteuser.jsp");
           }
          
       }
       catch(Exception e)
       {
          request.setAttribute("exception", e);
          rd=request.getRequestDispatcher("showexception.jsp");
          e.printStackTrace();
       }
       finally
       { if(rd!=null)
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
