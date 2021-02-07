package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
/**
 * Hello world!
 *
 */

public class BaseQuery 
{
	
	private DBConfig config;
	private Connection con;
	
	public BaseQuery(String configFilePath) throws FileNotFoundException {
		    BufferedReader bufferedReader = new BufferedReader(new FileReader(configFilePath));
		    Gson gson = new Gson();
		    this.config = gson.fromJson(bufferedReader, DBConfig.class);
	}
	
	public void openconn() throws ClassNotFoundException, SQLException {
		System.out.println("Opening connection with database.\n");
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		String remote = "jdbc:mysql://" + this.config.getHost() 
						+ ":" + this.config.getPort()+ "/" 
						+ this.config.getdbName();
		
		this.con=DriverManager.getConnection( remote, 
	    		this.config.getUsername(),this.config.getPassword());  
	}
	
	public void closeconn() throws SQLException {
		System.out.println("Closing connection with database.\n");
		this.con.close();
	}
	
	public ResultSet getResultSet(String sql) {
    	try{  
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery(sql);  
    		return rs;
    	} 
		catch(Exception e){ 
				System.out.println(e);
		}
		return null;
	}
	
	public void unimplementedMessage() {
		String method = new Throwable().getStackTrace()[1].getMethodName();
		String className = new Throwable().getStackTrace()[1].getClassName();
		System.out.println("Note: Method " + method + "(...) not implemented in class " + className + ".\n");
	}
	
}
