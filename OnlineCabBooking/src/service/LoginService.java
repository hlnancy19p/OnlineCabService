package service;

import java.util.Map;

import domain.User;

public interface LoginService {

	// check whether user uses correct userName and passWord to login
	public Integer loginAuthenticate (Map<String,String> map);
	
	// add logout history for the user
	public Boolean loginOut (User user);
	
	// get full user info
	public User getUserInfo (Integer userId);
}
