<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eclectic Shots</title>
<%@ page import="com.eclecticshots.Dao"%>
<%@ page import="java.util.List"%>
<%@ page import="com.eclecticshots.model.ECAlbum"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<meta name="title" content="Eclectic Shots"/>
<meta name="author" content="Darrell Dupas designr8.com"/>
<meta name="description" content="Eclectic Shots"/>
<meta name="keywords" content="Calgary, Photography, Shelly Priest, eclecticshots.com, designr8.com"/>

<link href="./css/eclecticshots.css" rel="stylesheet"/>


<meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="../../assets/js/html5shiv.js"></script>
      <script src="../../assets/js/respond.min.js"></script>
    <![endif]-->
    

<title>Eclectic Shots</title>
</head>
<body>


<%
		Dao dao = Dao.INSTANCE;
		List<ECAlbum> ecalbums = null;
		ecalbums = dao.listECAlbums();
%>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="//code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>




<div class="container">

<div class="row">
	
	<div class="col-md-3">
		<h1>Eclectic Shots</h1>
		<h2>for all your photographic needs</h2>
		<img src="http://lh3.googleusercontent.com/-o4R0sh8b7GY/Ttghzh2IKPI/AAAAAAAAAzs/p9XuS5pSh8Q/s200/_IGP0060%252520PPT%252520OL%2525205x7%252520web%252520%2525282%252529.jpg" alt="Shelly Priest" />
		<p>
			<i>Shelly - &quot;I can capture the essence of your special	moments&quot;</i>
		</p>
		<p>
			<a href="mailto:shelly@eclecticshots.com">Email</a>
		</p>
		
	</div>
	
		
	<div class="col-md-8">
		<ul>
		<%
			int row = 0;
			for (ECAlbum ecalbum : ecalbums) {
				row++;
		%>
		<li><a href="eca.jsp?name=<%=ecalbum.getName()%>">
		<img src="<%=ecalbum.getCoverurl()%>" alt="<%=ecalbum.getName() %>" />
 		<%=ecalbum.getName()%></a>
 		</li>
 		<%
			}
		%>
	</ul>
	</div> <!-- col-md-8 -->


</div> <!-- div row -->

<p>
<a href="about.html">Eclectic Shots 2013</a> 
</p>


</div> <!-- div container -->

</body>
</html>