let username, password, cpassword, city, address, ctzn, email, mobile,xhr;

function addUser() {
    username = $("#username").val();
    password = $("#password").val();
    cpassword = $("#cpassword").val();
    city = $("#city").val();
    address = $("#address").val();
    ctzn = $("#ctzn").val();
    email = $("#email").val();
    mobile = $("#mobile").val();
    if(validateUser() === false) {
        swal("Error", "All fields are mandatory!", "error");
    } else {
        if(password !== cpassword) {
            swal("Error", "Passwords do not match!", "error");
            return;
        } else {
            if(checkEmail() === false) {
                return;
            }
            if(checkMobile() === false) {
                return;
            }
            let data = {username : username,
                        password:password,
                        city:city,
                        address:address,
                        userid:ctzn,
                        email:email,
                        mobile:mobile
                        };
            xhr = $.post("RegistrationControllerServlet",data,processresponse);
            xhr.catch(handleError);
        }
    }
}
function processresponse(responseText, textStatus, xhr) {
    let str = responseText.trim();
    if(str==="success") {
        swal("Success!","Registration successfully done... You can now login","success");
        setTimeout(redirectUser, 3000);
    } else if(str==="uap") {
        swal("Error!","Sorry! the userid is already present","error");
    } else {
        swal("Error!","Registration failed! Try again","error");
    }
}
function handleError() {
    swal("Error!","Problem in server communication! "+xhr.statusText,"error");
}
function validateUser() {
    if(username==='' || password === '' || cpassword==='' || city==='' || address==='' || ctzn==='' || email==='' || mobile==='') {
        return false;
    }
    return true;
}
function checkEmail() {
    let attheratepos = email.indexOf("@");
    let dotpos = email.indexOf(".");
    if(attheratepos < 1 || dotpos < attheratepos + 2 || dotpos+2>=email.length) {
        swal("Error", "Please enter a valid email!", "error");
        return false;
    }
    return true;
}
function checkMobile() {
    if(mobile.length !== 10) {
        swal("Error","Please enter a valid mobile no!","error");
        return false;
    }
    return true;
}
function redirectUser() {
    window.location = "login.html";
}