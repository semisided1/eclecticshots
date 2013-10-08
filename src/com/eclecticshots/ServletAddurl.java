package com.eclecticshots;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eclecticshots.Dao;
import com.eclecticshots.Net;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class ServletAddurl extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		String surl = checkNull(req.getParameter("url"));
		String albumid = checkNull(req.getParameter("albumid"));	
		String width = checkNull(req.getParameter("width"));	
	
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		boolean lock = true;

		if (user == null) {
			
			return;
		}

		if ( 0 == user.getEmail().compareTo("dirtslayer@gmail.com") ) lock = false;
		if ( 0 == user.getEmail().compareTo("spriestphoto@gmail.com") ) lock = false;

		if (lock) {

			return;
		}
		
		List<String> imgs =  Net.getImages(surl);
		
		if ( width.compareTo("") == 0 ) {
			for (String img : imgs) Dao.INSTANCE.addECPhoto(img, "", albumid);
			resp.sendRedirect("/ECPhotoApplication.jsp");
			return;
		}
		
		
		int i,j;
		String f,g;
		
		for ( String img : imgs ) {
			i = img.lastIndexOf('/');
			j = img.lastIndexOf('/', i-1);
			f = img.substring(0,j+1);
			g = img.substring(i);
			Dao.INSTANCE.addECPhoto(f + width + g,"",albumid);
		}
		
		resp.sendRedirect("/ECPhotoApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}