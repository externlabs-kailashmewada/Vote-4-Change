<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="evoting.dto.UserDetails"%>
<%@page import="java.util.ArrayList"%>
<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        response.sendRedirect("accessdenied.html");
        return;
    }
    String result=(String)request.getAttribute("result");
    System.out.println(result);
    StringBuffer displayBlock=new StringBuffer("");
    if(result!=null && result.equalsIgnoreCase("UserList"))
    {
        ArrayList <String> adharId=( ArrayList<String> )request.getAttribute("adharid");
        displayBlock.append("<option value=''>Choose Id</option>");
        for(String c:adharId)
        {
          displayBlock.append("<option value='"+c+"'>"+c+"</option>");
        }
       JSONObject json=new JSONObject();
       json.put("uids", displayBlock.toString());
       out.println(json);
    }
    else if(result!=null && result.equals("details"))
    {
        UserDetails user=(UserDetails)request.getAttribute("user");
        displayBlock.append("<br><br><table>"
                            +"<tr><th>User Id:</th><td>"+user.getUserid()+"</td></tr>"
                            +"<tr><th>User Name:</th><td>"+user.getUsername()+"</td></tr>"
                            +"<tr><th>Email:</th><td>"+user.getEmail()+"</td></tr>"
                            +"<tr><th>Mobile No:</th><td>"+user.getMobile()+"</td></tr>"
                            +"<tr><th>City:</th><td>"+user.getCity()+"</td></tr>"
                            +"<tr><th>Address:</th><td>"+user.getAddress()+"</td></tr>"
                            +"<tr><th>Gender:</th><td>"+user.getGender()+"</td></tr>"
                           + " <tr><th><input type='button' value='Delete User' onclick='deleteuser()' id='addcnd'></th>"
                            +"</table>");
         
        JSONObject json=new JSONObject();
        json.put("subdetails", displayBlock.toString());
        out.println(json);
    }
     // System.out.println("in delete candidate");
     // System.out.println(displayBlock);
    
%>

