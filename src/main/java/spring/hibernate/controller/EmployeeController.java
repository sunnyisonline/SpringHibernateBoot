package spring.hibernate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import spring.hibernate.beans.Employee;
import spring.hibernate.beans.UserMaster;
import spring.hibernate.service.SpringHibernateService;

@Controller
public class EmployeeController {

	@Autowired
	SpringHibernateService springHibernateService;

	@RequestMapping(value = "/getEmployee", method = RequestMethod.GET)
	@ResponseBody
	public List allEmployees() {
		return springHibernateService.all(Employee.class);
	}

	@RequestMapping(value = "/updateEmployee", method = RequestMethod.GET)
	@ResponseBody
	public void updateEmployee() {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("employeeName", "II");
		Map<String, Object> where = new HashMap<String, Object>();
		where.put("employeeId", 2);
		springHibernateService.update(Employee.class, values, where);
	}

	@RequestMapping(value = "/insertEmployee/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public void insertEmployee(@PathVariable int employeeId,
			@RequestParam(value = "employeeName") String employeeName) {
		System.out.println("employeeId - " + employeeId);
		System.out.println("employeeName - " + employeeName);
		springHibernateService.insert(new Employee(employeeId, employeeName));
	}

	@RequestMapping(value = "/deleteEmployee/{employeeId}", method = RequestMethod.GET)
	@ResponseBody
	public void deleteEmployee(@PathVariable int employeeId,
			@RequestParam(value = "employeeName") String employeeName) {
		System.out.println("employeeId - " + employeeId);
		System.out.println("employeeName - " + employeeName);
		springHibernateService.delete(new Employee(employeeId, employeeName));
	}
	
	@RequestMapping(value = "/insertUser/{userName}/{password}/{role}", method = RequestMethod.GET)
	@ResponseBody
	public void insertUser(@PathVariable String userName, @PathVariable String password, @PathVariable String role) {
		System.out.println("userName - " + userName);
		System.out.println("password - " + password);
		System.out.println("role - " + role);
		springHibernateService.insert(new UserMaster(userName, password, role));
	}
}
