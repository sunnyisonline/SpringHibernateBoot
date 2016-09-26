package spring.hibernate.beans;

import javax.persistence.*;

@Entity
@Table(name = "user_master", schema = "test")
public class UserMaster {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int userId;

	@Column(name = "user_name")
	String userName;

	@Column(name = "password")
	String password;

	@Column(name = "role")
	String roleName;

	public UserMaster() {

	}

	public UserMaster(String userName, String password, String role) {
		this.userName = userName;
		this.password = password;
		this.roleName = role;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "UserMaster [userId=" + userId + ", userName=" + userName
				+ ", password=" + password + ", roleName=" + roleName + "]";
	}

}
