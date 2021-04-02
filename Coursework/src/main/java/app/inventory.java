package app;

public class inventory {
	
	int inventory_id;
	int film_id;
	
	
	
	public inventory(int inventory_id,	int film_id) {
		
		this.inventory_id=inventory_id;
		this.film_id =film_id;

		
	}



	public int getInventory_id() {
		return inventory_id;
	}



	public int getFilm_id() {
		return film_id;
	}
	
	
	

}
