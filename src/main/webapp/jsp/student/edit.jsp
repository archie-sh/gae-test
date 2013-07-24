<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%--
  Created by IntelliJ IDEA.
  User: pawel
  Date: 20.07.13
  Time: 11:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title></title>
</head>
<body>
<form:form commandName="student">
	<form:hidden path="id" />
	<div>
		<span>First name:</span>
		<form:input path="firstName"/>
	</div>
	<div>
		<span>Last name:</span>
		<form:input path="lastName" />
	</div>
	<div>
		<span>Password:</span>
		<form:password path="password" />
	</div>
	<form:hidden path="address.id" />
	<div>
		<span>Country</span>
		<form:input path="address.country" />
	</div>
	<div>
		<span>City</span>
		<form:input path="address.city" />
	</div>
	<div>
		<span>Street</span>
		<form:input path="address.street" />
	</div>
	<div>
		<span>Building Number</span>
		<form:input path="address.buildingNumber" />
	</div>
	<div>
		<input type="submit" value="Save raw" name="saveMethod"/>
		<input type="submit" value="Save repo" name="saveMethod"/>
	</div>
</form:form>
<%@ include file="../links.jsp" %>
</body>
</html>