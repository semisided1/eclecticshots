<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="ca.eclecticshots.model.*"%>
<%@ page import="ca.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	String name = null;
	name = request.getParameter("name");
	if (name == null) {
		response.sendRedirect("/");
	}

	String paramI = request.getParameter("i");
	if (paramI == null)
		paramI = "0";
	int i = 0;
	i = 0 + Integer.parseInt(paramI);
	ECPhoto photo = null;

	String paramS = request.getParameter("s");
	if (paramS == null)
		paramS = "500"; //default size
	int s = 0;
	s = 0 + Integer.parseInt(paramS);
	if (s < 200) {
		s = 200;
	} // min size
	if (s > 1000) {
		s = 1000;
	} // max size

	String paramT = request.getParameter("t");
	if (paramT == null)
		paramT = "0"; // default stop the show is zero
	int t = 0;
	t = 0 + Integer.parseInt(paramT);
	if (t != 0)
		if (t < 2)
			t = 2; // 2 second min

	ECAlbum eca = Dao.INSTANCE.getECAlbumbyName(name);
	List<ECPhoto> photos = Dao.INSTANCE.getECPhotos(eca.getName());
	int len = photos.size();

	if (t != 0) {
%>
<meta http-equiv="refresh"
	content="<%=t%>;url=eca.jsp?name=<%=eca.getName()%>&i=<%=(i + 1) % len%>&t=<%=t%>&s=<%=s%>">
<%
	}
%>
<link rel="stylesheet/less" type="text/css"
	href="http://twitter.github.com/bootstrap/lib/bootstrap.less">
<link href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.css"
	rel="stylesheet">
<link href="http://twitter.github.com/bootstrap/assets/css/docs.css"
	rel="stylesheet">
<link
	href="http://twitter.github.com/bootstrap/assets/js/google-code-prettify/prettify.css"
	rel="stylesheet">
<link href="./css/eclecticshots.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=eca.getName()%></title>
</head>
<body>
	<div class="content">
		<div class="row">
			<div class="span3 offset1">
				<a href="/">
					<h1>Eclectic Shots</h1>
				</a>
				<h2><%=eca.getName()%></h2>

				<%
					for (int x = 0; x < len; x++) {
						photo = photos.get(i);
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&s=<%=s%>"> <img
					src="<%=photo.getPhotourl()%>" />
				</a>
				<%
					i = (i + 1) % len;
					}
				%>
			</div>
			<div class="span9">
				<img class="bigshot" src="<%=photos.get(i).picasaSizeURL(s)%>" />
				<br />
			</div>
			<div class="span2">
				<%
					if (t != 0) {
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&s=<%=s%>">Slideshow
					Stop </a> <br />
				<%
					if (t > 2) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&s=<%=s%>&t=<%=t - 2%>">Faster
				</a> <br />
				<%
					}
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&s=<%=s%>&t=<%=t + 2%>">Slower</a>
				<br />
				<%
					} else {
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&t=10&s=<%=s%>">Slideshow
					Start</a>
				<%
					}
				%>
				<%
					if (s < 1000) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&t=<%=t%>&s=<%=s + 100%>">
					Grow </a> <br />
				<%
					}
				%>
				<%
					if (s > 100) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&i=<%=i%>&t=<%=t%>&s=<%=s - 100%>">
					Shrink </a>
				<%
					}
				%>
			</div>
		</div>
</body>
</html>