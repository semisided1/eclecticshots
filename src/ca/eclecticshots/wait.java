package ca.eclecticshots;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class wait extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// synchronized (this) {
			String st = req.getParameter("t");
			Long time = Long.parseLong(st);
			if (test()){
				resp.sendRedirect("/picasaimport");
			}
			java.util.Date d = new java.util.Date();
			
			resp.getWriter().println(d);
			resp.flushBuffer();
		//	resp.setHeader("refresh", 1)
		//	try {
		//		this.wait(1000);
		//	} catch (Exception e) {
		//		e.printStackTrace(resp.getWriter());
		//	}						
		//}
	}
		
	// todo:!!!!
	
	boolean test() {
		// check to see if there are any outstanding transactions
		// if there is resp.setHeader refresh 1
		// return false
		
		return true;
	}
		

}
