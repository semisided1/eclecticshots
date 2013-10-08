<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Eclectic Shots Album</title>

<%@ page import="com.eclecticshots.model.*"%>
<%@ page import="com.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="title" content="Eclectic Shots Album"/>
<meta name="author" content="Darrell Dupas designr8.com"/>
<meta name="description" content="Eclectic Shots"/>
<meta name="keywords" content="Calgary, Photography, Shelly Priest, eclecticshots.com, designr8.com"/>

 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
    
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
<meta http-equiv="refresh" content="<%=t%>;url=eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=(i + 1)%>&amp;t=<%=t%>&amp;s=<%=s%>"/><%
}%><title><%=eca.getName()%></title></head>
<body> 

 <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

<div class="container">


<div class="row">


<div class="col-md-3">

<a href="/"><h1>Eclectic Shots</h1></a>

<h2><%=eca.getName()%></h2>
				<ul>
				<%
				int first = i;
					for (int x = 0; x < 5; x++) {
						photo = photos.get(i);
				%>
				<li>
				<a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>">
					<img src="<%=photo.getPhotourl()%>" alt="thumb" />
				</a>
				<%
					i = (i + 1) % photos.size();
					}
				%>
				</li>
				</ul>
				
			<!--  	<ul> blalsllslslkdjfklasjdflkjsdlfkjsd
  <li> <a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=i%>&amp;s=<%=s%>"></a></li>
  
</ul>
-->


</div> <!-- div col-md-3 -->

<div class="col-md-8">			
			<ul><li>
			<img src="<%=photos.get(first).picasaSizeURL(s)%>" alt=""/>
			</li></ul>
</div>
</div> <!-- div row -->

		
		<div class="row" >
			
				<%
					if (t != 0) {
				%>
				<a href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;s=<%=s%>">Slideshow
					Stop </a> 
			
				<%
					if (t > 2) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;s=<%=s%>&amp;t=<%=t - 2%>">Faster
				</a> 
				<%
					}
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;s=<%=s%>&amp;t=<%=t + 2%>">Slower</a>
				<%
					} else {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;t=4&amp;s=<%=s%>">Slideshow
					Start</a>
				<%
					}
				%>
				<%
					if (s < 1000) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;t=<%=t%>&amp;s=<%=s + 100%>">
					Grow </a> 
				<%
					}
				%>
				<%
					if (s > 100) {
				%>
				<a
					href="eca.jsp?name=<%=eca.getName()%>&amp;ij=<%=first%>&amp;t=<%=t%>&amp;s=<%=s - 100%>">
					Shrink </a>
				<%
					}
				%>
			</div> <!-- div row -->

</div> <!-- div container -->
</body>
</html>