
function electionresult(){
    window.location="resultoption.jsp";
}
function redirectadministratorpage()
{
    swal("Admin!","Redirecting To Admin Actions Page!","success").then(value=>{
        window.location="adminactions.jsp";
    });
}
function redirectvotingpage()
{
    swal("Admin!","Redirecting To Voting Controller Page!","success").then(value=>{
        window.location="VotingControllerServlet";
    });
}
function manageuser()
{
    swal("Admin!","Redirecting To User Management Page!","success").then(value=>{
        window.location="manageuser.jsp";
    });
}
function managecandidate()
{
    swal("Admin!","Redirecting To Candidate Management Page!","success").then(value=>{
        window.location="managecandidate.jsp";
    });
}
function showaddcandidateform()
{
removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px ");
newdiv.innerHTML="<h3>Add New Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
<table><tr><th>Candidate Id:</th><td><input type='text' id='cid'></td></tr>\n\
<tr><th>User Id:</th><td><select id='uid' onchange='getdetails(event)'></select></td></tr>\n\
<tr><th>Candidate Name:</th><td><input type='text' id='cname'></td></tr>\n\
<tr><th>City:</th><td><select id='city'></select></td></tr>\n\
<tr><th>Party:</th><td><input type='text' id='party'></td></tr>\n\
<tr><td colspan='2'><input type='file' name='files' id='file' value='Select Image'></td></tr>\n\
<tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th>\n\
<th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
var addcand=$("#result")[0];
addcand.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
 data={id:"getid"};
    $.post("AddCandidateControllerServlet",data,function(responseText)
    {
        
     var data=JSON.parse(responseText);
     console.log("text is "+data);
            let cid=data.cid;
            let id=data.id;
            console.log("cid is ",cid);
             console.log("uid is ",id);
    $("#cid").val(cid);
     $("#uid").append(id);
    $('#cid').prop("disabled",true)
    });
   
}

function clearText()
{
    $("#addresp").html("");
     $("#cid").prop("disabled",false);
}
function getdetails(e)
{
   // if(e.keyCode===13)
    {
        data={uid:$("#uid").val()};
        $.post("AddCandidateControllerServlet",data,function(responseText)
        {
            let details=JSON.parse(responseText);
            let city=details.city;
            let uname=details.username;
            if(uname==="wrong"){
               swal("Wrong Adhar!","Adhar no not found in DB","error");
                return;
            }
            else
            {
                $("#cname").val(uname);
                $("#city").empty();
                $("#city").append(city);
                $("#cname").prop("disabled",true);
                $("#cname").val(uname);
                
            }
        });
    }
}
function addcandidate()
{   
   if( validate()===false)
        return ;
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    alert("data is: "+data);
    var cid=$("#cid").val();
    var cname=$("#cname").val();
    var city=$("#city").val();
    var party=$("#party").val();
    var uid=$("#uid").val();
    data.append("cid",cid);
    data.append("uid",uid);
    data.append("cname",cname);
    data.append("party",party);
    data.append("city",city);
    $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "AddNewCandidateControllerServlet",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
          /*  success: function (data) {
                str=data+"....";
                   swal("Admin!", str, "success").then((value)=>
                   {
                   showaddcandidateform();
                   });
            },*/

            success: function (data) {
               str=data.trim();
                console.log("responseText in added "+str);
             if(str==="success")
                {
                  swal("Admin!", str, "success").then((value)=>{
                  showaddcandidateform();
                  });
                }
             else if(str==="failed")
                {
                 swal("Admin!","candidate not added", "error");
                 }
             else  
                 {
                   swal("Admin!","candidate alreay  of this party in this city", "error");
                   showaddcandidateform();
                 }
//                $("#addresp").text(str).css("color","green").css("font-weight","bold");
//                window.setTimeout(function(){showaddcandidateform();},5000);
            },
            error: function (e) {
                swal("Admin!", e, "error");
                }
        });
}
function validate()
{
     if($("#uid").val()==="")
     { swal("Access Denied","Please enter userid","error");
        return false;}
     else if($("#party").val()==="" )
     {   swal("Access Denied","Please enter party name","error");
        return false;}
    else if($("#file")[0].files.length===0)
        {   swal("Access Denied","Please select party logo","error");
        return false;}
    else if($("#city").val()==="" )
     {   swal("Access Denied","Please enter city name","error");
        return false;}
    return true;
}
function removecandidateForm()
{
    var contdiv=document.getElementById("result");
    var formdiv=document.getElementById("candidateform");
    if(formdiv!==null)
    {
        $("#candidateform").fadeOut("3500");
        contdiv.removeChild(formdiv);   
    }
}

