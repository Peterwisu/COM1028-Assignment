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

		
		Map<Integer, Double> PriceinEachInventory= getInventory_cost(getfilm(),getinventory());

		Map<Integer, Double> PriceinEachrental=getrentalprice(getrental(),PriceinEachInventory);
		
		Map<Integer, Double> CustomerIDRevenue=getCustomerIDRevenue(getrental(), PriceinEachrental) ;
		
		Map<customer, Double> Customer_Revenue=getcustomer_with_revenue(CustomerIDRevenue,getcustomer());
				
		Map<Integer, Double> addressID_Revenue=getAddressidRevenue(Customer_Revenue,getaddress());

		Map<Integer, Double> cityID_Revenue =getcityidRevenue(addressID_Revenue,getaddress()) ;
			
		
		Map<city, Double>  CityWithRevenue =getcitywithRevenue(cityID_Revenue,getcity()); 
				
		
		ArrayList<String> actual = new ArrayList();
		DecimalFormat df = new DecimalFormat("#.##");
		int count =0;
		for(Map.Entry<city, Double> i :CityWithRevenue.entrySet()) {
			if(count!=10) {
			actual.add( i.getKey().getCity_id()+" "  +i.getKey().getCity()+ " "+ df.format(i.getValue())+"\n");
			count++;
			}else {
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
		

		for(customer i :customerlist) {
			
			for(Map.Entry<Integer, Double> j :CustomerIDRevenue.entrySet()) {
				
				if(i.getCustomer_id()==j.getKey()) {
					
					customer_revenue.put(i,j.getValue());
					
				}
				
				
			}
			
			
			
			
		}
		
		
		
		return customer_revenue ;
		
		
		
		
	}
	
	public HashMap<Integer, Double> getAddressidRevenue(Map<customer, Double> Customer_Revenue,ArrayList<address> addresslist) {
		
		
		HashMap<Integer,Double>  addressID_Revenue =new HashMap<Integer,Double>();
		
		
		for(address i : addresslist) {
			
			for(Map.Entry<customer, Double> j : Customer_Revenue.entrySet()) {
				
				
				if(i.getAddress_id()==j.getKey().getAddress_id()) {
					
					addressID_Revenue.put(i.getAddress_id(), j.getValue());
					
				}
				
			}
			
			
			
		}
		
		
		return addressID_Revenue;
		
	}
	
	
	
	public HashMap<Integer, Double> getcityidRevenue(Map<Integer, Double> addressID_Revenue,ArrayList<address> addresslist) {
		
		
		HashMap<Integer,Double> cityID_Revenue = new HashMap<Integer,Double>();
		
		
		for(address i :addresslist) {
			
			for(Map.Entry<Integer, Double>  j : addressID_Revenue.entrySet()) {
				
				if(i.getAddress_id()==j.getKey()) {
					
					if(cityID_Revenue.containsKey(i.getCity_id())) {
						
						double count= cityID_Revenue.get(i.getCity_id());
						cityID_Revenue.put(i.getCity_id(),count+ j.getValue());
						
					}else {
						cityID_Revenue.put(i.getCity_id(),j.getValue());
					}
					
					
					
				}
			}
			
		}
		
		
		return cityID_Revenue;
		
	}
	
	
	public HashMap<city, Double> getcitywithRevenue(	Map<Integer, Double> cityID_Revenue ,ArrayList<city> citylist) {
		
		
		HashMap<city,Double> City_Revenue = new HashMap<city,Double>();
		
		
		for(city i : citylist) {
			
			for(Map.Entry<Integer, Double> j :cityID_Revenue.entrySet()) {
				
				if(i.getCity_id()==j.getKey()) {
					
					
					City_Revenue.put(i,j.getValue());
				}
				
				
			}
			
		}
		
		
		HashMap<city, Double> sortedHashMap =sortHashMapbyValue(City_Revenue);
		
		return sortedHashMap;
	}
	
	
	

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
