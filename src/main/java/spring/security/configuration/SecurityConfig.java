package spring.security.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

//@EnableWebSecurity (Not needed for Spring Boot, as its already initialized)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		System.out.println("SRI .....");
		System.out.println("Setting Authentication Manager Builder ...");
		authenticationManagerBuilder.inMemoryAuthentication()
				.withUser("sunny.k").password("sunny.k").roles("ADMIN");
	}

	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		System.out.println("Configuring HttpSecurity ...");
		httpSecurity = httpSecurity.httpBasic().disable();
		httpSecurity = httpSecurity.authorizeRequests()
				.antMatchers("/", "/login").permitAll().and();
		httpSecurity = httpSecurity.authorizeRequests()
				.antMatchers("/security/**").access("hasRole('ROLE_ADMIN')")
				.and();
		httpSecurity = httpSecurity.formLogin().loginPage("/login")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and();
		httpSecurity = httpSecurity.logout()
				.logoutUrl("/j_spring_security_logout")
				.logoutSuccessUrl("/login?logout").and();
		httpSecurity = httpSecurity.csrf()
				.csrfTokenRepository(csrfTokenRepository()).and();
		httpSecurity = httpSecurity.addFilterAfter(csrfHeaderFilter(),
				CsrfFilter.class);
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request,
					HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request
						.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					System.out.println("Got Header - " + csrf.getHeaderName());
					System.out.println("Got Token - " + csrf.getToken());
					System.out.println("Got Parameter - "
							+ csrf.getParameterName());
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};

	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}
}
