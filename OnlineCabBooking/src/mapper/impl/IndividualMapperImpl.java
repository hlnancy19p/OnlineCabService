package mapper.impl;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import domain.Order;
import domain.User;
import mapper.IndividualBookingMapper;

@Repository("bookingOrder")
public class IndividualMapperImpl implements IndividualBookingMapper{

	// add logout history for the user
	@Override
	public Boolean createOrder(Order order) {
		boolean result = false;
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		result = session.save(order) != null;
		session.getTransaction().commit();
		
		return result;
	}

	// read info of one order
	public Order readOneOrder(Order order) {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String hql = "from Order where order_id=:orderId";  
		Query query = session.createQuery(hql);
		query.setParameter("orderId", order.getOrderId());
		List<Order> result = query.list();
		Iterator iterator = result.iterator();
		Order order_fullInfo = null;
		if(iterator.hasNext()) {
			for(;iterator.hasNext();) {
				order_fullInfo = (Order) iterator.next();
			}
			
			return order_fullInfo;
		}
		else {
			return null;
		}
	}
	// update one order
	@Override
	public Boolean updateOrder(Order order) {
		boolean result = false;
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
//		String hql = "update Order set "
//				+ "pickup_addr=:pickupAddr, "
//				+ "pickup_time=:pickupTime, "
//				+ "contact_phone=:contactPhone, "
//				+ "order_notes=:orderNotes, "
//				+ "num_of_passengers=:numOfPassengers "
//				+ "where order_id=:orderId";
//		Query query = session.createQuery(hql);
//		query.setParameter("pickupAddr", order.getPickupAddr());
//		query.setParameter("pickupTime", order.getPickupTime());
//		query.setParameter("contactPhone", order.getContactPhone());
//		query.setParameter("orderNotes", order.getOrderNotes());
//		query.setLong("numOfPassengers", order.getNumOfPassengers());
//		query.setLong("orderId", order.getOrderId());
//		int result = query.executeUpdate();
		try {
			// session will check version then update order.
			session.update(order);
			session.getTransaction().commit();
			result = true;
		}
		catch(Exception e) {
			
		}
//		if (result>0) {
//			return true;
// 		}else {
//			return false;
//		}
		return result;	
	}

	// delete one order
	@Override
	public Boolean deleteOneOrder(Order order) {

		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		String hql = "Delete from Order where order_id="+order.getOrderId();
		Query query = session.createQuery(hql);
        int result = query.executeUpdate();
		session.getTransaction().commit();
		if (result>0) {
			return true;
		}else {
			return false;
		}	
		

	}
	
	// delete orders
	@Override
	public int deleteOrders(int[] orderIds) {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		session.beginTransaction();
		String hql = "";
        for(int i=0;i<orderIds.length;i++) {
            if(i==0) {
                hql = "order_id="+orderIds[i];
            } else {
                hql =hql + " or order_id="+orderIds[i];
            }
        }
        Query query = session.createQuery("Delete from Order where "+hql);
        int result = query.executeUpdate();
		session.getTransaction().commit();
		if (result>0) {
			return result;
		}else {
			return -1;
		}	
	}

	// check status of all orders of one user
	@Override
	public List<Order> getUserOrders(User user) {
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String hql = "from Order where creater_id=:userId order by status_id asc , update_time desc";  
		Query query = session.createQuery(hql);
		query.setParameter("userId", user.getUserId());
		List<Order> result = query.list();
		Iterator iterator = result.iterator();
		if(iterator.hasNext()) {
			
			return result;
		}
		else {
			return null;
		}
	}

	@Override
	public List<Order> getBusinessOrders(User user) {
		// TODO Auto-generated method stub
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		String hql = "from Order where passenger_id=:userId and creater_id!=:userId order by status_id asc , update_time desc";  
		Query query = session.createQuery(hql);
		query.setParameter("userId", user.getUserId());
		List<Order> result = query.list();
		Iterator iterator = result.iterator();
		if(iterator.hasNext()) {
			
			return result;
		}
		else {
			return null;
		}
	}

	
}
