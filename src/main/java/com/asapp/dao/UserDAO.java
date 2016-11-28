package com.asapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.asapp.rest.model.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

@Repository
public class UserDAO {

	private DataSource dataSource;

	public UserDAO() {

		MysqlDataSource localDataSource = new MysqlDataSource();

		localDataSource.setUser("b2a72f483c1f0a");
		localDataSource.setPassword("ad5c2577");
		localDataSource
				.setUrl("jdbc:mysql://us-cdbr-iron-east-04.cleardb.net/ad_cb81bf59e2f38f9");

		dataSource = localDataSource;
	}

	public User findUser(User user) {

		String queryString = "SELECT * FROM ad_cb81bf59e2f38f9.users WHERE ID = "
				+ "\"" + user.getUser() + "\"";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(queryString);

			User userResult = null;
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				userResult = new User(rs.getString("ID"), rs.getString("PASS"));
			}
			rs.close();
			ps.close();
			return userResult;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean isExistingUserName(User user) {
		String queryString = "SELECT COUNT(ID) FROM ad_cb81bf59e2f38f9.users WHERE ID = "
				+ "\"" + user.getUser() + "\"";

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

			if (userResult != 0) {
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
				} catch (SQLException e) {
				}
			}
		}
	}

	public boolean createUserName(User user) {
		String queryString = "INSERT INTO ad_cb81bf59e2f38f9.users VALUE( \"" + user.getUser() + "\", \""
				+ user.getPass() + "\" );";

		Connection conn = null;

		try {
			conn = dataSource.getConnection();

			Statement executeStatement = conn.createStatement();

			executeStatement.executeUpdate(queryString.toString());

			executeStatement.close();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}
		}
	}
}
