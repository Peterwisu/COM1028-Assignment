package app;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import db.BaseQuery;

public class CWReq3 extends BaseQuery{

	public CWReq3(String configFilePath) throws FileNotFoundException {
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
	
	public ArrayList<String> getActual() throws SQLException {
		
		
		
		Map<Integer, Double> PriceinEachInventory= getInventory_cost(getfilm(),getinventory());

		Map<Integer, Double> PriceinEachrental=getrentalprice(getrental(),PriceinEachInventory);
		
		
		Map<Integer, Double> CustomerIDRevenue=getCustomerIDRevenue(getrental(), PriceinEachrental) ;
		
		
		
		
		Map<customer, Double>  cus=getcustomer_with_revenue(CustomerIDRevenue,getcustomer());
		DecimalFormat df = new DecimalFormat("#.##");

		
		
		ArrayList<customer> finalcustomerlist = new ArrayList<customer>();
    	ArrayList<Double> revenuelist =new ArrayList<Double>();
		
		
		int count=0;
	
		for(Entry<customer, Double> i: cus.entrySet()) {
			if(count!=10) {
				
				
				finalcustomerlist.add(i.getKey());
				revenuelist.add(i.getValue());
				
			
			
			count++;
			
			}else {
				break;
			}
			
		}
		
		
		
		

    	ArrayList<String> answer = new ArrayList<String>();
    	
    	
    	for(int i=0;i<finalcustomerlist.size();i++) {
    		
    		
    		
    		if(i!=finalcustomerlist.size()-1) {
    			
    			
    			if((df.format(revenuelist.get(i)).equals(df.format(revenuelist.get(i+1))))&&(finalcustomerlist.get(i).getCustomer_id()>finalcustomerlist.get(i+1).getCustomer_id())) {
    				Collections.swap(finalcustomerlist, i, i+1);
    				answer.add(finalcustomerlist.get(i).getCustomer_id()+" "+finalcustomerlist.get(i).getFirst_name()+" "+finalcustomerlist.get(i).getLast_name()+" "+df.format(revenuelist.get(i))+"\n");
        			
    			}else {
    			answer.add(finalcustomerlist.get(i).getCustomer_id()+" "+finalcustomerlist.get(i).getFirst_name()+" "+finalcustomerlist.get(i).getLast_name()+" "+df.format(revenuelist.get(i))+"\n");
    			}
    		}
    		else {
    		answer.add(finalcustomerlist.get(i).getCustomer_id()+" "+finalcustomerlist.get(i).getFirst_name()+" "+finalcustomerlist.get(i).getLast_name()+" "+df.format(revenuelist.get(i))+"\n");
    		}
    	}
		
		
		
		
		return answer;
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
		ResultSet rs3customer = this.getResultSet("Select * from customer");
		while(rs3customer.next()) {
			
			customer_id=rs3customer.getInt("customer_id");
		
			first_name=rs3customer.getString("first_name");
			last_name=rs3customer.getNString("last_name");
			
			address_id=rs3customer.getInt("address_id");
			
			
			
			
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
		ResultSet rs3rental =this.getResultSet("Select * from rental limit 50000");
		
		while(rs3rental.next()) {
			
			
			rental_id=rs3rental.getInt("rental_id");
			inventory_id=rs3rental.getInt("inventory_id");
			customer_id=rs3rental.getInt("customer_id");
			
			a= new rental(rental_id,inventory_id,customer_id);
			
			rentallist.add(a);
		}

		
		return rentallist;
	}
	
	
	
	
	public ArrayList<Film> getfilm() throws SQLException {
		ArrayList<Film> films = new ArrayList<Film>();
		
        Film f;
		
		Integer film_id;
		String title;
		String description;
		Double rental_rate;
		
		ResultSet rs3film = this.getResultSet("Select * from film");
		
		//iterate over the ResultSet to create an ArrayList of Film objects
		while(rs3film.next()) {
			film_id = rs3film.getInt("film_id");
			title = rs3film.getString("title");
			description = rs3film.getString("description");
			rental_rate = rs3film.getDouble("rental_rate");
			f = new Film(film_id, title, description, rental_rate);
			films.add(f);
		}
		
		return films;
		
	}
	
	
	
	public ArrayList<inventory> getinventory() throws SQLException {
		ArrayList<inventory> inventorylist = new ArrayList<inventory>();
		
		inventory a;
		int inventory_id;
		int film_id;
		
		
		
		ResultSet rs3inventory= this.getResultSet("select * from inventory;");
		
		while(rs3inventory.next()) {
			inventory_id=rs3inventory.getInt("inventory_id");
			film_id=rs3inventory.getInt("film_id");
			
			
			a= new inventory(inventory_id,film_id);
			inventorylist.add(a);
		}
		
		
		
		return inventorylist;
	}
	
	
	public Map<Integer, Double> getInventory_cost( ArrayList<Film> filmlist,ArrayList<inventory> inventorylist) {
		
		
		
		Map<Integer,Double>  Inventory_cost =new HashMap<Integer,Double>();
		
		
		
			
			for(Film j:filmlist) {
				
				for(inventory i:inventorylist) {
				
				
				if(j.getFilmID().equals(i.getFilm_id())) {
					Inventory_cost.put(i.getInventory_id(),j.getRentalRate() );
					
				}
				
				
			}
			
			
		}
		
		
		
		
		
		
		return Inventory_cost;
		
	}
	
	
	
	public Map<Integer, Double> getrentalprice(ArrayList<rental> rentallist,Map<Integer,Double> Inventory_cost) {
		
		Map<Integer,Double>  Rental_price =new HashMap<Integer,Double>();
		
		
		
		for(rental i :rentallist) {
			
			
			
			for(Map.Entry<Integer, Double> j :Inventory_cost.entrySet()) {
				
				
				if(i.getInventory_id()==j.getKey()) {
					
					Rental_price.put(i.getRental_id(), j.getValue());
				}
				
			}
		}
		
		return Rental_price;
		
	}
	
	
	public Map<Integer, Double> getCustomerIDRevenue(ArrayList<rental> rentallist,Map<Integer, Double> PriceinEachrental) {
		
		
		Map<Integer,Double> Customer_ID_Revenue = new HashMap<Integer,Double>();
		
		
		for(rental i: rentallist) {
			
			
			for(Map.Entry<Integer, Double> j :PriceinEachrental.entrySet()) {
				
				if(i.getRental_id()==j.getKey()) {
					
					if(Customer_ID_Revenue.containsKey(i.getCustomer_id())) {
						
						
						double count =Customer_ID_Revenue.get(i.getCustomer_id());
						Customer_ID_Revenue.put(i.getCustomer_id(), count+j.getValue());
						
						
					}else {
						
						Customer_ID_Revenue.put(i.getCustomer_id(), j.getValue());
						
					}
					
					
				}
				
				
			}
			
			
		}
			
		
		
		return Customer_ID_Revenue;
		
	}
	
	
	
	
	
	public Map<customer, Double>   getcustomer_with_revenue(Map<Integer, Double> CustomerIDRevenue,ArrayList<customer> customerlist) {
		
		Map<customer, Double> customer_revenue = new HashMap<customer, Double> ();
		
		

		int count=0;
		for(customer i :customerlist) {
			
			for(Map.Entry<Integer, Double> j :CustomerIDRevenue.entrySet()) {
				
				if(i.getCustomer_id()==j.getKey()) {
					
					customer_revenue.put(i,j.getValue());
					
				}
				
				
			}
			
			
			
			
		}
		Map<customer, Double> SortHashMap =sortHashMapbyValue(customer_revenue );
		
		
		return SortHashMap  ;
		
		
		
		
	}
	
	
	public Map<customer, Double> sortHashMapbyValue(Map<customer, Double> customer_revenue) {
		
		HashMap sortedHashMap = new LinkedHashMap();
		

		 List list = new LinkedList(customer_revenue.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	                  .compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });
		
	       
	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
		
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
