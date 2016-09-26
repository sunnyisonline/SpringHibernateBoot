package spring.hibernate.dao;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface SpringHibernateDao {
	public List<?> all(Class<?> entityClass);

	public void update(Class<?> entityClass, Map<String, Object> values,
			Map<String, Object> where);

	public int insert(Object object);
	
	public void delete(Object object);
	
	public UserDetails loadUserByUsername(String username);
}
