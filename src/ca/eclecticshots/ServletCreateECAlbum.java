package ca.eclecticshots;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ca.eclecticshots.Dao;

import java.net.URL;
import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.GphotoEntry;
import com.google.gdata.data.media.mediarss.MediaContent;

@SuppressWarnings("serial")
public class ServletCreateECAlbum extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		synchronized (this) {
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean lock = true;

		if (user == null) {
			resp.sendRedirect("/");
			return;
		}

		if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
		if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

		if (lock) {
			resp.sendRedirect("/");
			return;
		}
		
		String name = checkNull(req.getParameter("name"));
		String coverurl = checkNull(req.getParameter("coverurl"));
		String picasaurl = checkNull(req.getParameter("picasaurl"));
		String feedurl = checkNull(req.getParameter("feedurl"));
		String aorder = checkNull(req.getParameter("aorder"));
		
	
		
		Dao.INSTANCE.addECAlbum(name, coverurl, picasaurl, feedurl, aorder);
		
		// add the photos
		
		PicasawebService client = new PicasawebService("picasaimpr8.appspot.com");
		URL photosUrl = new URL(feedurl);	
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
					int i,j;
					String f,g,thumburl;
						i = m.getUrl().lastIndexOf('/');
						//j = m.getUrl().lastIndexOf('/', i-1);
						f = m.getUrl().substring(0,i);
						g = m.getUrl().substring(i);
						thumburl = f + "/s200" + g;
						
						Dao.INSTANCE.addECPhoto(thumburl, "", name);	
						//Dao.INSTANCE.addECPhoto(m.getUrl(),"testing 001", name);
						
											
						
						
				}

			}
		}
	
		resp.sendRedirect("/wait?t=5");
		} // end syncronized
		
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
