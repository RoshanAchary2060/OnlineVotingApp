<%-- 
    Document   : registrationresponse
    Created on : Apr 28, 2024, 12:16:20 PM
    Author     : rosan
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    boolean result = (boolean) request.getAttribute("result");
    boolean userfound = (boolean) request.getAttribute("userfound");
    if (userfound == true) {
        out.println("uap");
    } else if (result == true) {
        out.println("success");
    } else {
        out.println("error");
    }

%>