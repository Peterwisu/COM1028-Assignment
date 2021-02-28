package app;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import db.BaseQuery;

public class LabReq1 extends BaseQuery{

	public LabReq1(String configFilePath) throws FileNotFoundException {
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
		ArrayList<actor> actorlist = new ArrayList<actor>();
		HashMap<String,Integer> lastname =new HashMap<String,Integer>();
		
		ArrayList<String> result = new ArrayList<String>();
		
		
		actor a;

		Integer actor_id;
		String first_name;
		String last_name;
		Date last_update;
		
		
		ResultSet rs = this.getResultSet("Select * from actor");
		while(rs.next()) {
			actor_id=rs.getInt("actor_id");
			first_name=rs.getNString("first_name");
			last_name=rs.getNString("last_name");
			last_update=rs.getDate("last_update");
			a = new actor(actor_id,first_name,last_name,last_update);
			actorlist.add(a);
		}
		
		for(actor i:actorlist) {
			
			if(!(lastname.containsKey(i.getLast_name()))) {
				lastname.put(i.getLast_name(), 1);
			}else {
				int count = lastname.get(i.getLast_name());
				lastname.replace(i.getLast_name(), count+1);
			}
			
			
			
		}
		
		for(HashMap.Entry<String,Integer> i : lastname.entrySet()) {
			
			
			if(i.getValue()>=3) {
				result.add(i.getKey());
			}
			
		}
		
		Collections.sort(result);
		
		return result;
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
		ArrayList<String> print = getActual();
		
		for(String d: print) {
			System.out.println(d);
		}
	}

}
