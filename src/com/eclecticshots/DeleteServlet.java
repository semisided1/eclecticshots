package com.eclecticshots;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eclecticshots.model.ECAlbum;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
// deletes an album, we have to shuffle all the 
// albums below it up to preserve aorder, then delete it once its at the bottom
public class DeleteServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
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
			
			
			
			// we have to move the deleted entry to the bottom of the list
			// to preserve aorder integrity 
			
			String id = checkNull(req.getParameter("id"));
			List<ECAlbum> ecalbums = null;
			ecalbums = Dao.INSTANCE.listECAlbums();
			int row = 0;
			int last = ecalbums.size();
			ECAlbum a = ecalbums.get(0);
			ECAlbum b = a;
			
			
			for (ECAlbum ecalbum : ecalbums) {	
				resp.getWriter().print(id);
				resp.getWriter().println(" : " + ecalbum.getId());
				
				if (ecalbum.getId().compareTo(id)==0) {
					a = ecalbum;
					resp.getWriter().println("found at : " + row);
					break;
				}
					row++;
			}
			
			while (row<(last-1)){
			   b = ecalbums.get(row + 1);
			   String swap = a.getAorder();
			   Dao.INSTANCE.swap(a.getId(), b.getId());
			   row ++;
			}
			
			// ok we have moved it to the bottom
			// now we will remove the album and then all the photos in the album
			
			
			Dao.INSTANCE.removeAlbumCascade(a.getName(),a.getId());
			
			
			resp.sendRedirect("/wait?t=5");
						
		}
	}
		
		
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

}
