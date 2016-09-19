package spring.hibernate.beans;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

	@Id
	@Column(name = "emp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int employeeId;

	@Column(name = "emp_name")
	String employeeName;

	public Employee() {

	}

	public Employee(String employeeName) {
		this.employeeName = employeeName;
	}

	public Employee(int employeeId, String employeeName) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", employeeName="
				+ employeeName + "]";
	}

}
