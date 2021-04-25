package app;
/**
 * 
 * @author Wish Suharitdamrong
 *
 */
public class address {

	
	 private int address_id;
	 private int city_id;
	 
	 
	 
	 public address(int address_id,		 int city_id) {
		 
		 this.address_id=address_id;
		 
		 this.city_id=city_id;
	 }



	public int getAddress_id() {
		return address_id;
	}


	public int getCity_id() {
		return city_id;
	}
	 
}
