<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String userid = (String) session.getAttribute("userid");
if (userid == null) {
	session.invalidate();
	response.sendRedirect("accessdenied.html");
	return;
}
boolean result = (boolean)request.getAttribute("result");
if(result){
	out.println();
} else {
	out.println("failed");
}
%>