<%-- 
    Document   : loginresponse
    Created on : May 29, 2021, 8:28:54 PM
    Author     : Ghazala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid=(String)request.getAttribute("userid");
    String result=(String)request.getAttribute("result");
    if(userid!=null&&result!=null)
    {
     HttpSession sess=request.getSession();
     sess.setAttribute("userid", userid);
     String url="";
        if(result.equalsIgnoreCase("Admin"))
        {
         url="AdminControllerServlet;jsessionid="+session.getId();
        }
        else
        {
         url="VotingControllerServlet;jsessionid="+session.getId();
        }
        out.println(url);
    }
    else
    {
      out.println("error");
    }
%>

