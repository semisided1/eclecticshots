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
public class ServletCreateECAlbum extends HttpServlet {
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
		
		
		
		String name = checkNull(req.getParameter("name"));
		String cover = checkNull(req.getParameter("cover"));
		String description = checkNull(req.getParameter("description"));
		String aorder = checkNull(req.getParameter("aorder"));
				
		
		Dao.INSTANCE.addECAlbum(name,cover,description,aorder);
		resp.sendRedirect("/ECAlbumApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
