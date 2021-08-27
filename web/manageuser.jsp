<%-- 
    Document   : manageuser
    Created on : Jun 29, 2021, 3:21:12 PM
    Author     : Ghazala
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title> manage candidate</title>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/admin.css" rel="stylesheet">   
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/result.css" rel="stylesheet">
        <script src="jsscript/jquery.js"></script>    
        <script src="jsscript/adminuser.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script> 
    </head>
    <body>
        <%
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
                response.sendRedirect("AccessDenied.html");
                return;
            }
            session.setAttribute("userid",userid);
            StringBuffer displayBlock=new StringBuffer("");
            displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>"+
            "<div class='subcandidate'>Admin Actions Page</div><br><br>");
            displayBlock.append("<div class='logout'><a href='logout.jsp'>logout</a></div></div>");
            displayBlock.append("<div class='container'>");
            displayBlock.append("<div id='dv3' onclick='showUser()'>"
                    + "<img src='images/show.png' height='300px' width='300px'>"
                    + "<br><h3>Show User</h3></div>");
             displayBlock.append("<div id='dv4' onclick='deleteuserdetails()'>"
                    + "<img src='images/delete.jpg' height='300px' width='300px'>"
                    + "<br><h3>Delete User</h3></div>");
             displayBlock.append("</div>");
             displayBlock.append("<br><br><div align='center' id='result'></div>");
             out.println(displayBlock);


            %>
    </body>
</html>

