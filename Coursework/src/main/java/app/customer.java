package app;

import java.sql.Date;

public class customer {
	
	
	private int customer_id;

	private String first_name;
	private String last_name;

	private int address_id;

	
	public customer(int customer_id,String first_name,	String last_name,int address_id) {
		this.customer_id =customer_id;
	
		this.first_name =first_name;
		this.last_name =last_name;

		this.address_id=address_id;
	
	}

	public int getCustomer_id() {
		return customer_id;
	}

	

	public String getFirst_name() {
		return first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	

	public int getAddress_id() {
		return address_id;
	}

	
	

}
