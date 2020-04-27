package controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import domain.Order;
import domain.User;
import service.LoginService;
import util.SessionCheck;

@Controller
public class LoginController {
	
	// message to show the success of login
	private String message;
	
	@Autowired
	private LoginService loginService;
	
	public LoginService getLoginService() {
		return loginService;
	}
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	/**
	 * Return to index page
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index.htm")
	public String IndexPage(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) {
		
		String currentUser = authentication.getName();
		String currentPwd = (String) authentication.getCredentials();
		System.out.println(currentUser+" "+currentPwd+" Login");
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName", currentUser);
		map.put("passWord", currentPwd);
//		System.out.println(userName+" "+passWord);
		// call loginService to authenticate user
		Integer userID = loginService.loginAuthenticate(map);
		// Redirect to pages depending on the result.
		if(userID > 0) {
			
			User user_fullInfo = loginService.getUserInfo(userID);
			// store userId and userName in session until logout.
			HttpSession session = request.getSession();
			session.setAttribute("userId", user_fullInfo.getUserId());
			session.setAttribute("userName", user_fullInfo.getFullName());
			session.setAttribute("role", user_fullInfo.getRole().getRoleName());
//			System.out.println(user_fullInfo.getRole().getRoleName());
			session.setAttribute("companyId", user_fullInfo.getCompany().getCompanyId());
		}
		
		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if(isLogin == false) {
			return "login";
		}
		
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if(role!=null) {
			if(role.equals("Administrator")) {
				this.message = "Welcome Admin!";
				model.addAttribute("welcomeMessage", message);
				
				return "admin/homePage";
			}
			if(role.equals("Business")) {
				this.message = "Welcome Business!";
				model.addAttribute("welcomeMessage", message);
				
				return "business/homePage";
			}
			if(role.equals("Passenger")) {
				this.message = "Welcome Passenger!";
				model.addAttribute("welcomeMessage", message);
				
				return "passenger/homePage";
			}
		}
		
		return "login";
	}
	
	/**
	 * Simulate login action for one user
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login.htm")
	public String LoginSimulate(Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
	
		String userName = request.getParameter("username");
		String passWord = request.getParameter("password");
		
		if(userName!=null && passWord!=null) {
			// Just pretend that there is one user login to the system.
			Map<String,String> map = new HashMap<String,String>();
			map.put("userName", userName);
			map.put("passWord", passWord);
//			System.out.println(userName+" "+passWord);
			// call loginService to authenticate user
			Integer userID = loginService.loginAuthenticate(map);
			// Redirect to pages depending on the result.
			if(userID > 0) {
				
				User user_fullInfo = loginService.getUserInfo(userID);
				// store userId and userName in session until logout.
				HttpSession session = request.getSession();
				session.setAttribute("userId", user_fullInfo.getUserId());
				session.setAttribute("userName", user_fullInfo.getFullName());
				session.setAttribute("role", user_fullInfo.getRole().getRoleName());
	//			System.out.println(user_fullInfo.getRole().getRoleName());
				session.setAttribute("companyId", user_fullInfo.getCompany().getCompanyId());
				
				// redirect users to pages depending on their roles.
				if(user_fullInfo.getRole().getRoleName().equals("Administrator")) {
					this.message = "Login Success!";
					model.addAttribute("welcomeMessage", message);
					
					return "admin/homePage";
				}
				if(user_fullInfo.getRole().getRoleName().equals("Business")) {
					this.message = "Login Success! Welcome Business!";
					model.addAttribute("welcomeMessage", message);
					
					return "business/homePage";
				}
				if(user_fullInfo.getRole().getRoleName().equals("Passenger")) {
					this.message = "Login Success! Welcome Passenger!";
					model.addAttribute("welcomeMessage", message);
					// testing for lazy load
	//				Iterator iterator = user_fullInfo.getCompany().getEmployees().iterator();
	//				Iterator iterator = user_fullInfo.getOrders().iterator();
	//				while(iterator.hasNext()) {
	//					Order order = (Order) iterator.next();
	//					System.out.println(order.getOrderId());
	//					User one = (User) iterator.next();
	//					System.out.println(one.getFullName());
	//				}
					return "passenger/homePage";
				}
			}
			
			return "login";
		}
		else {
			
			// when login fail, go to fail.jsp
			this.message = "Sorry, Login Fail!";
			model.addAttribute("failMessage", message);
			
			return "login";
			
		}
		
		
	}
	
	/**
	 * User can return to home page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/home.htm")
	public String goBackToHome(Model model,
			HttpServletRequest request) {
		
		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if(isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}
		
		HttpSession session = request.getSession();
		String role = (String) session.getAttribute("role");
		if(role.equals("Administrator")) {
			this.message = "Welcome Admin!";
			model.addAttribute("welcomeMessage", message);
			
			return "admin/homePage";
		}
		if(role.equals("Business")) {
			this.message = "Welcome Business!";
			model.addAttribute("welcomeMessage", message);
			
			return "business/homePage";
		}
		if(role.equals("Passenger")) {
			this.message = "Welcome Passenger!";
			model.addAttribute("welcomeMessage", message);
			
			return "passenger/homePage";
		}
		
		return "login";
		
	}
	
	/**
	 * User can log out and session will be cleared.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout.htm")
	public String loginOut(Model model,
			HttpServletRequest request) {
		
		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if(isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}
		
		HttpSession session = request.getSession();
		session.invalidate();
		this.message = "Goodbye and Thanks for using!";
		model.addAttribute("goodbyeMessage", message);
		
		return "logout";
		
	}
	
	public void setMessage(String message) { 
		this.message = message;
	} 
	
	/**
	 * User fail to login.
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/fail.htm")
	public String loginFail(Model model,
			HttpServletRequest request) {
		
		this.message = "Sorry, you don't have the authority to access!";
		model.addAttribute("failMessage", message);
		return "fail";
		
	}

}