package app;

public class Film {
	private Integer film_id;
	private String title;
	private String description;
	private Double rental_rate;
	
	public Film(Integer id, String t, String d, Double r) {
		this.film_id = id;
		this.title = t;
		this.description = d;
		this.rental_rate = r;
	}
	
	public boolean descContains(String s) {
		if(this.description.contains(s)) return true;
		return false;
	}
	
	public Integer getFilmID() {
		return this.film_id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Double getRentalRate() {
		return this.rental_rate;
	}
	
}