<%-- 
    Document   : loginresponse
    Created on : Apr 28, 2024, 9:40:10 PM
    Author     : rosan
--%>


<%
    String userid = (String)request.getAttribute("userid");
    String result =(String) request.getAttribute("result");
    if(userid != null && result!=null) {
        HttpSession sess = request.getSession();
        sess.setAttribute("userid", userid);
        String url = "";
        if(result.equalsIgnoreCase("Admin")) {
            url = "AdminControllerServlet;jsessionid="+sess.getId();
        } else {
            url = "VotingControllerServlet;jsessionid="+sess.getId();
        }
        out.println(url);
    } else {
        out.println("error");
    }
%>