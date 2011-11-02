<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="ca.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>
<%@ page import="ca.eclecticshots.model.ECAlbum"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Eclectic Shots</title>

<meta http-equiv="content-type" content="text/html; charset=UTF-8">


<link href="http://twitter.github.com/bootstrap/1.3.0/bootstrap.css"
	rel="stylesheet">
<!--
<link href="http://twitter.github.com/bootstrap/assets/css/docs.css"
	rel="stylesheet">
<link
	href="http://twitter.github.com/bootstrap/assets/js/google-code-prettify/prettify.css"
	rel="stylesheet">
<link rel="stylesheet/less" type="text/css"
	href="http://twitter.github.com/bootstrap/lib/bootstrap.less">
 -->
<link href="./css/eclecticshots.css" rel="stylesheet">
</head>
<body>
	<!--  list all the albums and show their covers, put a lint to eca.html that will display the album -->
	<%
		Dao dao = Dao.INSTANCE;
		List<ECAlbum> ecalbums = null;
		ecalbums = dao.listECAlbums();
	%>

	<div class="content">

		<div class="row">

			<div class="span3 offset1">

				<h1>Eclectic Shots</h1>
				<h2>for all your photographic needs</h2>

				<!--<div class="shelly" />  -->
				<img src="../img/shelly.jpg" alt="">
				<p>
					<i>Shelly - &quot;I can capture the essence of your special
						moments&quot; </i>
				</p>
				<p>
					<a href="mailto:shelly@eclecticshots.com">Email</a>
				</p>
				<p>
					<a href="fbook.html">Facebook</a>
				</p>

			</div>
			<div class="span1 emptypanel"> &nbsp; </div>
<!-- 			<div class="span12 offset1"> -->
				<ul class="media-grid">

					<%
					int row = 0;
					for (ECAlbum ecalbum : ecalbums) {
						row++;

						
				%>

					<li><a href="eca.jsp?name=<%=ecalbum.getName()%>&i=0"> <img
							src="<%=ecalbum.getCover()%>" class="opac"
							alt="<%=ecalbum.getName() + " " + ecalbum.getDescription()%>" />
							<%=ecalbum.getName()%>
					</a></li>


					<%
					}
					
				%>
				</ul>
<!-- 			</div> -->
			<!-- end row  -->

		</div>
		<!--  column  -->

	</div>

	


	<p>
		<a href="about.html">Eclectic Shots 2011</a>
	</p>

</body>
</html>






