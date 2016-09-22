package spring.hibernate.configuration;

import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import spring.hibernate.beans.Employee;
import custom.schedular.timer.CustomTimer;

@Configuration
@PropertySource(value = "application.properties")
@EnableTransactionManagement
public class Conguration {

	@Value("${mysql.driver}")
	private String driverClassName;

	@Value("${mysql.url}")
	private String url;

	@Value("${mysql.username}")
	private String username;

	@Value("${mysql.password}")
	private String password;

	// @PostConstruct (To Enable Custom Timer, un-comment that.)
	public void callTimer() {
		Timer timer = new Timer();
		timer.schedule(new CustomTimer(), new Date(), 1000);
	}

	@Bean
	public DriverManagerDataSource driverManagerDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(driverClassName);
		driverManagerDataSource.setUrl(url);
		driverManagerDataSource.setUsername(username);
		driverManagerDataSource.setPassword(password);
		System.out.println("Driver Class - " + driverClassName);
		System.out.println("URL - " + url);
		System.out.println("Username - " + username);
		System.out.println("Password - " + password);
		return driverManagerDataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		// localSessionFactoryBean.setPackagesToScan("spring.hibernate.beans");
		localSessionFactoryBean.setAnnotatedClasses(Employee.class);
		localSessionFactoryBean.setDataSource(driverManagerDataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", MySQL5Dialect.class);
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties.put("hibernate.format_sql", "true");
		localSessionFactoryBean.setHibernateProperties(hibernateProperties);
		return localSessionFactoryBean;
	}

	@Bean
	@Autowired
	public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
		HibernateTemplate hibernateTemplate = new HibernateTemplate();
		hibernateTemplate.setSessionFactory(sessionFactory);
		return hibernateTemplate;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager hibernateTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager hibernateTransactionManager = new HibernateTransactionManager();
		hibernateTransactionManager.setSessionFactory(sessionFactory);
		return hibernateTransactionManager;
	}
}
