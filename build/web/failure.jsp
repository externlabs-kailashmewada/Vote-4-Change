<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
        return;      
    }
    String present=(String)request.getAttribute("present");
    System.out.println("Present value "+present);
    if(present!=null)
    {
     out.println("candidate alreay  of this party in this city");
     System.out.println("present ??????///////");
    }
    else{
        out.println("failed");
    }
    
    %>
