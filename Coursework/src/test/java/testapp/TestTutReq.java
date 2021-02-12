package testapp;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.Film;
import app.TutReq;
import junit.framework.TestCase;

public class TestTutReq extends TestCase {
	
	private TutReq r;

	public TestTutReq(String testName) throws FileNotFoundException {
		super(testName);
		r = new TutReq("src/test/java/testapp/DBconfiguration1.json");
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
    
    private ArrayList<Integer> getExpected() throws SQLException {
    	ArrayList<Integer> boringButExpensiveFilmIDs = new ArrayList<Integer>();
    	Integer filmID;
    	
    	ResultSet rs = r.getResultSet("select film_id from film where description"
    			+ " like \'%Boring%\' and rental_rate = 4.99");
    	while(rs.next()) {
    		filmID = rs.getInt("film_id");
    		boringButExpensiveFilmIDs.add(filmID);
    	}
    	
    	return boringButExpensiveFilmIDs;
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
    	ArrayList<Integer> actualIDs = new ArrayList<Integer>();
    	ArrayList<Integer> expectedIDs = getExpected();
    	for(Film f:r.getActual()) {
    		actualIDs.add(f.getFilmID());
    	}
    	assertEquals(expectedIDs, actualIDs);
    }
}
