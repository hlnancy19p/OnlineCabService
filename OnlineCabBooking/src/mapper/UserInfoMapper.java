package mapper;

import domain.User;

public interface UserInfoMapper {

	/**
	 * Check if the user has login
	 * 
	 * @param user
	 *            - instance of the user object to be verified
	 * @return userId, if the user uses correct userName and password to login;
	 *         otherwise, return 0
	 */
	public Integer loginCheck(User user);

	/**
	 * Add logout history for a user
	 * 
	 * @param user
	 *            - instance of the user object
	 * @return true, if the record is added; otherwise, return false.
	 */
	public Boolean loginOutRecord(User user);

	/**
	 * Get the information of a user
	 * 
	 * @param user
	 *            - an instance of the user to be retrieved
	 * @return An instance of User object which contains the information of a user.
	 */
	public User getUserInfo(User user);
}
