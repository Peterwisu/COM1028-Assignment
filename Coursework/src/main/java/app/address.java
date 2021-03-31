package app;

public class address {

	
	 int address_id;
	 String address;
	 String district;
	 int city_id;
	 
	 
	 
	 public address(int address_id,	String address,	 String district,	 int city_id) {
		 
		 this.address_id=address_id;
		 this.address=address;
		 this.district=district;
		 this.city_id=city_id;
	 }



	public int getAddress_id() {
		return address_id;
	}



	public String getAddress() {
		return address;
	}



	public String getDistrict() {
		return district;
	}



	public int getCity_id() {
		return city_id;
	}
	 
}
