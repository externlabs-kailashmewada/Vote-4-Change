<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
        return;      
    }
    System.out.println(" Present jsp ?????????????????????????   ");
    out.println("present");
    %>
