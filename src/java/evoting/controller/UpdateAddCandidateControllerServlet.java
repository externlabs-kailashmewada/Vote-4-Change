/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author Ghazala
 */
public class UpdateAddCandidateControllerServlet extends HttpServlet {

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
       HttpSession sess=request.getSession();
            long l=0;
            String uid=(String)sess.getAttribute("userid");
            if(uid==null)
            {
                sess.invalidate();
                response.sendRedirect("Accessdenied.html");
              }
            RequestDispatcher rd=null;
            try{
                DiskFileItemFactory df=new DiskFileItemFactory();
                ServletFileUpload sfu=new ServletFileUpload(df);
                ServletRequestContext src=new ServletRequestContext(request);
                List<FileItem>multilist=sfu.parseRequest(src);
                ArrayList <String>objValues=new ArrayList<>();
                InputStream inp=null;
                for(FileItem fit:multilist)
                {
                 if(fit.isFormField())
                 {
                    String fname=fit.getFieldName();
                    String value=fit.getString();
                    System.out.println("Inside if");
                    System.out.println(fname+":"+value);
                    objValues.add(value);
                   
                 }
                 else
                 {
                    inp=fit.getInputStream();
                    l=fit.getSize();
                    String key=fit.getFieldName();
                    String fileName=fit.getName();
                    System.out.println("Inside else");
                    System.out.println(key+":"+fileName);
                  }
                }
                CandidateDTO candidate=new CandidateDTO(objValues.get(0),objValues.get(3),objValues.get(4),objValues.get(1),inp);
                 boolean result=CandidateDAO.updateCandidate(candidate,l);
                if(result)
                     rd=request.getRequestDispatcher("success.jsp");
                 else
                     rd=request.getRequestDispatcher("failure.jsp");
               }
              
               catch(Exception ex)
               {
                 rd=request.getRequestDispatcher("Error.jsp");
                 request.setAttribute("exception", ex);
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
