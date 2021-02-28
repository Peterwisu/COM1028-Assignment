package app;

import java.sql.Date;

public class film_actor {
	
	Integer actor_id;
	Integer film_id;
	Date last_update;
	
	
	public film_actor(Integer actor_id,Integer film_id,Date last_update) {
		super();
		
		this.actor_id=actor_id;
		this.film_id=film_id;
		this.last_update=last_update;
		
	}


	public Integer getActor_id() {
		return actor_id;
	}


	public Integer getFilm_id() {
		return film_id;
	}


	public Date getLast_update() {
		return last_update;
	}
	

}
