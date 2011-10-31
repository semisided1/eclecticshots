<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="ca.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>
<%@ page import="ca.eclecticshots.model.ECAlbum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Eclectic Shots B E T A</title>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">

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
</head>
<body>
	<!--  list all the albums and show their covers, put a lint to eca.html that will display the album -->
	<%
		Dao dao = Dao.INSTANCE;
		List<ECAlbum> ecalbums = null;
		ecalbums = dao.listECAlbums();
	%>

<body>

	<div class="content">

		<div class="row">

			<div class="span4 offset1">

				<h1>Eclectic Shots</h1>
				<h2>for all your photographic needs</h2>

				<!--<div class="shelly" />  -->
				<img src="../img/shelly.jpg" alt=""> <i>Shelly - &quot;I
					can capture the essence of your special moments&quot; </i>
				<p>
					<a href="mailto:shelly.priest@facebook.com">s h e l l y . p r i
						e s t @ f a c e b o o k . c o m </a>
				</p>
				<p>
					<a href="fbook.html">Facebook</a>
				</p>

			</div>
			<div class="span10">

				<%
					int row = 0;
					for (ECAlbum ecalbum : ecalbums) {
						row++;

						if (row % 2 == 1) {
				%>
				<div class="row ">
					<!-- row  -->
					<%
						}
					%>
					<div class="span show-grid ">
						<a href="eca.jsp?name=<%=ecalbum.getName()%>&i=0"> <img
							src="<%=ecalbum.getCover()%>" class="opac"
							alt="<%=ecalbum.getName() + " " + ecalbum.getDescription()%>" />
							<br /> <%=ecalbum.getName()%>
						</a>

					</div>
					<%
						if (row % 2 == 0) {
					%>
				</div>
				<!-- end row  -->
				<%
					}
					}
				%>
				<%
					if (row % 2 == 1) {
				%>
			</div>
			<!-- end row  -->
			<%
				}
			%>
		</div>
		<!--  column  -->

	</div>

	</div>


	<a href="/ec.jsp">ec.jsp</a>
	<br />
	<a href="/eca.jsp">eca.jsp</a>
	<br />
	<a href="/ECAlbumApplication.jsp">ECAlbumApplication.jsp</a>
	<br />
	<a href="/ECPhotoApplication.jsp">ECPhotoApplication.jsp</a>
	<br />



</body>
</html>






