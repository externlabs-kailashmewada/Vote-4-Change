<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONObject"%>
<%@page import="evoting.dto.CandidateDetails"%>
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
    if(result!=null && result.equalsIgnoreCase("candidateList"))
    {
        ArrayList <String> candidateId=( ArrayList<String> )request.getAttribute("candidateId");
        displayBlock.append("<option value=''>Choose Id</option>");
        for(String c:candidateId)
        {
          displayBlock.append("<option value='"+c+"'>"+c+"</option>");
        }
       JSONObject json=new JSONObject();
       json.put("cids", displayBlock.toString());
       out.println(json);
    }
    else if(result!=null && result.equals("details"))
    {
        CandidateDetails candidate=(CandidateDetails)request.getAttribute("candidate");
        String str="<img src='data:image/jpg;base64,"+candidate.getSymbol()+"' style='width:300px;height:200px;'/>";

         displayBlock.append("<br><br><table>"
                            +"<tr><th>User Id:</th><td>"+candidate.getUserId()+"</td></tr>"
                            +"<tr><th>Candidate Name:</th><td>"+candidate.getCandidateName()+"</td></tr>"
                            +"<tr><th>City:</th><td>"+candidate.getCity()+"</td></tr>"
                            +"<tr><th>Party:</th><td>"+candidate.getParty()+"</td></tr>"
                            +"<tr><th>Symbol:</th><td id='image'>"+str+"</td></tr>"
                           + " <tr><th><input type='button' value='Delete Candidate' onclick='deleteCandidate()' id='addcnd'></th>"
                            +"</table>");
         
        JSONObject json=new JSONObject();
       // json.put("image", str);
        json.put("subdetails", displayBlock.toString());
        out.println(json);
    }
     // System.out.println("in delete candidate");
     // System.out.println(displayBlock);
    
%>

