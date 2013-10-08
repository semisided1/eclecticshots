package com.eclecticshots;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.servlet.http.*;

import com.eclecticshots.Dao;
import com.eclecticshots.model.ECAlbum;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.media.mediarss.MediaContent;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.GphotoEntry;

import java.util.List;


@SuppressWarnings("serial")
public class PicasaImportServlet extends HttpServlet {
	
	
	PrintWriter out = null;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		synchronized (this) {
		
		resp.setContentType("text/html");
		out = resp.getWriter();
		out.println("<html><head><title>Picasa Import</title><style>" +
				"table{	border-collapse:collapse;}" +
				"td{border: 1px solid black;" +
				"padding:20px;}" +
				"</style></head></html>");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean lock = true;

		if (user == null) {
			// resp.sendRedirect("/");
			out.print("<body>");
			
			// add about eclectic shots stuff here
			
			
			out.print("<a href=\"");
			out.print(userService.createLoginURL("/picasaimport"));
			out.print("\"> login to manage site </a></body></html>");
			return;
		}

		if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
		if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

		if (lock) {
			out.print("<body><a href=\"");
			out.print(userService.createLogoutURL("/picasaimport"));
			out.print("\">logout </a></body></html>");
			return;
		}
		
		//album_feed = 'http://picasaweb.google.com/data/feed/api/user/' . $user . '?v=2';
		//$photo_feed = 'http://picasaweb.google.com/data/feed/api/user/' . $user . '/albumid/'
		
		resp.getWriter().println("<h1>current albums</h1>");
		Dao dao = Dao.INSTANCE;
		List<ECAlbum> ecalbums = null;
		ecalbums = dao.listECAlbums();
		int row = 0;
		int last = ecalbums.size();
		out.println("<table><tr><td>Album Name</td><td colspan=\"3\"> Order </td><td colspan=\"2\">Manage</td> <td>link</td><td>picasa</td></tr>");
		for (ECAlbum ecalbum : ecalbums) {
			row++;
			out.println("<tr><td>");
			out.println("<b>" + ecalbum.getName() + "</b>");
			out.println("</td><td>");
			out.println( ecalbum.getAorder());
			out.println("</td><td>");
			if (row!=1) {
				out.println("<a href=\"order?dir=up&amp;album=" + ecalbum.getId() + "\"> up </a> ");
			}
			out.println("</td><td>");
			if (row!=last) {
				out.println("<a href=\"order?dir=down&amp;album=" + ecalbum.getId() + "\"> down </a> ");
			}
			out.println("</td><td>");
			out.println("<a href=\"cascade?id=" + ecalbum.getId() + "\"> del </a> ");
			out.println("</td><td>");
			out.print("<a href=\"update?id=" + ecalbum.getId() + "\"> update </a> ");
			out.println("</td><td>");
			out.println("<a href=\"/eca.jsp?name=" + ecalbum.getName() + "\"> album </a> ");
			out.println("</td><td>");
			
			out.println("<a href=\"" + ecalbum.getPicasaurl() + "\"> picasa </a> ");
			out.println("</td></tr>");
		}
		out.println("</table>");
		
		out.println("<h1>available picasa albums: </h1>");
		PicasawebService client = new PicasawebService("picasaimpr8.appspot.com");
		String urlprefix = "http://picasaweb.google.com/data/feed/api/user/";
		String urlstr = urlprefix + "spriestphoto@gmail.com" + "?v=2"; 
		URL albumUrl = new URL(urlstr);
		UserFeed userFeed = null;
		try {
			userFeed =  client.getFeed(albumUrl, UserFeed.class);
		} catch (Exception e) {
			e.printStackTrace(out);
		}
		
		
		List<GphotoEntry> entries = userFeed.getEntries();	
		for (GphotoEntry entry : entries) {
			GphotoEntry album = null;
			try {
				album = entry.getAdaptedEntry();
			} catch (Exception e) {
				e.printStackTrace(resp.getWriter());
			}
			if (album instanceof AlbumEntry) {
				// if album entry not in current
				// create a form to add the album into current
				if ( isin((AlbumEntry)album,ecalbums) == true) continue;
				addform(out,(AlbumEntry)album,last);
			}
		}
		
		java.util.Date d = new java.util.Date();
		
		resp.getWriter().println(d + "</body></html>");
		
		} // end sycronized
	}// end doGet
	
		void addform(PrintWriter out, AlbumEntry a, int last) {
			out.print("<form name=\"addalbum\" action=\"/neweca\" method=\"post\">");
			out.print("<input type=\"hidden\" id=\"aorder\" name=\"aorder\" value=\""
					+ (last + 1000) + "\"/>");
			out.print("<input type=\"hidden\" id=\"name\" name=\"name\" value=\""
					+ a.getName() + "\"/>");
			out.print("<input type=\"hidden\" id=\"picasaurl\" name=\"picasaurl\" value=\""
					+ a.getHtmlLink().getHref() + "\"/>");
			out.print("<input type=\"hidden\" id=\"feedurl\" name=\"feedurl\" value=\""
					+ a.getLinks().get(0).getHref() + "\"/>");
			out.print("<input type=\"hidden\" id=\"coverurl\" name=\"coverurl\" value=\""
					+ getcover(a) + "\"/>");
			
			out.print("<input type=\"submit\" value=\"add " + a.getName()  + "\" />");
			out.println("</form>");
		}
		
		boolean isin(AlbumEntry feedalbum, List<ECAlbum> currentalbums ) {
			String feedname = feedalbum.getName(); 
			boolean retval = false;
			for (ECAlbum iter : currentalbums) {
				if (iter.getName().compareTo(feedname) == 0 ) {
					retval = true;
					break;
				}
			}
			return retval;
		}
		
		String getcover(AlbumEntry feedalbum) {
			String coverurl = null;
			List<MediaContent> mc = feedalbum.getMediaContents();
			for ( MediaContent m: mc ) {
				coverurl = picasaSizeURL(m.getUrl(),200);
				//coverurl = m.getUrl();
				break;	
			}
			return coverurl;
		}
	
		public String picasaSizeURL(String photourl, int size) {
			int i;
			String f,g;
			i = photourl.lastIndexOf('/');
			f = photourl.substring(0,i);
			g = photourl.substring(i);
			return ( f + "/s" + size + g );		
		}
		/*	
	public String getLinkByRel(List<Link> links, String relValue) {
		for (Link link : links) {
			if (relValue.equals(link.getRel())) {
				return link.getHref();
			}
		}
		throw new IllegalArgumentException("Missing " + relValue + " link.");
	}
	*/
}
