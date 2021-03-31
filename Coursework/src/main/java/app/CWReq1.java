package app;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.BaseQuery;

public class CWReq1 extends BaseQuery{

	public CWReq1(String configFilePath) throws FileNotFoundException {
		super(configFilePath);
	}
	
	/* -------------------------------------------------------------
	 * TODO: getActual() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The getActual() method returns yours requirement code's output.
	 * In this instance, the return type is a String, you are free to choose
	 * other return types depending on the requirement. You are allowed to 
	 * write additional helper methods.
	 * ---------------------------------------------------------------------
	 */
	
	public int getActual() throws SQLException {
		

		ArrayList<customer> customerlist = new ArrayList<customer>();
		customer a;
		
		int customer_id;
		int store_id;
		String first_name;
		String last_name;
		String email;
		int address_id;
		int active;
		Date last_update;
		
		
		ResultSet rs1 = this.getResultSet("Select * from customer");
		while(rs1.next()) {
			
			customer_id=rs1.getInt("customer_id");
		
			first_name=rs1.getString("first_name");
			last_name=rs1.getNString("last_name");
			
			address_id=rs1.getInt("address_id");
			
			
			
			
			a = new  customer(customer_id,first_name,last_name,address_id);
			customerlist.add(a);
		}
		
		
		return customerlist.size();
	}
	
	
	/* -------------------------------------------------------------
	 * TODO: printOutput() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ----------------------------------------------------------------------
	 * The printOutput() method prints result of your requirement code 
	 * onto the console for the end-user to view. This method should
	 * rely on the requirement code results obtained through the getActual() 
	 * method, decorate it in a human friendly format and display the results 
	 * on the console. It is possible that this method may need to get additional 
	 * data to make the output human friendly. For example, if the requirement 
	 * code returns only the customer IDs, this method may additionally 
	 * want to fetch the customer names to make the output human-friendly.
	 * You are allowed to write additional helper methods.
	 * ----------------------------------------------------------------------
	 */
	
	public void printOutput() throws SQLException{
		System.out.println(getActual());
	}

}
