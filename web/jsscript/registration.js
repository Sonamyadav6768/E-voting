let adhar,password,cpassword,username,city,email,mobile,address;

function addUser()
{
    username=$("#username").val();
    password=$("#password").val();
    cpassword=$("#cpassword").val();
    adhar=$("#adhar").val();
    city=$("#city").val();
    email=$("#email").val();
    mobile=$("#mobile").val();
    address=$("#address").val();
    if(validateUser()===true)
    {
        if(password!==cpassword)
        {
            swal("Error!","Password do not match","error");
            return false;
        }
        else
        {
            if(validateEmail()===false)
                return false;
            if(validateMobile()==false)
                return false;
            let data={username:username,
                     password:password,
                     city:city,
                     address:address,
                     userid:adhar,
                     email:email,
                     mobile:mobile
                    };
            let xhr=$.post("RegistrationServlet",data,processResponse);
            xhr.error(handleError);
        }
    }
}

function validateMobile()
{
    if(mobile.length!==10)
    {
        swal("Error!","Enter 10 digit mobile number","error");
        return false;
    }
    for(let i=0;i<mobile.length;i++)
    {
        if(mobile.charAt(i)<'0' || mobile.charAt(i)>'9')
        {
            swal("Error!","Invalid Mobile Number","error");
            return false;
        }
    }
    return true;
}

function processResponse(responseText,textStatus,xhr)
{
    let str=responseText.trim();
    if(str==="success")
    {
        swal("Success!","Registration done successfully!!","success");
        setTimeout(redirect,5000);
    }
    else if(str==="uap")
        swal("Error!","Sorry ! userid already present","error");
    else
        swal("Error!","Registration failed!","error");
}
function handleError(xhr)
{
     swal("Error!","Problem in server communication"+xhr.statusText,"error");
}

function validateUser()
{
    if(username.length===0 || password.length===0 || cpassword.length===0 || adhar.length===0 || city.length===0 || email.length===0 ||
            mobile.length===0 || address.length===0)
    {
        swal("Error!","All feilds are mandatory","error");
        return false;
    }
    return true;
}

function validateEmail()
{
    let attheratepos=email.indexOf("@");
    let dotpos=email.indexOf(".");
    if(attheratepos<1 || dotpos<attheratepos+2 || dotpos+2>=email.length)
    {
        swal("Error!","Please enter a valid email","error");
        return false;
    }
    return true;
}

function redirect()
{
    window.location="login.html";   
}

function redirectUser()
{
    window.location="login.html";   
}
function redirectRegistration()
{
    window.location="registration.html";   
}