function addvote(event)
{
    var cid=$('input[type=radio][name=flat]:checked').attr('id');
    let data={"candidateid":cid};
    $.post("AddVoteControllerServlet",data,processResponse);
}

function processResponse(responseText)
{
    let str=responseText.trim();
    if(str==="success")
    {
        swal("Success","Vote casted successfully!!!","success").then((value)=>{
            window.location="votingresponse.jsp";
        });
    }
    else
    {
        swal("Error","Vote can't be cast!!","error").then((value)=>{
            window.location="votingresponse.jsp";
        });
    }
}

