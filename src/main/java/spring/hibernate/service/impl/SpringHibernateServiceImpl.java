package spring.hibernate.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.hibernate.dao.SpringHibernateDao;
import spring.hibernate.service.SpringHibernateService;

@Service
@Transactional
public class SpringHibernateServiceImpl implements SpringHibernateService,
		Serializable {

	private static final long serialVersionUID = -6043044834702894952L;

	@Autowired
	SpringHibernateDao springHibernateDao;

	@Override
	public List<?> all(Class<?> entityClass) {
		return springHibernateDao.all(entityClass);
	}

	@Override
	public void update(Class<?> entityClass, Map<String, Object> values,
			Map<String, Object> where) {
		springHibernateDao.update(entityClass, values, where);
	}

	@Override
	public int insert(Object object) {
		return springHibernateDao.insert(object);
	}

	@Override
	public void delete(Object object) {
		springHibernateDao.delete(object);
	}
}
