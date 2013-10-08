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

public class OrderServlet extends HttpServlet {

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
			
			String dir = checkNull(req.getParameter("dir"));
			String id = checkNull(req.getParameter("album"));
			
			Dao dao = Dao.INSTANCE;
			List<ECAlbum> ecalbums = null;
			ecalbums = dao.listECAlbums();
			int row = 0;
			int last = ecalbums.size();
			ECAlbum a = ecalbums.get(0);
			ECAlbum b = a;
			
			
			for (ECAlbum ecalbum : ecalbums) {	
				resp.getWriter().print(id);
				resp.getWriter().println(" : " + ecalbum.getId());
				
				if (ecalbum.getId().compareTo(id)==0) {
					a = ecalbum;
				//	resp.getWriter().println("found at : " + row);
					break;
				}
					row++;
			}
			if (dir.compareTo("up") == 0) {
				b = ecalbums.get(row -1);
			} else { // "dir is down"
			    b = ecalbums.get(row + 1);
			}
			//resp.getWriter().println("a id : " + a.getId());
			//resp.getWriter().println("b id : " + b.getId());
			Dao.INSTANCE.swap(a.getId(),b.getId());
			
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
