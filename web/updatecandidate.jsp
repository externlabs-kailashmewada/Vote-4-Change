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
        System.out.println("Cand Id from update details  "+candidateId);
       JSONObject json=new JSONObject();
       json.put("cids", displayBlock.toString());
       out.println(json);
    }
    else{
   CandidateDetails cd=(CandidateDetails)request.getAttribute("candidate");
   if(cd!=null){
   String name=cd.getCandidateName();
   String city=cd.getCity();
   String usid=cd.getUserId();
   String party=cd.getParty();
   String cid=cd.getCandidateId();
   String image="<img src='data:image/jpg;base64,"+cd.getSymbol()+"'style='width:300px;height:200px;'/>";
   JSONObject json=new JSONObject();
   json.put("name",name);
   json.put("userid",usid);
   json.put("city",city);
   json.put("party",party);
   json.put("src",image);
   out.println(json);
   }
   else{
       out.println("null object");
   }
    }
     // System.out.println("in update candidate");
     // System.out.println(displayBlock);
    
%>