function showcandidate()
{
removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px red");
newdiv.innerHTML="<h3>Show Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<div style='color:white; font-weight:bold'>Candidate Id:</div></div><select id='cid'></select></div>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
//newdiv.innerHTML=newdiv.innerHTML+"<br><span id='image'></span>";
var addPrd=$("#result")[0];
addPrd.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
data={data:"cid"};
  $.post("ShowCandidateControllerServlet",data,function(responseText){
       var cidlist=JSON.parse(responseText);
      $('#cid').append(cidlist.cids);  
      });


$("#cid").change(function()
{
     var cid=$("#cid").val();
     alert("Sel id:"+cid);
     if(cid==='')
     {
         swal("No selection!","Please select an id ","error");
         return;
     }

     data={data:cid};
    $.post("ShowCandidateControllerServlet",data,function(responseText)
    {
        clearText();
        var details=JSON.parse(responseText);
        $("#addresp").append(details.subdetails);
      //  $("#image").append(details.image);
    });
     });
}
function electionresult2()
{
    $.post("ElectionResultControllerServlet",null,function(responseText)
    {
        swal("Result Fetched!","Success","success");
      //  alert($("#result"));
        $("#result").html(responseText.trim());
    });
}

function deletecandidatedetails()
{
 removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px red");
newdiv.innerHTML="<h3>Delete Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<div style='color:white; font-weight:bold'>Candidate Id:</div></div><select id='cid'></select></div>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
//newdiv.innerHTML=newdiv.innerHTML+"<br><span id='image'></span>";
var addPrd=$("#result")[0];
addPrd.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
data={data:"cid"};
  $.post("DeleteCandidateControllerServlet",data,function(responseText){
       var cidlist=JSON.parse(responseText);
       console.log(cidlist);
      $('#cid').append(cidlist.cids);  
      });


$("#cid").change(function()
{
     var cid=$("#cid").val();
     alert("Sel id:"+cid);
     if(cid==='')
     {
         swal("No selection!","Please select an id ","error");
         return;
     }

     data={data:cid};
   $.post("DeleteCandidateControllerServlet",data,function(responseText)
    {
        clearText();
        var details=JSON.parse(responseText);
        $("#addresp").append(details.subdetails);
      //  $("#image").append(details.image);
    });
     });
}

