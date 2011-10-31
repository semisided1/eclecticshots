<%@page import="java.net.CookieManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="ca.eclecticshots.model.ECAlbum"%>
<%@ page import="ca.eclecticshots.Dao"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css" />
<title>Albums</title>
</head>
<body>
	<%


Dao dao = Dao.INSTANCE;

UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();

boolean lock = true;

if (user == null) { %>
	<a href="<%=userService.createLoginURL(request.getRequestURI())%>">login</a>
</body>
</html>
</body>
</html>
<%
	return;
}



if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

if (lock) {

	%>
</body>
</html>
<%
	return;
}

List< ECAlbum > ecalbums = null;
ecalbums = dao.listECAlbums();

 
    
%>
Albums

<table>
	<tr>
		<th>id</th>
		<th>name</th>
		<th>cover</th>
		<th>description</th>
	</tr>

	<%for (ECAlbum ecalbum : ecalbums ){ %>
	<tr>
		<td><%= ecalbum.getName() %></td>
		<td><%= ecalbum.getCover() %></td>
		<td><%= ecalbum.getDescription() %></td>
		<td><a class="deleca" href="/deleca?id=<%=ecalbum.getId()%>">del</a>
		</td>
	</tr>
	<%}
%>
</table>


<hr />

<div class="main">

	<div class="headline">New Album</div>

	<!--Userid <%= user.getUserId() %> -->
	<!--<br>-->
	<!--Email <%= user.getEmail() %> -->
	<form action="/neweca" method="post" accept-charset="utf-8">
		<table>
			<tr>
				<td><label for="name">Name </label></td>
				<td><input type="text" name="name" id="name" size="65" /></td>
			</tr>
			<tr>
				<td><label for="cover">Cover </label></td>
				<!--  -->
				<td><input type="text" name="cover" id="cover" size="65" /></td>
			</tr>
			<tr>
				<td><label for="description">Description </label></td>
				<!--  -->
				<td><input type="text" name="description" id="description"
					size="65" /></td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit"
					value="Create" /></td>
			</tr>
		</table>
	</form>


</div>
</body>
</html>