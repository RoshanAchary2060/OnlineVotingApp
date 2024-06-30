var userid, password;

function connectUser() {
    console.log("Inside connectUser");
    userid = $("#username").val();
    password = $("#password").val();
    if(validateUser() === false) {
        swal("Access Denied","Please enter userid/password","error");
        return;
    }
    let data = {userid:userid, password:password};
    let xhr = $.post("LoginControllerServlet", data, processResponse);
    xhr.catch(handleError);
}
function processResponse(responseText) {
    if(responseText.trim() === "error") {
        swal("Access Denied","Invalid userid/password","error");
    } else if(responseText.trim().indexOf("jsessionid")!=-1) {
        swal("Success","Login Successful","success").then((value)=> {
            window.location = responseText.trim();
        });
    } else {
        swal("Access Denied","Some problem occured:" +responseText, "error");
    }
}
function handleError() {
    swal("Error!","Problem in server communication! "+xhr.statusText,"error");
}
function validateUser() {
    if(username==='' || password ==='') {
        swal("Error", "All fields are mandatory!", "error");
    }
    return true;
}