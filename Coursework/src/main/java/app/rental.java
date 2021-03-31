package app;

import java.sql.Date;

public class rental {
	
	private int rental_id;
	private int inventory_id;
	private int customer_id;


	
	public rental( int rental_id,int inventory_id,int customer_id) {
		
		this.rental_id =rental_id;	
		this.inventory_id=inventory_id;
		this.customer_id=customer_id;
		
		
	}

	public int getRental_id() {
		return rental_id;
	}



	public int getInventory_id() {
		return inventory_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}


}
