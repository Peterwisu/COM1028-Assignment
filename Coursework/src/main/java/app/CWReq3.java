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
/**
 * 
 * @author Wish Suharitdamrong
 *
 */
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
		
		
		//Call Function getInventory_cost() to retrieve a HashMap of inventory_id and its rental rate 
		Map<Integer, Double> PriceinEachInventory= getInventory_cost(getfilm(),getinventory());

		//Call Function getrentalprice() to retrieve a rental_id with its rental rate
		Map<Integer, Double> PriceinEachrental=getrentalprice(getrental(),PriceinEachInventory);
		
		//Call Function getCustomerIDRevenue() to get customer_ID and a total revenue of each customer_id
		Map<Integer, Double> CustomerIDRevenue=getCustomerIDRevenue(getrental(), PriceinEachrental) ;
		
		//Call Function getcustomer_with_revenue() to get a customer object with its total revenue
		Map<customer, Double>  customerRevenue=getcustomer_with_revenue(CustomerIDRevenue,getcustomer());
		
		
		DecimalFormat df = new DecimalFormat("#.##");

		//Create ArrayList for customer object and total revenue
		ArrayList<customer> finalcustomerlist = new ArrayList<customer>();
    	ArrayList<Double> revenuelist =new ArrayList<Double>();
		
		//initialise value of count by 0
		int count=0;
	
		//Iterate 10 times over HashMap  customerRevenue and assign its key and value in an Array 
		for(Entry<customer, Double> i:  customerRevenue.entrySet()) {
			//check whether count is equal to 10 
			if(count!=10) {
				
				
				finalcustomerlist.add(i.getKey());
				revenuelist.add(i.getValue());
				
			
			//increase value of count by 1
			count++;
			
			}else {
				//if count is equal to 10 exit the loop
				break;
			}
			
		}
		
		
		
		
		//Create ArrayList for an customer detail and its total revenue
    	ArrayList<String> answer = new ArrayList<String>();
    	
    	//iterate over finalcustomerlist
    	for(int i=0;i<finalcustomerlist.size();i++) {
    		
    		
    		//Check whether i index is not a second last index
    		if(i!=finalcustomerlist.size()-1) {
    			
    			//Check if value of revenuelist at index i is equal to value of next index and customer_id of  finalcustomerlist at index i is greater than customer_id in the next index ,This is for sorting a Customer_id when there two or more customer have same total revenue
    			if((df.format(revenuelist.get(i)).equals(df.format(revenuelist.get(i+1))))&&(finalcustomerlist.get(i).getCustomer_id()>finalcustomerlist.get(i+1).getCustomer_id())) {
    				
    				//Use  Collections.swap() to swap current index and next index
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
	
	
	
	/**Retrieve all Film from the database and create the Film objects
	 * 
	 * @return list of all film
	 * @throws SQLException
	 */
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
	
	
	/**Retrieve all Inventory from the database and create the Inventory objects
	 * 
	 * @return list of all Inventory
	 * 
	 * @throws SQLException
	 */
	public ArrayList<inventory> getinventory() throws SQLException {
		ArrayList<inventory> inventorylist = new ArrayList<inventory>();
		
		inventory a;
		int inventory_id;
		int film_id;
		
		
		//iterate over the ResultSet to create an ArrayList of Inventory objects
		ResultSet rs3inventory= this.getResultSet("select * from inventory;");
		
		while(rs3inventory.next()) {
			inventory_id=rs3inventory.getInt("inventory_id");
			film_id=rs3inventory.getInt("film_id");
			
			
			a= new inventory(inventory_id,film_id);
			inventorylist.add(a);
		}
		
		
		
		return inventorylist;
	}
	
	
	
	/**This Function is use to match a rental rate of each Inventory in a HashMap
	 * 
	 * @param filmlist
	 * @param inventorylist
	 * @return HashMap<Integer, Double> of Inventory_id and Rental Rate
	 */
	public Map<Integer, Double> getInventory_cost( ArrayList<Film> filmlist,ArrayList<inventory> inventorylist) {
		
		
		//Create a HashMap of <Interger,Double> for Inventory_id and  Rental Rate respectively
		Map<Integer,Double>  Inventory_cost =new HashMap<Integer,Double>();
		
		
		
			//Iterate loop on ArrayList filmlist in outer loop as j
			for(Film j:filmlist) {
				//Iterate loop on ArrayList inventorylist in inner loop as i
				for(inventory i:inventorylist) {
				
				//Check if film_id from i and j is equal
				if(j.getFilmID().equals(i.getFilm_id())) {
					
					//put Inventory_id from i as key and  Rental Rate from j as value  in an HashMap
					Inventory_cost.put(i.getInventory_id(),j.getRentalRate() );
					
				}
							
			}
			
		}
		
		return Inventory_cost;
		
	}
	
	
	/**This function is use to match a Rental_id with its Rental rate
	 * 
	 * @param rentallist
	 * @param Inventory_cost
	 * @return HashMap<Integer, Double> of Rental_id and Rental Rate
	 */
	public Map<Integer, Double> getrentalprice(ArrayList<rental> rentallist,Map<Integer,Double> Inventory_cost) {
		
		//Create a HashMap of <Integer,Double> for Rental_id and Rental Rate respectively
		Map<Integer,Double>  Rental_price =new HashMap<Integer,Double>();
		
		
		//iterate over a rentallist as i in outer loop
		for(rental i :rentallist) {
			//iterate over HashMap Inventory_cost using entrySet() as j in inner loop
			for(Map.Entry<Integer, Double> j :Inventory_cost.entrySet()) {
				
				//Check if Inventory_id of i is equal to key of HashMap in j which contains contains Inventory_if
				if(i.getInventory_id()==j.getKey()) {
					
					//Put a Rental_id of i in key and value of j which are rental rate in value of a HashMap respectively
					Rental_price.put(i.getRental_id(), j.getValue());
				}
				
			}
		}
		
		return Rental_price;
		
	}
	
	/**This function is used to find a total revenue of each customer_id and store it in HashMap
	 * 
	 * @param rentallist
	 * @param PriceinEachrental
	 * @return HashMap<Integer, Double> of customer_id and Revenue
	 */
	public Map<Integer, Double> getCustomerIDRevenue(ArrayList<rental> rentallist,Map<Integer, Double> PriceinEachrental) {
		
		//Create HashMap of <Integer,Double> for Customer_id and  Revenue respectively
		Map<Integer,Double> Customer_ID_Revenue = new HashMap<Integer,Double>();
		
		//iterate over rentallist as i in outer loop
		for(rental i: rentallist) {
			 
			//iterate over HashMap PriceinEachrental using entrySet() as j in inner loop
			for(Map.Entry<Integer, Double> j :PriceinEachrental.entrySet()) {
				
				//Check if  Rental_id from i is equal to key from HashMap j which contain Rental_id
				if(i.getRental_id()==j.getKey()) {
					
					
					//Check whether  HashMap Customer_ID_Revenue already contains current customer_id from i in an HashMap
					if(Customer_ID_Revenue.containsKey(i.getCustomer_id())) {
						
						//temporary store revenue in a value of HashMap Customer_ID_Revenue which have key equal to current Customer_id at i in count
						double count =Customer_ID_Revenue.get(i.getCustomer_id());
						//using put() to increase a value  of revenue in an HashMap Customer_ID_Revenue by adding count which temporary store previous value and new value 
						Customer_ID_Revenue.put(i.getCustomer_id(), count+j.getValue());
						
						
					}else {
						//use put() to add Customer_id and its Rental rate or revenue
						Customer_ID_Revenue.put(i.getCustomer_id(), j.getValue());
						
					}
					
				}
							
			}
					
		}
				
		return Customer_ID_Revenue;
		
	}
	
	
	
	
	/**This function is used to match an object of Customer and its total revenue and store it in HashMap
	 * 
	 * @param CustomerIDRevenue
	 * @param customerlist
	 * @return HashMap<Customer, Double> of customer and Revenue
	 */
	public Map<customer, Double>   getcustomer_with_revenue(Map<Integer, Double> CustomerIDRevenue,ArrayList<customer> customerlist) {
		
		//Create HashMap of <Customer,Double> for Customer and Revenue respectively
		Map<customer, Double> customer_revenue = new HashMap<customer, Double> ();
		
		//iterate over customerlist as i in outer loop
		for(customer i :customerlist) {
			
			//iterate over CustomerIDRevenue using entrySet() as j in inner loop
			for(Map.Entry<Integer, Double> j :CustomerIDRevenue.entrySet()) {
				
				
				//Check if customer_id of i is equal to customer_id at Key of HashMap j
				if(i.getCustomer_id()==j.getKey()) {
					//put customer object and its revenue respectively in a HashMap
					
					customer_revenue.put(i,j.getValue());
					
				}

			}

		}
		
		//use sortHashMapbyValue() to sort HashMap by its value in descending order
		Map<customer, Double> SortHashMap =sortHashMapbyValue(customer_revenue );
		
		
		return SortHashMap  ;
		
		
		
		
	}
	
	/**This function is used to sort HashMap by value in descending order
	 * 
	 * @param customer_revenue
	 * @return Sorted HashMap
	 * 
	 * 
	 * References :https://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
	 * Last Access[30/04/2021]
	 */
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
		ArrayList<String> answer = getActual();
		System.out.println(answer);
	}

}
