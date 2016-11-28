package com.asapp.dao;

import com.asapp.rest.model.Message;
import com.asapp.rest.model.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MessageDAO {

	private DataSource dataSource;

	public MessageDAO() {
		
		MysqlDataSource localDataSource = new MysqlDataSource();
		
		localDataSource.setUser("b2a72f483c1f0a");
		localDataSource.setPassword("ad5c2577");
		localDataSource.setUrl("jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_cb81bf59e2f38f9");
		
		dataSource = localDataSource;
	}
	
	public void insert(User user) {
		
	}
	
	public <List>Message findMessages(User user) {
		
		String queryString = "SELECT * FROM ad_cb81bf59e2f38f9.users WHERE ID = " + "\"" + user.getId() + "\"";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(queryString);
			
			User userResult = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userResult = new User(
					rs.getString("ID"),
					rs.getString("PASS")
				);
			}
			rs.close();
			ps.close();
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}

	public boolean isExistingUserName(User user) {
		String queryString = "SELECT COUNT(ID) FROM ad_cb81bf59e2f38f9.users WHERE ID = " + "\"" + user.getId() + "\"";
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(queryString);
			
			int userResult = 0;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userResult = rs.getInt("COUNT(ID)");
					
			}
			rs.close();
			ps.close();
			
			if(userResult != 0) {
				return true;
			} else {
				return false;
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
				conn.close();
				} catch (SQLException e) {}
			}
		}
	}
}
