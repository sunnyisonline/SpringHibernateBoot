/*
 * Table Schemas required
 * 
 * CREATE TABLE `address` (
 `employee_id` int(11) NOT NULL AUTO_INCREMENT,
 `address1` varchar(50) DEFAULT NULL,
 PRIMARY KEY (`employee_id`),
 CONSTRAINT `employee_foreign` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`emp_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

 CREATE TABLE `employee` (
 `emp_id` int(11) NOT NULL AUTO_INCREMENT,
 `emp_name` varchar(50) DEFAULT NULL,
 PRIMARY KEY (`emp_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

 CREATE TABLE `user_master` (
 `user_id` int(11) NOT NULL AUTO_INCREMENT,
 `user_name` varchar(50) DEFAULT NULL,
 `password` varchar(50) DEFAULT NULL,
 `role` varchar(50) DEFAULT NULL,
 PRIMARY KEY (`user_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
 */

package spring.hibernate.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import spring.hibernate.beans.Employee;
import spring.hibernate.beans.UserMaster;
import spring.hibernate.dao.SpringHibernateDao;

@Repository
public class SpringHibernateDaoImpl implements SpringHibernateDao, Serializable {

	private static final long serialVersionUID = -3567909367643400797L;
	private static final String APPENDER = "arg";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<?> all(Class<?> entityClass) {
		Session session = openSession();
		List<Employee> employeeList = createCriteria(entityClass, session)
				.list();
		closeSession(session);
		return employeeList;
	}

	// Manual Query
	@Override
	public void update(Class<?> entityClass, Map<String, Object> values,
			Map<String, Object> where) {
		StringBuilder stringBuilder = new StringBuilder(" UPDATE ");
		stringBuilder.append(" " + entityClass.getSimpleName() + " AS e ");
		stringBuilder.append(" SET ");
		int counter = 0;
		for (Entry<String, Object> entry : values.entrySet()) {
			stringBuilder.append(" e." + entry.getKey() + " ");
			stringBuilder.append(" = ");
			stringBuilder.append(" :" + APPENDER + (counter++) + " ");
		}
		stringBuilder.append(" WHERE ");
		for (Entry<String, Object> entry : where.entrySet()) {
			stringBuilder.append(" e." + entry.getKey() + " ");
			stringBuilder.append(" = ");
			stringBuilder.append(" :" + APPENDER + (counter++) + " ");
		}
		System.out.println("Query - " + stringBuilder);
		Session session = openSession();
		Query query = session.createQuery(stringBuilder.toString());
		counter = 0;
		Iterator<Object> iterator = values.values().iterator();
		while (iterator.hasNext()) {
			query.setParameter(APPENDER + counter++, iterator.next());
		}
		iterator = where.values().iterator();
		while (iterator.hasNext()) {
			query.setParameter(APPENDER + counter++, iterator.next());
		}
		System.out.println("Final Query - " + query.getQueryString());
		query.executeUpdate();
		closeSession(session);
	}

	@Override
	public int insert(Object object) {
		Session session = openSession();
		// session.save(object); //1
		// session.close(); //1

		// session.saveOrUpdate(object); //2

		// Employee employee = session.get(Employee.class, 3);
		// System.out.println("Employee GET - "+employee);
		// employee.setEmployeeName("SRI GANESH");
		// session.persist(employee);
		// session.flush(); //Mandatory for Persist

		// Employee employee = (Employee) session.merge(object);
		// employee.setEmployeeName("SRI");
		// session.persist(employee);
		// session.flush();

		session.save(object);
		session.flush();

		session.close();
		return 1;
	}

	@Override
	public void delete(Object object) {
		Session session = openSession();
		System.out.println("Object - " + (Employee) object);
		session.delete(object);
		session.flush(); // Mandatory for Delete
		session.close();
	}

	@Override
	public UserDetails loadUserByUsername(String userName) {
		Session session = openSession();
		Criteria criteria = session.createCriteria(UserMaster.class);
		criteria.add(Restrictions.eq("userName", userName));
		criteria.setFetchSize(1);
		criteria.setMaxResults(1);
		// List<UserMaster> list = criteria.list();
		// System.out.println("List - " + list);
		// UserMaster userMaster = null == list ? null : list.get(0);
		UserMaster userMaster = (UserMaster) criteria.uniqueResult();
		List<SimpleGrantedAuthority> grantedAutherityList = new ArrayList<SimpleGrantedAuthority>();
		grantedAutherityList.add(new SimpleGrantedAuthority(userMaster
				.getRoleName()));
		UserDetails userDetails = new User(userMaster.getUserName(),
				userMaster.getPassword(), true, true, true, true,
				grantedAutherityList);
		closeSession(session);
		return userDetails;
	}

	private Session openSession() {
		return sessionFactory.openSession();
	}

	private void closeSession(Session session) {
		session.close();
	}

	private Criteria createCriteria(Class<?> persistentClass, Session session) {
		return session.createCriteria(persistentClass);
	}
}
