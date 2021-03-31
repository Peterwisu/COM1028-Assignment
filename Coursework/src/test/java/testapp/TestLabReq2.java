package testapp;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.Film;
import app.LabReq2;
import app.actor;
import junit.framework.TestCase;

public class TestLabReq2 extends TestCase {
	
	private LabReq2 r;

	public TestLabReq2(String testName) throws FileNotFoundException {
		super(testName);
		r = new LabReq2("src/test/java/testapp/DBconfiguration1.json");
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
    
    private ArrayList<actor> getExpected() throws SQLException {
    	
    	ArrayList<actor> actorlist = new ArrayList<actor>();
    	actor a;
    	

		Integer actor_id;
		String first_name;
		String last_name;
		Date last_update;
    	ResultSet rs = r.getResultSet("select * from actor where actor_id in (select actor_id from film_actor where film_id in (select film_id from film where title = 'Karate Moon'))");
    	while(rs.next()) {
    		actor_id=rs.getInt("actor_id");
			first_name=rs.getNString("first_name");
			last_name=rs.getNString("last_name");
			last_update=rs.getDate("last_update");
			a = new actor(actor_id,first_name,last_name,last_update);
			actorlist.add(a);
    	}
    	
    	StringBuffer display = new StringBuffer();
    	
    	for(actor d: actorlist) {
    		display.append(d.getActor_id()+" "+d.getFirst_name()+" "+d.getLast_name()+" "+d.getLast_update()+"\n");
    	}
    	
    	return actorlist;
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
    	List<actor> actual = r.getActual();
    	List<actor> expected = getExpected();
    	assertEquals(expected, actual);
    }
    
    
}
