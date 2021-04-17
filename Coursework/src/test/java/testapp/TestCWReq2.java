package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import app.CWReq2;
import app.customer;
import junit.framework.TestCase;

public class TestCWReq2 extends TestCase {
	
	private CWReq2 r;

	public TestCWReq2(String testName) throws FileNotFoundException {
		super(testName);
		r = new CWReq2("src/test/java/testapp/DBconfiguration1.json");
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
    	
    	customer ans = null;
    	
    	int customer_id;
	
		String first_name;
		String last_name;
		
		int address_id;
    	
    	 ResultSet rs2 = r.getResultSet("select  * from customer where customer_id =(SELECT customer.customer_id  FROM customer inner join\n"
    	 		+ "\n"
    	 		+ "(SELECT customer_id,COUNT(*) as numberOfrental\n"
    	 		+ "FROM rental\n"
    	 		+ "GROUP BY customer_id ) as x on customer.customer_id = x.customer_id where x.numberOfrental = (select max(x.numberOfrental)  FROM customer inner join\n"
    	 		+ "\n"
    	 		+ "(SELECT customer_id,COUNT(*) as numberOfrental\n"
    	 		+ "FROM rental\n"
    	 		+ "GROUP BY customer_id ) as x on customer.customer_id = x.customer_id));");
    	 
    	 while(rs2.next()) {
    		 
    		customer_id=rs2.getInt("customer_id");
 	
 			first_name=rs2.getString("first_name");
 			last_name=rs2.getNString("last_name");
 		
 			address_id=rs2.getInt("address_id");
 		
 			ans = new  customer(customer_id,first_name,last_name,address_id);
    		 
    	 }
    	 
    		
 		StringBuffer A = new StringBuffer();
 		A.append(ans.getCustomer_id()+" "+ans.getFirst_name()+" "+ans.getLast_name());
 		
 		return A.toString();
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
