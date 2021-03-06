package spring.security.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@Autowired
	HttpServletRequest httpServletRequest;

	@RequestMapping(value = { "/", "/login", "/error" }, method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView login() {
		return new ModelAndView("loginPage");
	}

	@RequestMapping(value = "/security/index", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView index() {
		System.out.println("Hitting - index()");
		return new ModelAndView("index");
	}
}
