package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.CWReq4;
import app.city;
import app.customer;
import junit.framework.TestCase;

public class TestCWReq4 extends TestCase {
	
	private CWReq4 r;

	public TestCWReq4(String testName) throws FileNotFoundException {
		super(testName);
		r = new CWReq4("src/test/java/testapp/DBconfiguration1.json");
	}
	
    @Override
    protected void setUp() throws Exception {
    	System.out.println("\n\n\n\n");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println("Running tests in " + this.getClass().getName() + "...");
    	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++\n\n");
    	r.openconn();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	r.closeconn();
    	System.out.println("Finished tests in " + this.getClass().getName());
    	System.out.println("-------------------------------------------------------\n\n");
    }
    
	/* -------------------------------------------------------------
	 * TODO: getExpected() to be completed as part of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The getExpected() method returns yours requirements's expected results.
	 * These results can be obtained by writing complex SQL queries 
	 * and using the ResultSet API to obtain the expected results. Unlike 
	 * the requirement code, where you talk to the database multiple times 
	 * to get tables into memory and compute over them, the expected results 
	 * can be obtained by running SQL queries which have advanced SQL 
	 * primitives in it such as SUM, COUNT, ORDER BY, GROUP BY, etc.
	 * In this instance, the return type is a String, you are free to choose
	 * other return types depending on the requirement. You are allowed to 
	 * write additional helper methods.
	 * ---------------------------------------------------------------------
	 */
    
    private String getExpected() throws SQLException {
    	
    	int city_id;
    	String city;

    	city a;
    	
    	ArrayList<city> finalcitylist = new ArrayList<city>();
    	
    	ResultSet rs4 = r.getResultSet("select  city.city,city.city_id,x.total_revenue from city inner join ( select  x.city_id,sum(revenue) as total_revenue from ( select z.inventory_id,z.film_id,z.rental_id,z.customer_id,z.title,z.revenue,customer.address_id,address.city_id from ( select x.inventory_id,x.film_id,x.rental_id,x.customer_id,z.title,z.revenue from (\n"
    			+ "select inventory.inventory_id,film_id,rental.rental_id,rental.customer_id\n"
    			+ "from inventory INNER JOIN rental \n"
    			+ " on inventory.inventory_id = rental.inventory_id) as x inner join(select  film.film_id,film.title,rentalcount*rental_rate as revenue from film inner join (select film_id,count(*) as rentalcount \n"
    			+ "from inventory INNER JOIN rental \n"
    			+ " on inventory.inventory_id = rental.inventory_id\n"
    			+ " group by film_id   order by film_id ) as counted on film.film_id =counted.film_id ) as z on x.film_id = z.film_id)as z inner join customer on z.customer_id = customer.customer_id  inner join address on address.address_id = customer.address_id )as x group by city_id \n"
    			+ " )as x on city.city_id =x.city_id order by total_revenue desc limit 10;");
    	
    	while(rs4.next()) {
    		
    		city_id= rs4.getInt("city_id");
    		city=rs4.getString("city");
    		
    		a = new city(city_id,city);
    		finalcitylist.add(a);
    	}
    	
    	
    	return null;
    }
	
	/* -------------------------------------------------------------
	 * TODO: testAndOutput() to be modified, if required, as part 
	 * of the coursework.
	 * --------------------------------------------------------------
	 */
	/* ---------------------------------------------------------------------
	 * The testAndOutput() method does two things:
	 *    1. Invokes the method which prints output from the requirement 
	 *    	 code on to the console in a human-friendly format.
	 *    2. Compares the expected result with the actual result.
	 *    
	 * This method may need to be modified if you are using a type which 
	 * is other than a string for returning your expected and actual results.
	 * ---------------------------------------------------------------------
	 */
    /**
     *  Output Results and Test Sample Requirement
     * @throws FileNotFoundException 
     * @throws SQLException 
     */
    
    public void testAndOutput() throws FileNotFoundException, SQLException
    {
    	r.printOutput();
    	String actual = r.getActual();
    	String expected = getExpected();
    	assertEquals(expected, actual);
    }
}
