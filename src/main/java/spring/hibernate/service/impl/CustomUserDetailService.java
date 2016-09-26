package spring.hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import spring.hibernate.dao.SpringHibernateDao;

@Component(value="customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	SpringHibernateDao springHibernateDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return springHibernateDao.loadUserByUsername(username);
	}

}
