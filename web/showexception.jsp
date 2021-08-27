<%-- 
    Document   : showexception
    Created on : May 15, 2021, 6:40:45 PM
    Author     : Ghazala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
   Exception ex=(Exception)request.getAttribute("Exception");
   System.out.println("Exception is :"+ex);
   out.println("Some Exception Occured! :"+ex.getMessage());
%>
