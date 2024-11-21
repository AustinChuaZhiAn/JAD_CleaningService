package Model;

public class ServiceType {
	private int service_type_id = 0;
	private String service_type = "";
	
	public ServiceType(int service_type_id, String service_type) {
		this.service_type_id = service_type_id;
		this.service_type = service_type;
	}

	public int getService_type_id() {
		return service_type_id;
	}

	public void setService_type_id(int service_type_id) {
		this.service_type_id = service_type_id;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	
}
