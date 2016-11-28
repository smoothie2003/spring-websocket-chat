package com.asapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.asapp.rest.model.Message;
import com.asapp.rest.model.User;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
	
	public boolean insertMessage(Message message) {
		
		StringBuilder queryString = new StringBuilder();
		queryString.append("INSERT");
		queryString.append(" INTO ad_cb81bf59e2f38f9.messages");
		queryString.append(" VALUE")
				   .append("(\"").append(message.getSender()).append("\"")
				   .append(",\"").append(message.getReceiver()).append("\"")
				   .append(",\"").append(message.getMessageBody()).append("\"")
				   .append(",\"").append(message.getMetaData()).append("\"")
				   .append(",\"").append(MessageDAO.getDateTime()).append("\"")
				   .append(",\"").append(message.getMessageType()).append("\");");
		
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
				} catch (SQLException e) {}
			}
		}
				
		
	}
	
	public List<Message> getMessages(String sender,String receiver,String limit,String page) {
		
		Integer pageNumber = Integer.valueOf(limit) * (Integer.valueOf(page) - 1);
		
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT * ")
				   .append("FROM ad_cb81bf59e2f38f9.messages ")
				   .append("WHERE sender = ").append("\"").append(sender).append("\" ")
				   .append("AND receiver = ").append("\"").append(receiver).append("\" ")
				   .append("ORDER BY timestamp ")
				   .append(" LIMIT ").append(limit)
				   .append(" OFFSET ").append(pageNumber).append(";");
		
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(queryString.toString());
			
			ResultSet rs = ps.executeQuery();
			
			List<Message> messages = new ArrayList<Message>();
			
			while (rs.next()) {
				Message message = new Message();
				message.setSender(rs.getString("SENDER"));
				message.setReceiver(rs.getString("RECEIVER"));
				message.setMessageBody(rs.getString("MESSAGE"));
				message.setMessageType(rs.getString("MESSAGETYPE"));
				message.setMetaData(rs.getString("METADATA"));
				
				Timestamp timestamp = rs.getTimestamp("timeStamp");
				
				Date date = new Date(timestamp.getTime());
				
				message.setTimeStamp(date.toString());
				
				messages.add(message);
			}
			rs.close();
			ps.close();
			return messages;
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
		String queryString = "SELECT COUNT(ID) FROM ad_cb81bf59e2f38f9.users WHERE ID = " + "\"" + user.getUser() + "\"";
		
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
	
	private static String getDateTime() {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String dateTimeFormat = dateFormat.format(date);
		
		return dateTimeFormat;
	}
}
