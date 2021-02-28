package app;

import java.sql.Date;

public class actor {

	Integer actor_id;
	String first_name;
	String last_name;
	Date last_update;
	
	
	
	public actor(Integer i,String f,String l,Date lu) {
		super();
		this.actor_id =i;
		this.first_name=f;
		this.last_name=l;
		this.last_update=lu;
		
	}



	public Integer getActor_id() {
		return actor_id;
	}



	public String getFirst_name() {
		return first_name;
	}



	public String getLast_name() {
		return last_name;
	}



	public Date getLast_update() {
		return last_update;
	}
}
