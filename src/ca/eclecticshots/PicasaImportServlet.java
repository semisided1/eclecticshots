package ca.eclecticshots;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.*;

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



@SuppressWarnings("serial")
public class PicasaImportServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		
		resp.getWriter().println("<html><head><title>Picasa Import</title></head></html>");
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean lock = true;

		if (user == null) {
			// resp.sendRedirect("/");
			resp.getWriter().print("<body><a href=\"");
			resp.getWriter().print(userService.createLoginURL("/picasaimport"));
			resp.getWriter().print("\"> login </a></body></html>");
			return;
		}

		if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
		if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

		if (lock) {
			resp.getWriter().print("<body><a href=\"");
			resp.getWriter().print(userService.createLogoutURL("/picasaimport"));
			resp.getWriter().print("\"> logout </a></body></html>");
			return;
		}
		
		//album_feed = 'http://picasaweb.google.com/data/feed/api/user/' . $user . '?v=2';
		//$photo_feed = 'http://picasaweb.google.com/data/feed/api/user/' . $user . '/albumid/'
		
		
		PicasawebService client = new PicasawebService("picasaimpr8.appspot.com");
		String urlprefix = "http://picasaweb.google.com/data/feed/api/user/";
		String urlstr = urlprefix + "spriestphoto@gmail.com" + "?v=2"; 
		URL albumUrl = new URL(urlstr);
		UserFeed userFeed = null;
		try {
			userFeed =  client.getFeed(albumUrl, UserFeed.class);
		} catch (Exception e) {
			e.printStackTrace(resp.getWriter());
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

				resp.getWriter().println("<p>" + album.getTitle().getPlainText() + "</p>");   

				// get the photo feed url from the album feed
				String feedHref = null;
				try {
					feedHref = getLinkByRel(album.getLinks(), Link.Rel.FEED);
				} catch (Exception e) {
					e.printStackTrace(resp.getWriter());
				}

				// make another request with the same gdata client, this time for the photos (feedHref)
				URL photosUrl = new URL(feedHref);	
				AlbumFeed albumFeed = null;
				try {
					albumFeed = client.getFeed(photosUrl, AlbumFeed.class);
				} catch ( Exception e) {
					e.printStackTrace(resp.getWriter());
				}

				List<GphotoEntry> photoentries = albumFeed.getEntries();

				for (GphotoEntry photoentry : photoentries) {
					GphotoEntry photo = null;
					try {
						photo = photoentry.getAdaptedEntry();
					} catch (Exception e) {
						e.printStackTrace(resp.getWriter());
					}
					if (photo instanceof PhotoEntry) {
						List<MediaContent> mc = ((PhotoEntry) photo).getMediaContents();
						for ( MediaContent m: mc ) {
							// show the url of the photo
							//resp.getWriter().println("<p> mediacontent.url: " +  m.getUrl() + "</p>");
							int i,j;
							String f,g,thumburl;
							
						
								i = m.getUrl().lastIndexOf('/');
								f = m.getUrl().substring(0,i);
								g = m.getUrl().substring(i);
								thumburl = f + "/s100" + g;
							//resp.getWriter().println("<p> thumburl: " + thumburl + "</p>");
								resp.getWriter().println("<img src=\"" + thumburl + "\"/>");	
						}

					}
				}
			} 
		}
		
		
		
		
		
		
		
		
		resp.getWriter().println("</body></html>");
		
	}// end doGet
	
	
	
	public String getLinkByRel(List<Link> links, String relValue) {
		for (Link link : links) {
			if (relValue.equals(link.getRel())) {
				return link.getHref();
			}
		}
		throw new IllegalArgumentException("Missing " + relValue + " link.");
	}
}
