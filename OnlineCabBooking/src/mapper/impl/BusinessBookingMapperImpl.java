package mapper.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import domain.Company;
import domain.Order;
import domain.User;
import mapper.IndividualBookingMapper;
import mapper.BusinessBookingMapper;

@Repository("businessBooking")
public class BusinessBookingMapperImpl implements BusinessBookingMapper{

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
		try {
			// session will check version then update order.
			session.update(order);
			session.getTransaction().commit();
			result = true;
		}
		catch(Exception e) {
			
		}

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
		
		String hql = "Delete from Order where order_id=:orderId";
		Query query = session.createQuery(hql);
		query.setParameter("orderId", order.getOrderId());
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

	// get all orders of one user
	@Override
	public List<Order> getEmployeeOrders(User user,Company company) {
		
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Object object = session.load(Company.class, company.getCompanyId());
		Company currentCompany = (Company) object;
		
		// get all business orders from business user
		List<Order> businessOrder = new ArrayList<Order>();
		for(User receptionist: currentCompany.getReceptionistSet()) {
			businessOrder.addAll(receptionist.getOrders());
		}
		
		// find the orders to the specific employee by userid
		List<Order> employeeOrder = new ArrayList<Order>();
		Iterator orderIterator = businessOrder.iterator();
		while(orderIterator.hasNext()) {
			Order order = (Order) orderIterator.next();
			if(order.getPassenger().getUserId()==user.getUserId()) {
				employeeOrder.add(order);
			}
		}

		
		return employeeOrder;
	}

	// get all orders for a company
	@Override
	public List<Order> getCompanyOrders(Company company) {
		// TODO Auto-generated method stub
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Object object = session.load(Company.class, company.getCompanyId());
		Company currentCompany = (Company) object;
		
		// get all business orders from business user
		List<Order> employeeOrder = new ArrayList<Order>();
		for(User receptionist: currentCompany.getReceptionistSet()) {
			employeeOrder.addAll(receptionist.getOrders());
		}
		
		return employeeOrder;
	}

	@Override
	public List<User> getCompanyEmployees(Company company) {
		// TODO Auto-generated method stub
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		
		Object object = session.load(Company.class, company.getCompanyId());
		Company currentCompany = (Company) object;
		
		// get all employees in a company with the help of lazy load pattern
		List<User> companyEmployee = new ArrayList<User>();
		companyEmployee.addAll(currentCompany.getEmployees());
		Iterator iterator = companyEmployee.iterator();
		if(iterator.hasNext()) {
			return companyEmployee;
		}
		else {
			return null;
		}
	}

	
}
