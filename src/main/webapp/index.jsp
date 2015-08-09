<%@page import="com.zapstitch.analyzer.base.AppUser"%>
<%@page import="com.zapstitch.analyzer.base.Constants"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Zap Analyzer</title>
</head>
<body>
	<div class="readme">
		<h1>ZapAnalyzer</h1>
			<p><h3>
			 ZapAnalyzer is an App to analysis your personal gmail Messages. It will Help you to analyze
whom you are communicating with the most and how often in the past 3 months. It will
help you to  analyze this data by generating a report of most frequently communicated contacts.
	
			</h3><p/>
<hr>
	</div>


	<div class="oauthrization">
		<%
			AppUser user = (AppUser) session.getAttribute(Constants.SESSION_APPUSER);
			if (user.isTokenCertified() == false) {
				out.println("<a href='" + user.buildLoginUrl() + "'>log in with google</a>");
			} else {
				%>
				<jsp:forward page="./dashboard.jsp" />
				<%
			}
		%>
	</div>
	
	<div>
	<h4> * We do not save User's data and access permission so you donot need to worry while login with Google Account. *</h4>
	</div>
	<br />
</body>
</html>