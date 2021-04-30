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

import db.BaseQuery;
/**
 * 
 * @author Wish Suharitdamrong
 *
 */
public class CWReq4 extends BaseQuery{

	public CWReq4(String configFilePath) throws FileNotFoundException {
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
		Map<customer, Double> Customer_Revenue=getcustomer_with_revenue(CustomerIDRevenue,getcustomer());
		//Call Function getAddressidRevenue() to get a  Address_ID and its Revenue
		Map<Integer, Double> addressID_Revenue=getAddressidRevenue(Customer_Revenue,getaddress());
		//Call Function getcityidRevenue() to get a city_id with its  revenue
		Map<Integer, Double> cityID_Revenue =getcityidRevenue(addressID_Revenue,getaddress()) ;			
		//Call Function getcitywithRevenue() to get a city object with its total revenue
		Map<city, Double>  CityWithRevenue =getcitywithRevenue(cityID_Revenue,getcity()); 
				
		//Create ArrayList for an City detail and its total revenue
		ArrayList<String> actual = new ArrayList<String>();
		DecimalFormat df = new DecimalFormat("#.##");
		//initialise value of count by 0
		int count =0;
		//Iterate 10 times over HashMap  CityWithRevenue and assign its key and value in an Array 
		for(Map.Entry<city, Double> i :CityWithRevenue.entrySet()) {
			//check whether count is equal to 10 
			if(count!=10) {
			actual.add( i.getKey().getCity_id()+" "  +i.getKey().getCity()+ " "+ df.format(i.getValue())+"\n");
			//increase value of count by 1
			count++;
			}else {
				//if count is equal to 10 exit the loop
				break;
			}
		}
		
		return actual;
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
		ResultSet rs4customer = this.getResultSet("Select * from customer");
		while(rs4customer.next()) {
			
			customer_id=rs4customer.getInt("customer_id");
		
			first_name=rs4customer.getString("first_name");
			last_name=rs4customer.getNString("last_name");
			
			address_id=rs4customer.getInt("address_id");
			
			
			
			
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
		ResultSet rs4rental =this.getResultSet("Select * from rental limit 50000");
		
		while(rs4rental.next()) {
			
			
			rental_id=rs4rental.getInt("rental_id");
			inventory_id=rs4rental.getInt("inventory_id");
			customer_id=rs4rental.getInt("customer_id");
			
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
		
		ResultSet rs4film = this.getResultSet("Select * from film");
		
		//iterate over the ResultSet to create an ArrayList of Film objects
		while(rs4film.next()) {
			film_id = rs4film.getInt("film_id");
			title = rs4film.getString("title");
			description = rs4film.getString("description");
			rental_rate = rs4film.getDouble("rental_rate");
			f = new Film(film_id, title, description, rental_rate);
			films.add(f);
		}
		
		return films;
		
	}
	
	
	/**Retrieve all Inventory from the database and create the Inventory objects
	 * 
	 * @return List of Inventory
	 * @throws SQLException
	 */
	
	public ArrayList<inventory> getinventory() throws SQLException {
		ArrayList<inventory> inventorylist = new ArrayList<inventory>();
		
		inventory a;
		int inventory_id;
		int film_id;
		
		
		
		ResultSet rs4inventory= this.getResultSet("select * from inventory;");
		
		while(rs4inventory.next()) {
			inventory_id=rs4inventory.getInt("inventory_id");
			film_id=rs4inventory.getInt("film_id");
			
			
			a= new inventory(inventory_id,film_id);
			inventorylist.add(a);
		}
		
		
		
		return inventorylist;
	}
	
	
	/**Retrieve all Address from the database and create the Address objects
	 * 
	 * @return List of Address
	 * @throws SQLException
	 */
	public ArrayList<address> getaddress() throws SQLException {
		
		ArrayList<address> addresslist = new ArrayList<address>();
		
		address a;
		int address_id;
		int city_id;
		
		
		ResultSet rs4address =this.getResultSet("select * from address");
		
		while(rs4address.next()) {
			
			address_id=rs4address.getInt("address_id");
			city_id=rs4address.getInt("city_id");
			
			a= new address(address_id,city_id);
			addresslist.add(a);
		}
		
		return addresslist;
	}
	
	
	/**Retrieve all City from the database and create the City objects
	 * 
	 * @return List of City
	 * @throws SQLException
	 */
	public ArrayList<city> getcity() throws SQLException {
		
		ArrayList<city> citylist = new ArrayList<city>();
		city a;

		int city_id;
		String city;
			
		ResultSet rs4city = this.getResultSet("select * from city");
		
		while(rs4city.next()) {
			
			 city_id=rs4city.getInt("city_id");
			 city=rs4city.getString("city");
			 
			a = new city (city_id,city);
			citylist.add(a);
		}

		return citylist;	
	}
	
	
	/**This Function is use to match a rental rate of each Inventory in a HashMap
	 * 
	 * 
	 * @param filmlist
	 * @param inventorylist
	 * @return  Inventory_id and Rental Rate
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
	 * @return HashMap<Integer, Double> of customer and Revenue
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
		
		return customer_revenue ;
		
		
		
		
	}
	
	/**This Function is used to match a  address_id with its revenue
	 * 
	 * @param Customer_Revenue
	 * @param addresslist
	 * @return  HashMap<Integer, Double> of Address_ID and Revenue
	 */
	public HashMap<Integer, Double> getAddressidRevenue(Map<customer, Double> Customer_Revenue,ArrayList<address> addresslist) {
		
		//Create HashMap of <Integer,Double> for address_id and Revenue respectively
		HashMap<Integer,Double>  addressID_Revenue =new HashMap<Integer,Double>();
		
		//iterate over addresslist as i in outer loop
		for(address i : addresslist) {
			//iterate over Customer_Revenue using entrySet() as j in inner loop
			for(Map.Entry<customer, Double> j : Customer_Revenue.entrySet()) {
				
				//Check if address_id of i is equal to address_id at Key of HashMap j
				if(i.getAddress_id()==j.getKey().getAddress_id()) {
					//put address_id and its revenue respectively in a HashMap
					addressID_Revenue.put(i.getAddress_id(), j.getValue());
					
				}
				
			}
			
			
			
		}
		
		
		return addressID_Revenue;
		
	}
	
	
	/**This Function is used to match a  City_id with its revenue
	 * 
	 * @param addressID_Revenue
	 * @param addresslist
	 * @return HashMap<Integer, Double> of city_ID and Revenue
	 */
	public HashMap<Integer, Double> getcityidRevenue(Map<Integer, Double> addressID_Revenue,ArrayList<address> addresslist) {
		
		//Create HashMap of <Integer,Double> for city_id and Revenue respectively
		HashMap<Integer,Double> cityID_Revenue = new HashMap<Integer,Double>();
		
		//iterate over addresslist as i in outer loop
		for(address i :addresslist) {
			//iterate over addressID_Revenue using entrySet() as j in inner loop
			for(Map.Entry<Integer, Double>  j : addressID_Revenue.entrySet()) {
				//Check if address_id of i is equal to address_id at Key of HashMap j
				if(i.getAddress_id()==j.getKey()) {
					

					//Check whether  HashMap cityID_Revenue already contains current city_id from i in an HashMap
					if(cityID_Revenue.containsKey(i.getCity_id())) {

						//temporary store revenue in a value of HashMap cityRevenue which have key equal to current City at i in count
						double count= cityID_Revenue.get(i.getCity_id());
						//using put() to increase a value  of revenue in an HashMap cityID_Revenue by adding count which temporary store previous value and new value 
						cityID_Revenue.put(i.getCity_id(),count+ j.getValue());
						
					}else {
						//use put() to add City_id and revenue
						cityID_Revenue.put(i.getCity_id(),j.getValue());
					}
					
					
					
				}
			}
			
		}
		
		
		return cityID_Revenue;
		
	}
	
	/**This Function is to match a city object with its total revenue and store in HashMap
	 * 
	 * @param cityID_Revenue
	 * @param citylist
	 * @return HashMap<city,Double> of city object and its total revenue
	 */
	public HashMap<city, Double> getcitywithRevenue(	Map<Integer, Double> cityID_Revenue ,ArrayList<city> citylist) {
		
		//Create a HashMap of <city,Double> for City and Revenue
		HashMap<city,Double> City_Revenue = new HashMap<city,Double>();
		
		//iterate over citylist
		for(city i : citylist) {
			
			//iterate over HashMap using entrySet()
			for(Map.Entry<Integer, Double> j :cityID_Revenue.entrySet()) {
				
				//Check if city_id at i is equal to city_id at Key of HashMap j
				if(i.getCity_id()==j.getKey()) {
					
					//put city object and its revenue respectively in a HashMap
					City_Revenue.put(i,j.getValue());
				}
				
				
			}
			
		}
		
		//use sortHashMapbyValue() to sort HashMap by its value in descending order
		HashMap<city, Double> sortedHashMap =sortHashMapbyValue(City_Revenue);
		
		return sortedHashMap;
	}
	
	
	
	/**This function is used to sort HashMap by value in descending order
	 * 
	 * @param city_Revenue
	 * @return Sorted HashMap
	 * 
	 * 
	 * References :https://beginnersbook.com/2013/12/how-to-sort-hashmap-in-java-by-keys-and-values/
	 * Last Access[30/04/2021]
	 */
	public HashMap<city, Double> sortHashMapbyValue(HashMap<city, Double> city_Revenue) {
		
		HashMap sortedHashMap = new LinkedHashMap();
		

		 List list = new LinkedList(city_Revenue.entrySet());
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
