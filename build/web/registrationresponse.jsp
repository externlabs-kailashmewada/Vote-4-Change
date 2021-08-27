<%-- 
    Document   : registrationresponse
    Created on : May 15, 2021, 6:35:04 PM
    Author     : Ghazala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% boolean userfound=(boolean)request.getAttribute("userfound");
   boolean result=(boolean)request.getAttribute("result");
   if(userfound==true)
       out.println("uap");
   else if(result==true)
       out.println("success");
   else
   out.println("error");
%>
