     function redirectadministratorpage()
    {
        swal("Admin!" ,"Redirecting to Adim action page","success").then((value)=>{
           window.location="adminactions.jsp"; 
        });
    }
    
    function redirectvotingpage()
    {
        swal("Admin!" ,"Redirecting to Voting page","success").then((value)=>{
           window.location="VotingControllerServlet"; 
        });
    }
    
    
    function manageuser()
    {
        swal("Admin!" ,"Redirecting to User management page","success").then((value)=>{
           window.location="manageuser.jsp"; 
        });
    }
    
    function managecandidate()
    {
        swal("Admin!" ,"Redirecting to User candidate page","success").then((value)=>{
           window.location="managecandidate.jsp"; 
        });
    }
    function election()
    {
        var newdiv = document.createElement("div");
        newdiv.setAttribute("float", "left");
        newdiv.setAttribute("padding-left", "12px");
        newdiv.setAttribute("border", "2px solid red");
//        newdiv.innerHTML = "<h3>Result</h3>";
        newdiv.innerHTML=newdiv.innerHTML+"<button onclick='electionresult()'>Candidate Wise Result</button></br>";
        newdiv.innerHTML=newdiv.innerHTML+"<button onclick='partyresult()'>Party Wise Result</button>";
        var addcand = $("#button")[0];
        addcand.appendChild(newdiv);
    }
    function electionresult()
    {
        $.post("ElectionResultControllerServlet",null,function(responseText)
        {
            cleartext();
            swal("ResultFecthed!!!","Success","success");
            $("#result").html(responseText.trim());
        });
    }
    
    function partyresult()
    {
        $.post("PartyResultControllerServlet",null,function(responseText)
        {
            cleartext();
            swal("ResultFecthed!!!","Success","success");
            $("#result").html(responseText.trim());
        });
    }
    function showaddcandidateform()
    {
        removecandidateform();
        var newdiv = document.createElement("div");
        newdiv.setAttribute("id", "candidateform");
        newdiv.setAttribute("float", "left");
        newdiv.setAttribute("padding-left", "12px");
        newdiv.setAttribute("border", "2px solid red");
        newdiv.innerHTML = "<h3>Add New Candidate</h3>";
        newdiv.innerHTML =
          newdiv.innerHTML +
          "<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
          <table><tr><th>Candidate Id:</th><td><input type='text' id='cid'/></td></tr>\n\
          <tr><th>User Id:</th><td><input type='text' id='uid' onkeypress='return getdetails(event)'/></td></tr>\n\
          <tr><th>Name:</th><td><input type='text' id='cname' /></td></tr>\n\
          <tr><th>City:</th><td><select  id='city'></select></td></tr>\n\
          <tr><th>Party:</th><td><input type='text' id='party'/></td></tr>\n\
          <tr><td colspan='2'><input type='file' id='file' name='files' value='Select Image'/></td></tr>\n\
          <tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'/></th>\n\
          <th><input type='reset' value='Clear' onclick='clearData()' id='addcnd'/></th></tr>\n\</table></form>";
        newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
        var addcand = $("#result")[0];
        addcand.appendChild(newdiv);
        $("#candidateform").hide();
        $("#candidateform").fadeIn(3500);
        data = { id: "getid" };
        $.post("AddCandidateControllerServlet", data, function (responseText) {
          $("#cid").val(responseText);
          $("#cid").prop("disabled", true);
        });
    }
    function clearData()
    {
        $("#uid").val("");
        $("#cname").val("");
        $("#city").empty();
        $("#party").val("");
        $("#file").val("");
        data = { id: "getid" };
        $.post("AddCandidateControllerServlet", data, function (responseText) {
          $("#cid").val(responseText);
          $("#cid").prop("disabled", true);
        });
    }
    function showupdatecandidateform()
    {
      removecandidateform();
      var newdiv = document.createElement("div");
        newdiv.setAttribute("id", "candidateform");
        newdiv.setAttribute("float", "left");
        newdiv.setAttribute("padding-left", "12px");
        newdiv.setAttribute("border", "2px solid red");
        newdiv.innerHTML = "<h3>Update Candidate</h3>";
        newdiv.innerHTML =
          newdiv.innerHTML +
          "<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
          <table><tr><th>Candidate Id:</th><td><select id='cid'></select></td></tr>\n\
          <tr><th>User Id:</th><td><input type='text' id='uid''/></td></tr>\n\
          <tr><th>Name:</th><td><input type='text' id='cname' /></td></tr>\n\
          <tr><th>City:</th><td><select  id='city'></select></td></tr>\n\
          <tr><th>Party:</th><td><input type='text' id='party'/></td></tr>\n\
          <tr><td colspan='2'><input type='file' name='files' id='file' value='Select Image'/></td></tr>\n\
          <tr><th><input type='button' value='Update Candidate' onclick='updatecandidate()' id='addcnd'/></th>\n\
          <th><input type='reset' value='Clear' onclick='cleartext()' id='addcnd'/></th></tr>\n\</table></form>";
        newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
        var addcand = $("#result")[0];
        addcand.appendChild(newdiv);
        $("#candidateform").hide();
        $("#candidateform").fadeIn(3500);
        data = { data: "cid" };
        $.post("UpdateCandidateControllerServlet", data, function (responseText) {
          var idList=JSON.parse(responseText);
          $("#cid").append(idList.cid);
        });
        $("#cid").change(function(){
            var cid=$("#cid").val();
            if(cid==="")
            {
                swal("No Selection","Please select an id","error");
                return;
            }
            data = { data: cid };
            $.post("UpdateCandidateControllerServlet", data, function (responseText) {
                cleartext();
             var details=JSON.parse(responseText);
             console.log(details.city);
              $("#cid").val(details.cid);
              $("#uid").val(details.userid);
              $("#uid").prop("disabled", true);
              $("#cname").val(details.name);
              $("#cname").prop("disabled", true);
              $("#city").append(details.city);
              $("#party").val(details.party);
              $("#addresp").append(details.symbol);
            });
        })
        
    }
    function showcandidate()
    {
        removecandidateform();
        var newdiv = document.createElement("div");
        newdiv.setAttribute("id", "candidateform");
        newdiv.setAttribute("float", "left");
        newdiv.setAttribute("padding-left", "12px");
        newdiv.setAttribute("border", "2px solid red");
        newdiv.innerHTML = "<h3>Show Candidate</h3>";
        newdiv.innerHTML =newdiv.innerHTML+"<div id='heading'><div id='delete'>CandidateId:</div><div><select id='cid'></select></div></div>";
        newdiv.innerHTML=newdiv.innerHTML+"<br><span id='addresp'></span>";
    
        var addcand = $("#result")[0];
        addcand.appendChild(newdiv);
        $("#candidateform").hide();
        $("#candidateform").fadeIn(3500);
        data = { data: "cid" };
        $.post("ShowCandidateControllerServlet", data, function (responseText) {
          var idList=JSON.parse(responseText);
          $("#cid").append(idList.cid);
        });
        
        $("#cid").change(function(){
            var cid=$("#cid").val();
            if(cid==="")
            {
                swal("No Selection","Please select an id","error");
                return;
            }
            data = { data: cid };
            $.post("ShowCandidateControllerServlet", data, function (responseText) {
                cleartext();
             var details=JSON.parse(responseText);
              $("#addresp").append(details.details);
            });
        })
    }
    
    function deletecandidate()
    {
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
        $.post("RemoveNewControllerServlet", data, function (responseText) {
          var idList=JSON.parse(responseText);
          $("#cid").append(idList.cid);
        });
        
        $("#cid").change(function(){
            var cid=$("#cid").val();
            if(cid==="")
            {
                swal("No Selection","Please select an id","error");
                return;
            }
            data = { data: cid };
            $.post("RemoveNewControllerServlet", data, function (responseText) {
                cleartext();
             var details=JSON.parse(responseText);
              $("#addresp").append(details.details);
            });
        })
    }
    
    function removeCandidate()
    {
        data={cid:$("#cid").val()};
        console.log(data);
        $.post("RemoveCandidateControllerServlet",data,function(responseText){
            if(responseText.trim()==="success")
            {
                swal("Admin!","Candidate Info Deleted Successfully","success").then((value)=>{
                    deletecandidate();
                });
            }
            else
            {
                swal("Admin!","Candidate Info not Deleted Successfully","error").then((value)=>{
                    deletecandidate();
                });
            }
        });
    }
    function getdetails(e)
    {
        if(e.keyCode===13)
        {
            data={"uid":$("#uid").val()};
             $.post("AddCandidateControllerServlet", data, function(responseText){
                                         
                var obj=JSON.parse(responseText);
                let uname=obj.username;
                if(uname==="exist")
                {
                    swal("Admin!","Candidate Info Already Exist","error").then((value)=>{
                        showaddcandidateform();
                    });
                }
                 let city=obj.cities;
                if(uname==="wrong")
                {
                    swal("Wrong Aadhar No.","Aadhar No. is invalid","error");
                    return;
                }

                $("#cname").val(obj.username);
                $("#city").empty();
                $("#city").append(city);
                $("#cname").prop("disabled",true);
             });
        }
    }
    
    function cleartext()
    {
        $("#addresp").html("");
    }
    
    function addcandidate()
    {
        var form=$("#fileUploadForm")[0];
        var data=new FormData(form); //image copy ho rahi h
//        alert(data);
        var cid=$("#cid").val();
        var uid=$("#uid").val();
        var cname=$("#cname").val();
        var city=$("#city").val();
        var party=$("#party").val();
        data.append("cid",cid);
        data.append("uid",uid);
        data.append("cname",cname);
        data.append("city",city);
        data.append("party",party);
        $.ajax({
            type:"POST",
            enctype:'multipart/form-data',
            url:"AddNewCandidateServlet",
            data:data,
            processData:false,
            contentType:false,
            cache:false,
            timeout:60000000,
            success:function(data){
                
                swal("Admin!","Candidate Info Added Successfully","success").then((value)=>{
                    showaddcandidateform();
                });
            },
            error:function (e){
                swal("Admin!",e,"error");
            }
        });  
    }
    
    function updatecandidate()
    {
        var form=$("#fileUploadForm")[0];
        var data=new FormData(form); //image copy ho rahi h
        var cid=$("#cid").val();
        var uid=$("#uid").val();
        var cname=$("#cname").val();
        var city=$("#city").val();
        var party=$("#party").val();
        data.append("cid",cid);
        data.append("uid",uid);
        data.append("cname",cname);
        data.append("city",city);
        data.append("party",party);
        $.ajax({
            type:"POST",
            enctype:'multipart/form-data',
            url:"UpdateNewCandidateServlet",
            data:data,
            processData:false,
            contentType:false,
            cache:false,
            timeout:60000000,
            success:function(data){
                
                swal("Admin!","Candidate Info Update Successfully","success").then((value)=>{
                    showupdatecandidateform();
                });
            },
            error:function (e){
                swal("Admin!",e,"error");
            }
        });  
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
    