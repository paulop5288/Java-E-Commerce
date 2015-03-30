
import java.sql.*;


public class DBConnection {
	private Connection con = null;
    private Statement stmt = null;
    private int count;
	
//	public static void main(String args[]) {
//		DBConnection dbConnection = new DBConnection();
//		dbConnection.getConnection();
//		ResultSet resultSet = dbConnection.executeQuery("select * from user;");
//
//		try {
//			while (resultSet.next()) {
//				String id = resultSet.getString("id");
//				System.out.println(id);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			System.out.println("error");
//		}
//		dbConnection.closeConnection();
//	}
    public DBConnection() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("fail to load driver.");
		}
    }
    
    public ResultSet executeQuery(String query) {
    	ResultSet res = null;
    	try {
    		res = stmt.executeQuery(query);
    	}
    	catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
    }
    
    public void updateQuery(String query) {
		try {
			count = stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    public void getConnection() {
		try {
			con = DriverManager.getConnection
					("jdbc:mysql://stusql.dcs.shef.ac.uk/team158", "team158","9a5b309d");
			stmt = con.createStatement();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("...");
		}
	}
    
    public void closeConnection() {
		try {
			stmt.close();
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			if (con != null) try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}