function deleteCandidate()
{ 
    let cid=$("#cid").val();
    alert("Sel id in delet fun :"+cid);
    let data={delete:cid};
   $.post("DeleteCandidateControllerServlet",data,function (responseText)
   {  clearText();
      responseText=responseText.trim();
      if(responseText==="success"){
            console.log("success");
                swal("Admin!","candidate Delete sucessfully", "success").then((value)=>{
                     deletecandidatedetails();
                });
                    
                
            }
            else{
                console.log("fail");
                 swal("Admin!","candidate not Deleted", "error")
            }
    });
    }
    
  function showupdatecandidateform()
  {
     removecandidateForm();
    //swal("sucess","form swaing","success")
   console.log("show candidate form start");
   var newdiv=document.createElement("div");
   newdiv.setAttribute("id","candidateform");
   newdiv.setAttribute("float","left");
   newdiv.setAttribute("padding-left","12px");
   newdiv.setAttribute("border","solid 2px red");
   newdiv.innerHTML="<h3>Update Candidate</h3>";
   newdiv.innerHTML=newdiv.innerHTML+"<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>>\n\
   <table><tr><th>Candidate Id:</th><td><select id='cid' onchange='showupdetails()'></select></td></tr>\n\
   <tr><th><label for='name'>UserId</label></th><td><input type='text' id='userid'></td></tr>\n\
   <tr><th><label for='name'>UserName</label></th><td><input type='text' id='name'></td></tr>\n\
   <tr><th><label for='party'>party  </label></th><td><input type='text' id='party'></td></tr>\n\
   <tr><th><label for='city'>city  </label></th><td><input type='text' id='city'></td></tr>\n\
\n\<tr><th><label for='image'>image </label></th><td><div id='image' style='width:300px;height:200px;border:solid 2px red'></div>\n\
    <tr><td colspan='2'><input type='file' name='files' id ='file' value='Select Image'></td></tr>\n\
   <tr><th><input type='button' value='Update Candidate' onclick='updateCandidate()' id='addcnd'></th>\n\
   <th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
   newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
   console.log("show candidate form end");
   var addcand=$("#result")[0];
   addcand.appendChild(newdiv);
   $("#candidateform").hide();
   $("#candidateform").fadeIn("3500");
   data={data:"cid"};
   $.post("UpdateCandidateControllerServlet",data,function(responseText){
    console.log("response text is "+responseText)
    var details=JSON.parse(responseText);
            let cid=details.cids;
            console.log(cid);
            let id=$("#cid");
            console.log("candidate id"+id);
            $("#cid").append(cid);
             console.log("before "+id);
});
}
   
function showupdetails()
{
  let select=$("#cid").val();
  console.log("value of selected item "+select);
   if(select==='Choose Id')
   {
    swal("Error","plzz select candidate id","error");
    return;
   }
  data={data:select};
  $.post("UpdateCandidateControllerServlet",data,function(responseText){
     let details=JSON.parse(responseText);
     let userid=details.userid;
     let name=details.name;
     let city=details.city;
     let party=details.party;
      let src=details.src;
    // console.log("name is"+name);
    // console.log(""+city);
    // console.log(party);
     $("#userid").val(userid);
     $("#name").val(name);
     $("#city").val(city);
     $("#party").val(party);
     let img=document.getElementById("image");
     img.innerHTML=src;
     /*src=$("#image").val($("file").val());   hit and trial for img change adnan
     console.log(src);
     $("#image").innerHTML=src;  */  
     $("#userid").prop("disabled",true);
     $("#cid").prop("disabled",true);
     $("#name").prop("disabled",true);
     
   // console.log(src);
    /* // DOM Method not by jquery ///
    let usid=document.getElementById("userid");
    let username=document.getElementById("name");
    let usercity=document.getElementById("city");
    let userparty=document.getElementById("party");
    let img=document.getElementById("image");
    username.value=name;
    usid.value=userid;
    usercity.value=city;
    userparty.value=party;
    img.innerHTML=src;
  //  usid.prop("disabled",true);*/
   
});

}
function updateCandidate(){
    if( validate()===false)
        return ;
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    var cid=$("#cid").val();
    var cname=$("#name").val();
    var city=$("#city").val();
    var party=$("#party").val();
    var uid=$("#userid").val();
    data.append("cid",cid);
    data.append("uid",uid);
    data.append("cname",cname);
    data.append("party",party);
    data.append("city",city);
    $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "UpdateAddCandidateControllerServlet",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) 
            {
              str=data.trim();
              console.log("responseText in added "+str);
              if(str==="success")
                {
                  swal("Admin!","candidate Updated sucessfully", "success").then((value)=>
                  {
                  showupdatecandidateform();
                  })}
              else
                 {
                   swal("Admin!","candidate not Updated", "error");
                  }
             },
             error: function (e) 
              {
                 swal("Admin!", e, "success");
              }
            });
 }



 