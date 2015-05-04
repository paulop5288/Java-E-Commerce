package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private String role = "", username = "", title = "", firstName = "", lastName = "";
	private String qualification = "", origanisation = "", specification = "";
	private int id = 0,credit = 0;
	private boolean isMatched = false;
	public User(String username,String password) {
		DBConnection dbConnection = new DBConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String queryString = "SELECT * FROM author WHERE username = ? AND"
				+ " password = ?;";
		try {
			pstm = dbConnection.createPreparedStatement(queryString);
			pstm.setString(1, username);
			pstm.setString(2, password);
			resultSet = dbConnection.executeQuery(pstm);
			if (resultSet.next()) {
				id = resultSet.getInt("authorID");
				credit = resultSet.getInt("credit");
				title = resultSet.getString("title");
				this.username = username;
				firstName = resultSet.getString("firstName");
				lastName = resultSet.getString("lastName");
				qualification = resultSet.getString("qualification");
				origanisation = resultSet.getString("organisation");
				specification = resultSet.getString("specialisation");
				isMatched = true;
			} else {
				isMatched = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbConnection.closeConnection();
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean isMatched() {
		return isMatched;
	}
	
	public int getID() {
		return id;
	}
	
	public int getCredit() {
		return credit;
	}
	
	public String getFirstName() {
		if (firstName == null) {
			return "";
		}
		return firstName;
	}
	
	public String getLastName() {
		if (lastName == null) {
			return "";
		}
		return lastName;
	}
	
	public String getOriganisation() {
		if (origanisation == null) {
			return "";
		}
		return origanisation;
	}
	
	public String getQualification() {
		if (qualification == null) {
			return "";
		}
		return qualification;
	}
	
	public String getRole() {
		if (role == null) {
			return "";
		}
		return role;
	}
	
	public String getSpecification() {
		if (specification == null) {
			return "";
		}
		return specification;
	}
	
	public String getTitle() {
		if (title == null) {
			return "";
		}
		return title;
	}
	
	public String getUsername() {
		if (username == null) {
			return "";
		}
		return username;
	}
	public static void main(String[] args) {
		User auUser = new User("xwang104@sheffield.ac.uk","f4a71e5d6f43");
		System.out.println(auUser.getFirstName());
	}

}
