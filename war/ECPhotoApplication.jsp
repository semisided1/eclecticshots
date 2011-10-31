<%@page import="java.net.CookieManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="ca.eclecticshots.model.*" %>
<%@ page import="ca.eclecticshots.Dao" %>
<%@ page import="java.util.List" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/main.css" />
<title>Photos</title>
</head>
<body>
<%
String albumid = null;

UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();


boolean lock = true;

if (user == null) {
	%>
	<a href="<%=userService.createLoginURL(request.getRequestURI())%>">login</a>
	
	</body>
	</html>
	<%
	return;
}

if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

if (lock) {

	%>
	<h1> not admin sorry </h1>
	</body>
	</html>
	<%
	return;
}



List< ECPhoto> photos = null;
List< ECAlbum> albums = null;


albumid = request.getParameter("albumid");
if (albumid == null) { 
	albums = Dao.INSTANCE.listECAlbums();
	if (albums == null) {
		return;
	}
%>

<form action="/ECPhotoApplication.jsp" method="post"
accept-charset="utf-8"><select name="albumid" id="albumid">
<%
	for (ECAlbum album : albums) { %>
			<option value="<%=album.getName()%>"><%=album.getName()%></option>
<%	}
%>		</select> <input type="submit" value="select album" /></form>
		</body>
	</html>
	<%
	return;
}

photos = Dao.INSTANCE.getECPhotos(albumid);
if (photos==null) {
	return;
}
%>
	<%
for (ECPhoto photo : photos ){ %>
<div class="card" >
	
	<img class="cardimg" src="<%= photo.getPhotourl() %>" />
	
	<br/>
	<%= photo.getNotes() %>
	<a class="delecp" href="/delecp?id=<%=photo.getId()%>">del</a>
		</div>

	<% } %>
	

<h1>New Photo for <%=albumid %> </h1>

<form action="/newecp" method="post" accept-charset="utf-8">
<input type="hidden" name="albumid" id="albumid" value="<%=albumid%>" />
<table>
	
	<tr>
		<td><label for="photourl">Photo URL </label></td>
		<td><input type="text" name="photourl" id="photourl" size="65" /></td>
	</tr>
	<tr>
		<td><label for="notes">Notes </label></td>
		<td><input type="text" name="notes" id="notes" size="65" /></td>
	</tr>
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Create" /></td>
	</tr>
</table>
</form>


<h1>Or Add all Photos from web page into <%=albumid %> </h1>

<form action="/addurl" method="post" accept-charset="utf-8">
<input type="hidden" name="albumid" id="albumid" value="<%=albumid%>" />
<table>
	
	<tr>
		<td><label for="url">web page URL </label></td>
		<td><input type="text" name="url" id="url" size="65" /></td>
	</tr>
	<tr>	
	<td><label for="width">set picassa size folder</label></td>
		<td><select name="width" id="width"  />
			<option value=""> </option>
			<option value="s150">s100</option>			
			<option value="s150">s150</option>
			<option value="s200">s200</option>
			<option value="s250">s250</option>
			<option value="s300">s300</option>
			<option value="s350">s350</option>
			<option value="s400">s400</option>
			<option value="s150-c">s150-c</option>
			<option value="s200-c">s200-c</option>
			<option value="s250-c">s250-c</option>
			<option value="s300-c">s300-c</option>
			<option value="s350-c">s350-c</option>
			<option value="s400c-">s400-c</option>	
		</select> 
	</tr>
	
	<tr>
		<td colspan="2" align="right"><input type="submit" value="Add Multiple" /></td>
	</tr>
	
</table>
</form>

<h1>Delete all photos in this album</h1>
<form action="/dpia" method="post" accept-charset="utf-8">
<input type="hidden" name="albumid" id="albumid" value="<%=albumid%>" />
<input type="submit" value="Remove All" />
</form>
</body>
</html>


