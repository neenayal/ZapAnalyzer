<%@page import="com.zapstitch.analyzer.base.AppUser"%>
<%@page import="com.zapstitch.analyzer.base.Report"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.zapstitch.analyzer.base.ZapMail"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Zap Analyzer</title>
<head>
<link href="./stylesheets/jquery-ui.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery-ui.min.js"></script>


<script>
	$(document).ready(function() {
		$("#basedate").datepicker({
			dateFormat : "yy/mm/dd",
				showOn: "button",
		        buttonText: 'Calendar'
		});
	});
</script>

<script type="text/javascript">
	function validate(ele) {
		var baseDate = ele.basedate.value;
		var flag = true;
		if (baseDate == '') {
			flag = false;
			document.getElementById("error").innerHTML = "Please Enter Date";
		}else{
			document.getElementById("result").innerHTML = "<h4>Loading ..... </h4>";
			document.getElementById("generate").disabled = true;
		}
		return flag;
	}
</script>

</head>
<body>
	<div class="oauthrization">
		<%
			AppUser user = (AppUser) session.getAttribute("appUser");
			if (user.isTokenCertified() == false) {
		%>
		<jsp:forward page="./index.jsp" />
		<%
			} else {
				out.println("Welcome, " + user.getUsername()+" | "+ user.getEmail() + " | ");
				out.println("<a href='./logout'>Disconnect</a>");
			}
		%>
	</div>

	<br />
	<div class="query">
		<h1>ZapAnalyzer</h1>
		<form action="./report" method="get" onsubmit="return validate(this)">
			Select Date <input type="text" id="basedate" name="basedate" value=""
				readonly />
			<button type="submit" value="Genrate Report" id= "generate" enabled >Genrate Report</button>
		</form>
	</div>
	<div id="error" style="color:red"></div>
	<hr>

	<div class="result_content">
		<div id="result_header">
			<h3>ZapAnalyzer Report</h3>
		</div>
		<div id="result">
		
			<%
				Report result = (Report) session.getAttribute("myReport");
				if (result != null) {
					ArrayList<ZapMail> list = result.getReportList();
			%>
			
			<table>
  			<tr><th colspan="2">Email:
			<%	out.println(user.getEmail());	%> 
			</th></tr>
			
			<tr><th colspan="2">Start Date :
			<% 	out.println(result.getStartDate()); %>
			</th></tr>
			
			<tr><th colspan="2">End Date :
			<% 	out.println(result.getEndDate());	%>
			<hr>
			</th></tr>
			
			<tr><th>Email </th><th>#convs </th></tr>

			<%
				if (list.isEmpty()) {
						out.println("<tr><td colspan=\"2\">No record found. </td></tr>");
					} else {
						for (ZapMail mail : list) {
							out.println("<tr><td>"+mail.getEmailId() + "</td><td>" + mail.getNoOfCommunication() + "</td></tr>");
						}
					}
			%>
			</table>
			<%
				} else {
					out.println("Your Generated Report will be displayed here....... ");
				}
			%>
		</div>
	</div>

</body>
</html>