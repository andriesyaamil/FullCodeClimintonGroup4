package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private static Connect instance;
	
	private String username = "root";
	private String password = "";
	
	private String host = "localhost:3306";
	private String database = "clminton";
	private String url = String.format("jdbc:mysql://%s/%s", host,database);
	
	public ResultSet rs;
	public ResultSetMetaData rsm;
	
	private Connection con;
	private Statement st;
	private static Connect connect;
	
	public static Connect getInstance() {
		if(connect == null) return new Connect();
		return connect;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url,username,password);
			st = con.createStatement();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet exeQuery(String Query) {
		try {
			rs = st.executeQuery(Query);
			rsm = rs.getMetaData();
		}catch(SQLException e ) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
		
	}
	
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
	
	
}
