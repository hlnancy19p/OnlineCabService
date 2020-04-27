package service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.User;
import mapper.UserInfoMapper;
import service.LoginService;

@Service("loginService")
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserInfoMapper userInfo;
	
	public UserInfoMapper getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoMapper userInfo) {
		this.userInfo = userInfo;
	}

	// check whether user uses correct userName and passWord to login
	@Override
	public Integer loginAuthenticate (Map<String,String> map) {
		Integer result = 0;
		
		User user = new User();
		user.setUserName(map.get("userName"));
		user.setPassWord(map.get("passWord"));
		
		result = userInfo.loginCheck(user);
		
		return result;
	}
	
	// add logout history for the user
	@Override
	public Boolean loginOut (User user) {
		Boolean result = false;
		
		result = userInfo.loginOutRecord(user);
		
		return result;

	}
	
	// get full user info
	@Override
	public User getUserInfo (Integer userId) {
		User user = new User();
		user.setUserId(userId);
		
		User currentUser = null;
		
		currentUser = userInfo.getUserInfo(user);
		
		return currentUser;
	}
}
