<?xml version="1.0" encoding="iso-8859-1"?><%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%><%@ page import="ca.eclecticshots.model.*"%><%@ page import="ca.eclecticshots.Dao"%><%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="title" content="Eclectic Shots Album"/>
<meta name="author" content="Darrell Dupas designr8.com"/>
<meta name="description" content="Eclectic Shots"/>
<meta name="keywords" content="Calgary, Photography, Shelly Priest, eclecticshots.com, designr8.com"/>
<link href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.css" rel="stylesheet"/>
<link href="./css/eclecticshots.css" rel="stylesheet"/>
<%
	String name = null;
	name = request.getParameter("name");
	if (name == null) {
		response.sendRedirect("/");
	}
	
	ECAlbum eca = Dao.INSTANCE.getECAlbumbyName(name);
	List<ECPhoto> photos = Dao.INSTANCE.getECPhotos(eca.getName());
	
	String paramI = request.getParameter("ij");
	if (paramI == null)
		paramI = "0";
	int i = 0;
	i = 0 + Integer.parseInt(paramI);
	if (i>= photos.size()) i = i % photos.size();
	
	ECPhoto photo = null;

	String paramS = request.getParameter("s");
	if (paramS == null)
		paramS = "700"; //default size
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

	
	if (t != 0) {
%>
<meta http-equiv="refresh" content="<%=t%>;url=eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=(i + 1)%>&amp;t=<%=t%>&amp;s=<%=s%>"><%
}%><title><%=eca.getName()%></title></head>
<body> <div class="content">	<div class="row">
<div class="span3 offset1">
<p class="heading">
<a href="/">Eclectic Shots</a>
</p>
				
				<p class="heading2"><%=eca.getName()%></p>
				
				<%
				int first = i;
					for (int x = 0; x < 5; x++) {
						photo = photos.get(i);
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>">
					<img src="<%=photo.getPhotourl()%>" alt="thumb" />
				</a>
				<%
					i = (i + 1) % photos.size();
					}
				%>
				<ul id="navlist">
  <li id="home"><a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>"></a></li>
  
</ul>
			</div>
			<div class="span1 emptypanel"> &nbsp; </div>
			
			<img class="bigshot" src="<%=photos.get(first).picasaSizeURL(s)%>" alt="big shot"/> <br />
			
		</div>
		
		<div class="row">
			<div class="span2">
				<%
					if (t != 0) {
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>">Slideshow
					Stop </a> <br />
				<%
					if (t > 2) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>&amp;t=<%=t - 2%>">Faster
				</a> <br />
				<%
					}
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>&amp;t=<%=t + 2%>">Slower</a>
				<br />
				<%
					} else {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;t=4&amp;s=<%=s%>">Slideshow
					Start</a>
				<%
					}
				%>
				<%
					if (s < 1000) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;t=<%=t%>&amp;s=<%=s + 100%>">
					Grow </a> <br />
				<%
					}
				%>
				<%
					if (s > 100) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;t=<%=t%>&amp;s=<%=s - 100%>">
					Shrink </a>
				<%
					}
				%>
			</div>
		</div>
		</div>
		<p>
    <a href="http://validator.w3.org/check?uri=referer"><img
      src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Strict" height="31" width="88" /></a>
  </p>
</body>
</html>