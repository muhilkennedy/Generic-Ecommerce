package com.platform.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.logging.Log;

/**
 * @author Muhil
 * NOTE: advised not use, if possible go with JPA only!
 *
 */
@Component
public class DatabaseUtil {

	private static String url;
	private static String userName;
	private static String password;

	@Value("${platform.database.url}")
	public void setDatabaseUrl(String url) {
		DatabaseUtil.url = url;
	}
	
	@Value("${platform.database.username}")
	public void setDatabaseUsername(String userName) {
		DatabaseUtil.userName = userName;
	}
	
	@Value("${platform.database.password}")
	public void setDatabasePassword(String password) {
		DatabaseUtil.password = password;
	}

	public static Connection getConnectionInstance() throws SQLException {
		return DriverManager.getConnection(url, userName, password);
	}

	public static boolean executeDML(String sql) throws SQLException {
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			boolean isDone = statement.execute(sql);
			Log.platform.debug("executeDML : Excecuted query {} , with execution status {}", sql, isDone);
			return isDone;
		}
	}

	public static List<?> executeDQL(String sql, Class<?> resultClass) throws SQLException {
		List result = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			Log.platform.debug("executeDQL : Excecuted query {}", sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				for (int _iterator = 0; _iterator < rsmd.getColumnCount(); _iterator++) {
					Map<String, Object> resultMap = new HashMap<String, Object>();
					String columnName = rsmd.getColumnName(_iterator + 1);
					Object columnValue = rs.getObject(_iterator + 1);
					Log.platform.debug("executeDQL : ColumnName - {} , ColumnValue - {}", columnValue, columnValue);
					resultMap.put(columnName, columnValue);
					result.add(mapper.convertValue(resultMap, resultClass));
				}
			}
		}
		Log.platform.debug("executeDQL : DQL result {}", result.toArray());
		return result;
	}
	
	public static List<Object> executeDQL(String sql) throws SQLException {
		 List<Object> result = new ArrayList<Object>();
		try (Connection connection = getConnectionInstance()) {
			Statement statement = connection.createStatement();
			Log.platform.debug("executeDQL : Excecuting query {}", sql);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				result.add(rs.getObject(1));
			}
		}
		return result;
	}
	
	public static Object executeDQL(String sql, List<?> clauseValues) throws SQLException {
		try (Connection connection = getConnectionInstance()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (int index = 0; index < clauseValues.size(); index++) {
				statement.setObject(index+1, clauseValues.get(index));
			}
			Log.platform.debug("executeDQL : Excecuted query {}", statement);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				return rs.getObject(1);
			}
		}
		return null;
	}

}
