package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionCheck {

	public static Boolean checkLoginSession(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		Integer userid = (Integer) session.getAttribute("userId");
		if(userid!=null)
			return true;
		
		return false;
		
	}
}
