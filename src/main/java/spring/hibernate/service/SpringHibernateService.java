package spring.hibernate.service;

import java.util.List;
import java.util.Map;

public interface SpringHibernateService {

	public List<?> all(Class<?> entityClass);

	public void update(Class<?> entityClass, Map<String, Object> values,
			Map<String, Object> where);

	public int insert(Object object);
	
	public void delete(Object object);
}
