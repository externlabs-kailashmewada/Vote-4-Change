function showUser()
{
    console.log("result showing")
    $.post("ShowUserControllerServlet",function(responseText){
       var addcand=document.getElementById("result");
    console.log("add candidate"+addcand+"responseText:"+responseText);
    addcand.innerHTML="";
    addcand.innerHTML=addcand.innerHTML+responseText;
    });
}

function deleteuserdetails()
{
removecandidateForm();
var newdiv=document.createElement("div");
newdiv.setAttribute("id","candidateform");
newdiv.setAttribute("float","left");
newdiv.setAttribute("padding-left","12px");
newdiv.setAttribute("border","solid 2px");
newdiv.innerHTML="<h3>Delete Candidate</h3>";
newdiv.innerHTML=newdiv.innerHTML+"<div style='color:white; font-weight:bold'>UserId Id:</div></div><select id='uid' ></select></div>";
newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
var addPrd=$("#result")[0];
addPrd.appendChild(newdiv);
$("#candidateform").hide();
$("#candidateform").fadeIn(3500);
data={data:"uid"};
  $.post("DeleteUserControllerServlet",data,function(responseText){
       var uidlist=JSON.parse(responseText);
       console.log(uidlist);
      $('#uid').append(uidlist.uids);  
      });


$("#uid").change(function()
{
     var uid=$("#uid").val();
     alert("Sel id:"+uid);
     if(uid==='')
     {
         swal("No selection!","Please select an id ","error");
         return;
     }

     data={data:uid};
   $.post("DeleteUserControllerServlet",data,function(responseText)
    {
        var details=JSON.parse(responseText);
        $("#addresp").append(details.subdetails);
    });
     });
}

function deleteuser()
{
     let select=$("#uid").val();
     console.log("value of selected item "+select);
     if(select==='Choose Id')
     {
       swal("Error","plzz select User id","error");
       return;
     }
     data={delete:select};
     $.post("DeleteUserControllerServlet",data,function(responseText){
     console.log(responseText)
      if(responseText.trim()==="success"){
            console.log("success")
                swal("Admin!","User deleted sucessfully", "success").then((value)=>{
                     deleteuserdetails();
                });                   
            }
            else{
                 console.log("fail")
                 swal("Admin!","user not Deleted", "error")
            }
 });

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


