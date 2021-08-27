function showCityWise(){
     
    console.log("result showing");
    $.post("ElectionResultControllerServlet",null,function(responseText){
        swal("Result Fetched!","Success","success");
      //  alert($("#result"));
        $("#result").html(responseText.trim());
      /* var addresult=document.getElementById("data");
    console.log("add candidate"+addresult);
    addresult.innerHTML="";
    addresult.innerHTML=addresult.innerHTML+responseText;*/
    });
}
function showPartyWise(){
    console.log("result showing party wise");
    $.post("ElectionResultByPartyServlet",null,function(responseText){
         swal("Result Fetched!","Success","success");
        $("#result").html(responseText.trim());
    });
}
function showGenderWise(){
    console.log("result showing party wise");
    $.post("ElectionResultByGenderControllerServlet",null,function(responseText){
        swal("Result Fetched!","Success","success");
        $("#result").html(responseText.trim());
      
    });
}


