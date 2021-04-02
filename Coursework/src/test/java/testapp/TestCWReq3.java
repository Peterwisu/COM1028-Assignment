package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.CWReq3;
import app.actor;
import app.customer;
import junit.framework.TestCase;

public class TestCWReq3 extends TestCase {
	
	private CWReq3 r;

	public TestCWReq3(String testName) throws FileNotFoundException {
		super(testName);
		r = new CWReq3("src/test/java/testapp/DBconfiguration1.json");
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
    	
    	ArrayList<customer> finalcustomerlist = new ArrayList<customer>();
    	ArrayList<Double> revenuelist =new ArrayList<Double>();
    	customer a;
    	
    	int customer_id;

    	String first_name;
    	String last_name;
    	int address_id;
    	Double revenue;
    	
    	ResultSet rs3= r.getResultSet("select customer.customer_id,customer.first_name,customer.last_name,customer.address_id,rev.revenue from customer inner join(\n"
    			+ "select customer_id,sum(rental_rate) as revenue from (select rental.rental_id,x.inventory_id,x.rental_rate,rental.customer_id from rental inner join (select rental_rate,inventory.inventory_id,film.film_id from film inner join inventory on film.film_id=inventory.film_id\n"
    			+ ")as x on rental.inventory_id =x.inventory_id)as z group by customer_id order by revenue desc limit 10 )as rev on customer.customer_id= rev.customer_id;\n"
    			+ "");
    	while(rs3.next()) {
    		
    		customer_id=rs3.getInt("customer_id");
    	 	
 			first_name=rs3.getString("first_name");
 			last_name=rs3.getNString("last_name");
 			address_id=rs3.getInt("address_id");
 			revenue=rs3.getDouble("revenue");
    		
 			
 			a = new  customer(customer_id,first_name,last_name,address_id);
    		finalcustomerlist.add(a);
    		revenuelist.add(revenue);
    	}
    	
    	
    	
    	
    	ArrayList<String> answer = new ArrayList<String>();
    	
    	
    	for(int i=0;i<finalcustomerlist.size();i++) {
    		
    		answer.add(finalcustomerlist.get(i).getCustomer_id()+" "+finalcustomerlist.get(i).getFirst_name()+" "+finalcustomerlist.get(i).getLast_name()+" "+revenuelist.get(i)+"\n");
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
    	System.out.print(getExpected());
    	r.printOutput();
    	ArrayList<String> actual = r.getActual();
    	ArrayList<String> expected = getExpected();
    	assertEquals(expected, actual);
    }
}
