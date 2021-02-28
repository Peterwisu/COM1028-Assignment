package app;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.BaseQuery;

public class LabReq2 extends BaseQuery{
	
	
	
	
	

	public LabReq2(String configFilePath) throws FileNotFoundException {
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
	
	public Integer getfilmID(String name,ArrayList<Film> film) {
		Integer film_id=0;
		for(Film i: film) {
			
			if(i.getTitle().equals(name)) {
				film_id=i.getFilmID();
				
				
			}
			
		}
		
		
		
		return film_id;
	}
	
	
	public ArrayList<Integer> getactorID(Integer film_id,ArrayList<film_actor> list) {
		ArrayList<Integer> actor_id = new ArrayList<Integer>();
		
		for(film_actor i:list) {
			
			if(i.getFilm_id().equals(film_id)) {
				
				
				actor_id.add(i.getActor_id());
				
				
				
			}
		}
		
		return actor_id;
		
	}
	
	public String getActual() throws SQLException {
		ArrayList<actor> actorlist = new ArrayList<actor>();
		ArrayList<film_actor> film_actorlist =new ArrayList<film_actor>();
		ArrayList<actor> actorkarate = new ArrayList<actor>();
		ArrayList<Film> films = new ArrayList<Film>();
		actor a;

		Integer actor_id;
		String first_name;
		String last_name;
		Date last_update;
		
		
		film_actor b;
		
		Integer actor_id2;
		Integer film_id2;
		Date last_update2;
		
		
		Film f;
		
		Integer film_id;
		String title;
		String description;
		Double rental_rate;
		
		
		ResultSet rs = this.getResultSet("Select * from actor");
		while(rs.next()) {
			actor_id=rs.getInt("actor_id");
			first_name=rs.getNString("first_name");
			last_name=rs.getNString("last_name");
			last_update=rs.getDate("last_update");
			a = new actor(actor_id,first_name,last_name,last_update);
			actorlist.add(a);
		}
		
		
		ResultSet rs2 = this.getResultSet("Select * from film_actor");
		while(rs2.next()) {
			actor_id2=rs2.getInt("actor_id");
			film_id2=rs2.getInt("film_id");
			last_update2=rs2.getDate("last_update");
			b =new film_actor(actor_id2,film_id2,last_update2);
			film_actorlist.add(b);
		}
		
		ResultSet rs3 = this.getResultSet("Select * from film");
		
		//iterate over the ResultSet to create an ArrayList of Film objects
		while(rs3.next()) {
			film_id = rs3.getInt("film_id");
			title = rs3.getString("title");
			description = rs3.getString("description");
			rental_rate = rs3.getDouble("rental_rate");
			f = new Film(film_id, title, description, rental_rate);
			films.add(f);
		}
		
		
		
		Integer filmID=getfilmID("KARATE MOON",films );
		
		ArrayList<Integer> actorID =getactorID(filmID,film_actorlist);
		
		for(actor i:actorlist) {
			
			
			for(Integer j: actorID) {
				
				if(i.getActor_id().equals(j)) {
					
					actorkarate.add(i);
					
					
				}
				
			}
			
			
		}
		
		StringBuffer display = new StringBuffer();
    	
    	for(actor d: actorkarate) {
    		display.append(d.getActor_id()+" "+d.getFirst_name()+" "+d.getLast_name()+" "+d.getLast_update()+"\n");
    	}
    	
    	return display.toString();
		
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
		String karate = getActual();
		System.out.println(karate);
	}
	
	

}
