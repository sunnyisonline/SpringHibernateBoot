package spring.hibernate.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "address")
public class EmployeeAddress {

	@Id
	@Column
	@GeneratedValue(generator = "seq")
	@GenericGenerator(name = "seq", strategy = "foreign", parameters = @Parameter(name = "property", value = "employee"))
	private int employeeId;

	@Column(name = "address1")
	private String address1;

	public EmployeeAddress() {
	}

	public EmployeeAddress(String address1) {
		this.address1 = address1;
	}

	public EmployeeAddress(int employeeId, String address1) {
		this.employeeId = employeeId;
		this.address1 = address1;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	@Override
	public String toString() {
		return "EmployeeAddress [employeeId=" + employeeId + ", address1="
				+ address1 + "]";
	}

}
