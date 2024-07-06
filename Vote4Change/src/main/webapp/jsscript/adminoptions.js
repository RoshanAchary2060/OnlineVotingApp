function redirectadministratorpage() {
    swal("Admin!", "Redirecting To Admin Actions Page!", "success").then(value => {
        window.location = "adminactions.jsp";
    });
}
function redirectvotingpage() {
    swal("Admin!", "Redirecting To VotingController Page!", "success").then(value => {
        window.location = "VotingControllerServlet";
    });
}
function manageuser() {
    swal("Admin!", "Redirecting To User Management Page!", "success").then(value => {
        window.location = "manageuser.jsp";
    });
}
function managecandidate() {
    swal("Admin!", "Redirecting To Candidate Management Page!", "success").then(value => {
        window.location = "managecandidate.jsp";
    });
}
function electionresult() {
    swal("Admin!", "Redirecting To Election Result Page!", "success").then(value => {
        window.location = "";
    });
}
function removecandidateForm() {

}
function showaddcandidateform() {
    removecandidateForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Add New Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
    <table><tr><th>Candidate Id:</th><td><input type='text' id='cid'></td></tr>\n\
    <tr><th>User Id:</th><td><input type='text' id='uid' onkeypress='return getdetails(event)'></td></tr>\n\
    <tr><th>Candidate Name:</th><td><input type='text' id='cname'></td></tr>\n\
    <tr><th>City:</th><td><select id='city'></select></td></tr>\n\
    <tr><th>Party:</th><td><input type='text' id='party'></td></tr>\n\
    <tr><td colspan='2'><input type='file' name='files' value='Select Image'></td></tr>\n\
    <tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th>\n\
    <th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $('#result')[0];
    addcand.appendChild(newdiv);
    $('#candidateform').hide();
    $('#candidateform').fadeIn(3500);
    data = {id: 'getid'};
    $.post("AddCandidateControllerServlet", data, function (responseText) {
        $('#cid').val(responseText);
        $('#cid').prop("disabled", true);
    });
}
function addcandidate() {
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    alert(data);
    var cid = $('#cid').val();
    var cname = $('#cname').val();
    var city = $('#city').val();
    var party = $('#party').val();
    var uid = $('#uid').val();
    data.append('cid', cid);
    data.append('uid', uid);
    data.append('cname', cname);
    data.append('party', party);
    data.append('city', city);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: 'AddNewCandidateControllerServlet',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            str = data + "....";
            swal("Admin!", str, 'success').then((value) => {
                showaddcandidateform();
            });
//            $('#addresp').text(str).css("color",'green').css("font-weight","bold");
//            window.setTimeout(function() {showaddcandidateform();},50000);
        },
        error: function (e) {
            swal("Admin!", e, 'error');
        }
    });
}
function clearText() {

}
function getdetails(e) {
    if (e.keyCode === 13) {
        data = {uid: $('#uid').val()};
        $.post("AddCandidateControllerServlet", data, function (responseText) {
            let details = JSON.parse(responseText);
            let city = details.city;
            let uname = details.username;
            if (uname === "wrong") {
                swal("Wrong Ctzn_No!", "Ctzn_no is invalid!", "error");
            } else {
                $('#cname').val(uname);
                $('#city').empty();
                $('#city').append(city);
                $('#cname').prop("disabled", true);
            }
        });
    }
}