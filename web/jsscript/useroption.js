function showuserform()
{
    $.post("ShowUserControllerServlet",null,function(responseText){
       $("#result").html(responseText); 
    });
}

function removeuserform()
{
    $("#result").html("");
    removecandidateform();
        var newdiv = document.createElement("div");
        newdiv.setAttribute("id", "candidateform");
        newdiv.setAttribute("float", "left");
        newdiv.setAttribute("padding-left", "12px");
        newdiv.setAttribute("border", "2px solid red");
        newdiv.innerHTML = "<h3>Delete Candidate</h3>";
        newdiv.innerHTML =newdiv.innerHTML+"<div id='heading'><div id='delete'>CandidateId:</div><div><select id='cid'></select></div></div>";
        newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
    
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = { data: "cid" };
    $.post("RemoveUserControllerServlet", data, function (responseText) {
      var idList=JSON.parse(responseText);
      $("#cid").append(idList.uid);
    });

    $("#cid").change(function(){
        var cid=$("#cid").val();
        if(cid==="")
        {
            swal("No Selection","Please select an id","error");
            return;
        }
        data = { data: cid };
        $.post("RemoveUserControllerServlet", data, function (responseText) {
            cleartext();
         var details=JSON.parse(responseText);
          $("#addresp").append(details.details);
        });
    })
}

  function removecandidateform()
{
    var contdiv=document.getElementById("result");
    var formdiv=document.getElementById("candidateform");
    if(formdiv!=null)
    {
        $("#candidateform").fadeOut("3500");
        contdiv.removeChild(formdiv);
    }
}

function cleartext()
{
    $("#addresp").html("");
}

function removeUser()
{
    var temp=$("#cid").val();
    data={cid:temp};
    alert(temp);
    console.log(temp);
    $.post("RemoveNewUserControllerServlet",data,function(responseText){
        if(responseText.trim()==="success")
        {
            swal("Admin!","User Info Deleted Successfully","success").then((value)=>{
                removeuserform();
            });
        }
        else
        {
            swal("Admin!","User Info not Deleted Successfully","error").then((value)=>{
                removeuserform();
            });
        }
    });
}