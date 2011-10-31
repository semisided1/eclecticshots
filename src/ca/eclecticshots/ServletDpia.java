package ca.eclecticshots;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ca.eclecticshots.Dao;
import java.util.List;
import ca.eclecticshots.model.ECPhoto;

public class ServletDpia extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			
		String albumid = req.getParameter("albumid");
		List<ECPhoto> photos = Dao.INSTANCE.getECPhotos(albumid);
		for ( ECPhoto photo : photos) Dao.INSTANCE.removeECPhoto(photo.getId());
		resp.sendRedirect("/ECPhotoApplication.jsp");
	}
}
