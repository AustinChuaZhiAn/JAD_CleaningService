package Model;

public class AddressType {
	private int address_type_id;
	private String address_type;
	
	public AddressType(int address_type_id, String address_type) {
		this.address_type_id = address_type_id;
		this.address_type = address_type;
	}

	public int getAddress_type_id() {
		return address_type_id;
	}

	public void setAddress_type_id(int address_type_id) {
		this.address_type_id = address_type_id;
	}

	public String getAddress_type() {
		return address_type;
	}

	public void setAddress_type(String address_type) {
		this.address_type = address_type;
	}
}
