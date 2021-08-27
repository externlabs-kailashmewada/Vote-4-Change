<% session.invalidate();
System.out.println("Logout from logout.jsp");
 response.sendRedirect("login.html");%>
