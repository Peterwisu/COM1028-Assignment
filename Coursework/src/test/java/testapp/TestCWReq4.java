package testapp;

import java.io.FileNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import app.CWReq4;
import app.city;
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
    
    private ArrayList<String> getExpected() throws SQLException {
    	
    	int city_id;
    	String city;
    	city a;
    	Double revenue;
    	ArrayList<city> finalcitylist = new ArrayList<city>();
    	ArrayList<Double> revenuelist =new ArrayList<Double>();
    	
    	ResultSet rs4 = r.getResultSet("select city.city_id,city.city,sum(revenue) as revenue from city inner join address on city.city_id =address.city_id inner join \n"
    			+ "(select customer.customer_id,customer.first_name,customer.last_name,customer.address_id,rev.revenue from customer inner join\n"
    			+ "(select customer_id,sum(rental_rate) as revenue from (select rental.rental_id,x.inventory_id,x.rental_rate,rental.customer_id from rental inner join (select rental_rate,inventory.inventory_id,film.film_id from film inner join inventory on film.film_id=inventory.film_id\n"
    			+ ")as x on rental.inventory_id =x.inventory_id)as z group by customer_id order by revenue  )as rev on customer.customer_id= rev.customer_id\n"
    			+ ")as x on x.address_id = address.address_id group by city.city_id order by revenue desc limit 10;");
    	
    	while(rs4.next()) {
    		
    		city_id= rs4.getInt("city_id");
    		city=rs4.getString("city");
    		revenue=rs4.getDouble("revenue");
    		a = new city(city_id,city);
    		finalcitylist.add(a);
    		revenuelist.add(revenue);
    	}

    	ArrayList<String> answer = new ArrayList<String>();
    	
    	for(int i=0;i<finalcitylist.size();i++) {
    		
    		answer.add(finalcitylist.get(i).getCity_id()+" "+finalcitylist.get(i).getCity()+" "+revenuelist.get(i)+"\n");
    		
    	}
    	
    	return answer;
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
    	ArrayList<String> actual = r.getActual();
    	ArrayList<String> expected = getExpected();
    	assertEquals(expected, actual);
    }
}
