package app;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import db.BaseQuery;

public class CWReq2 extends BaseQuery{

	public CWReq2(String configFilePath) throws FileNotFoundException {
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
	
	public String getActual() throws SQLException {
		
		
		
		customer a;
		
		
		ArrayList<customer> allcustomer = new ArrayList<customer>();
		
		ArrayList<rental>  allrental =new ArrayList<rental>();
		
		int customer_id_WithHighestRent;
		
		allcustomer = getcustomer();
		allrental = getrental();
		
		
		customer_id_WithHighestRent =gethighestrental(allrental);
		
		
		
		a =getcustomerdetails(allcustomer, customer_id_WithHighestRent);
		
		
		
		
		
		
		
		
		StringBuffer A = new StringBuffer();
		A.append(a.getCustomer_id()+" "+a.getFirst_name()+" "+a.getLast_name());
		
		return A.toString();
	}
	
	
	
	
	/**
	 * Retrieve all Customer from the database and create the Customer objects
	 * 
	 * @return list of customer
	 * @throws SQLException
	 */
	public ArrayList<customer> getcustomer() throws SQLException {
		

		ArrayList<customer> customerlist = new ArrayList<customer>();
		customer a;	
		int customer_id;	
		String first_name;
		String last_name;	
		int address_id;
		

		//iterate over the ResultSet to create an ArrayList of Customer objects
		ResultSet rs2customer = this.getResultSet("Select * from customer");
		while(rs2customer.next()) {
			
			customer_id=rs2customer.getInt("customer_id");
		
			first_name=rs2customer.getString("first_name");
			last_name=rs2customer.getNString("last_name");
			
			address_id=rs2customer.getInt("address_id");
			
			
			
			
			a = new  customer(customer_id,first_name,last_name,address_id);
			customerlist.add(a);
		}
		
		return customerlist;
		
	}
	
	
	
	
	/**Retrieve all Rental from the database and create the Rental objects
	 * 
	 * 
	 * @return list of all rental
	 * @throws SQLException
	 */
	public ArrayList<rental> getrental() throws SQLException {
		
		
		ArrayList<rental>  rentallist =new ArrayList<rental>();	
		rental a;
		int rental_id;
		int inventory_id;
		int customer_id;
		
		

		//iterate over the ResultSet to create an ArrayList of  Rental objects
		ResultSet rs2rental =this.getResultSet("Select * from rental");
		
		while(rs2rental.next()) {
			
			
			rental_id=rs2rental.getInt("rental_id");
			inventory_id=rs2rental.getInt("inventory_id");
			customer_id=rs2rental.getInt("customer_id");
			
			a= new rental(rental_id,inventory_id,customer_id);
			
			rentallist.add(a);
		}

		
		return rentallist;
	}
	
	
	
	
	/**get a customer id which has a highest rental from list of rental
	 * 
	 * @param rentallist List of a rental
	 * @return customer_id 
	 */
	public int gethighestrental(ArrayList<rental>  rentallist) {
		
		Map<Integer,Integer> rentalcount =new HashMap<Integer,Integer>();
		
		
		for(rental i :rentallist) {
			
			if(rentalcount.containsKey(i.getCustomer_id())) {
				int count = rentalcount.get(i.getCustomer_id());
				
				rentalcount.put(i.getCustomer_id(), count+1);
				
				
			}else {
				rentalcount.put(i.getCustomer_id(), 1);
			}	
		}
		
		
		Map.Entry<Integer,Integer> Max=null;
		
		int customer_id; //contain customer id which have a highest rental
		
		
		for(Map.Entry<Integer, Integer> entryI : rentalcount.entrySet()) {
			
			if(Max==null||entryI.getValue()>Max.getValue()) {
				Max=entryI;	
			}
			
		}
		
		customer_id=Max.getKey();
		
		
		
		return customer_id;
		
	}
	
	
	
	/**get a details of a customer which have highest rental from customer list
	 * 
	 * @param allcustomer
	 * @param customer_id
	 * @return customer which have highest rental
	 */
	public customer getcustomerdetails(ArrayList<customer> allcustomer,int customer_id) {
		
		customer answer = null;
	
		for(customer i: allcustomer) {		
			if(customer_id==i.getCustomer_id()) {
				answer=i;
				break;		
			}			
		}	
		
		
		
		return answer;
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
