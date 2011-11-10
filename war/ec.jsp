<?xml version="1.0" encoding="iso-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
               "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="ca.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>
<%@ page import="ca.eclecticshots.model.ECAlbum"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="title" content="Eclectic Shots"/>
<meta name="author" content="Darrell Dupas designr8.com"/>
<meta name="description" content="Eclectic Shots"/>
<meta name="keywords" content="Calgary, Photography, Shelly Priest, eclecticshots.com, designr8.com"/>
<link href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.css" rel="stylesheet"/>
<link href="./css/eclecticshots.css" rel="stylesheet"/>
<title>Eclectic Shots</title>
</head>
<body><%
		Dao dao = Dao.INSTANCE;
		List<ECAlbum> ecalbums = null;
		ecalbums = dao.listECAlbums();
%><div class="content">
<div class="row">
<div class="span3 offset1">
<p class="heading">	Eclectic Shots</p>
<p class="heading2" >for all your photographic needs</p>
<img src="../img/shelly.jpg" alt="Shelly Priest" />
<p>
<i>Shelly - &quot;I can capture the essence of your special	moments&quot;</i>
</p>
<p>
<a href="mailto:shelly@eclecticshots.com">Email</a>
</p>
<p>
<a href="fbook.html">Facebook</a>
</p>
</div>
<div class="span1 emptypanel"> </div>
<ul class="media-grid"><%
					int row = 0;
					for (ECAlbum ecalbum : ecalbums) {
						row++;
%><li><a href="eca.jsp?name=<%=ecalbum.getName()%>">
<img src="<%=ecalbum.getCover()%>" class="opac"	alt="<%=ecalbum.getName() + " " + ecalbum.getDescription()%>" />
 <%=ecalbum.getName()%></a></li><%
					}
%></ul>
</div>
</div>
<p>
<a href="about.html">Eclectic Shots 2011</a>
</p>
<p style="float: right;">
    <a href="http://validator.w3.org/check?uri=referer"><img
      src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Strict" height="31" width="88" /></a>
  </p>
</body>
</html>