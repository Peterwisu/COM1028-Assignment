package app;

import java.sql.Date;

public class rental {
	
	private int rental_id;
	private Date rental_date;
	private int inventory_id;
	private int customer_id;
	private Date return_Date;
	private int staff_id;
	
	public rental( int rental_id,Date rental_date,int inventory_id,int customer_id,Date return_Date,int staff_id) {
		
		this.rental_id =rental_id;
		this.rental_date=rental_date;
		this.inventory_id=inventory_id;
		this.customer_id=customer_id;
		this.return_Date =return_Date;
		this.staff_id=staff_id;
		
		
	}

	public int getRental_id() {
		return rental_id;
	}

	public Date getRental_date() {
		return rental_date;
	}

	public int getInventory_id() {
		return inventory_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public Date getReturn_Date() {
		return return_Date;
	}

	public int getStaff_id() {
		return staff_id;
	}

}
