<%-- 
    Document   : showexception
    Created on : Apr 28, 2024, 12:25:52 PM
    Author     : rosan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Exception ex = (Exception) request.getAttribute("exception");
    System.out.println("Exception is::" + ex);
    out.println("Some exception occured::" + ex.getMessage());
%>
