let userid;
let password;
function connectUser()
{
    userid=$("#username").val();
    password=$("#password").val();
    
    if(validateInput()===true)
    {
        let data={userid:userid,
                  password:password};
        let xhr=$.post("LoginServlet",data,processResponse);
        xhr.fail(handleError);
    }
    
}

function processResponse(responseText)
{
    if(responseText.trim()==="error")
    {
         swal("Access denied!","Invalid userid/password","error");
    }
    else if(responseText.trim().indexOf("jsessionid")!==-1)
    {
        swal("Success","Login Succcessfull","success").then((value)=>{
            window.location=responseText.trim();
        });
    }
    else
        swal("Access denied!","Some problem occured!!"+responseText,"error");
}
function handleError(xhr)
{
     swal("Error!","Problem in server communication"+xhr.statusText,"error");
}

function validateInput()
{
    if(userid==="" || password==="")
    {
         swal("Error!","All feilds are mandatory","error");
        return false;
    }
    return true;
}