package spring.hibernate.dao.impl;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import spring.hibernate.beans.Employee;
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

//		Employee employee = (Employee) session.merge(object);
//		employee.setEmployeeName("SRI");
//		session.persist(employee);
//		session.flush();
		
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
