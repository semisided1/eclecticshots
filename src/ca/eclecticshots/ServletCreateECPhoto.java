package ca.eclecticshots;

import java.io.IOException;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ca.eclecticshots.Dao;

@SuppressWarnings("serial")
public class ServletCreateECPhoto extends HttpServlet {
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
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
		
		
		String photourl = checkNull(req.getParameter("photourl"));
		String notes = checkNull(req.getParameter("notes"));		
		String albumid = checkNull(req.getParameter("albumid"));	
		Dao.INSTANCE.addECPhoto(photourl, notes, albumid);
		resp.sendRedirect("/ECPhotoApplication.jsp");
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

}
