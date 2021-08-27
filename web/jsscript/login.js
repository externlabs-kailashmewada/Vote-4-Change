let userid,password;

function connectUser()
{
    userid=$("#username").val();
    password=$("#password").val();
    if(validate()===false)
    {
        swal("Access Denied","Please enter userid/passord","error");
        return ;
    }
    let data={userid:userid,password:password};
    let xhr=$.post("LoginControllerServlet",data,processResponse);
    xhr.fail(handleError);
}
function processResponse(responseText)
{
    if(responseText.trim()==='error')
    {
        swal("Access Denied","Invalid userid/password","error"); 
    }
    else if(responseText.trim().indexOf("jsessionid")!==-1)
    {
        swal("Success","Login Successful","success").then((value)=>{
                window.location=responseText.trim();
        });    
    }
    else
    {
        swal("Access Denied","Some problem occurred:"+responseText,"error"); 
        
    }
}
function handleError(xhr)
{
    swal("Error","Problem in server communication:"+xhr.statusText,"error");
}
function validate()
{
    if(userid==="" || password==="")
    {
        swal("Error!","All fields are mandatory","error");
        return false;
    }
    return true;
}



