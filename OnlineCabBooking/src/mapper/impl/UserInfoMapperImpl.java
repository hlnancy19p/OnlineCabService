package mapper.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import domain.User;
import mapper.UserInfoMapper;

@Repository("userInfo")
public class UserInfoMapperImpl implements UserInfoMapper{

//	@Autowired
//	private SessionFactory sessionFactory;
//	
//	public SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}
//
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	// check whether user uses correct userName and passWord to login
	@Override
	public Integer loginCheck(User user) {
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String hql = "Select userId from User where user_name=:userName and pwd=:passWord";  
		Query query = session.createQuery(hql);
		query.setParameter("userName", user.getUserName());
		query.setParameter("passWord", user.getPassWord());
		List result = query.list();
		
		Iterator iterator = result.iterator();

		if(iterator.hasNext()) {
			return (Integer) iterator.next();
		}
		else {
			return 0;
		}
	}
	
	// add logout history for the user
	@Override
	public Boolean loginOutRecord(User user) {
		
		return true;
	}
	
	// get full user info
	@Override
	public User getUserInfo(User user) {
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Object object = session.load(User.class, user.getUserId());
		User user_fullInfo = (User) object;
//		System.out.println("Name: "+user.getUserName());
		
		
		return user_fullInfo;
	}
}
