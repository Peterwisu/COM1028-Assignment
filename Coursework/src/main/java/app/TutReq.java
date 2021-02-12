package app;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.BaseQuery;

public class TutReq extends BaseQuery{

	public TutReq(String configFilePath) throws FileNotFoundException {
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
	
	/**public String getActual() {
		unimplementedMessage();
		return null;
	}
	*/

	public ArrayList<Film> getActual() throws SQLException {
		ArrayList<Film> films = new ArrayList<Film>();
		ArrayList<Film> expensiveBoringFilms = new ArrayList<Film>();
		
		Film f;
		
		Integer film_id;
		String title;
		String description;
		Double rental_rate;
		
		ResultSet rs = this.getResultSet("Select * from film");
		
		//iterate over the ResultSet to create an ArrayList of Film objects
		while(rs.next()) {
			film_id = rs.getInt("film_id");
			title = rs.getString("title");
			description = rs.getString("description");
			rental_rate = rs.getDouble("rental_rate");
			f = new Film(film_id, title, description, rental_rate);
			films.add(f);
		}
		
		for(Film x: films) {
			if(x.getRentalRate() == 4.99 
					&& x.getDescription().contains("Boring")) {
				expensiveBoringFilms.add(x);
			}
		}
		
		return expensiveBoringFilms;
	
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
		ArrayList<Film> expensiveBoringFilms = getActual();
		for(Film f:expensiveBoringFilms) {
			System.out.println(f.getFilmID() + " " + f.getTitle() + " " + f.getRentalRate() + " " + f.getDescription());
		}
	}

}
