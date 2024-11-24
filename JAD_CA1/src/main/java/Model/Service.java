package Model;

public class Service {
    private int service_id;
    private int category_id;
    private int service_type_id;
    private int frequency_id;
    private String price;
    
    // Display fields for frontend
    private String category_name;
    private String service_type;
    private String frequency;
    private String status;  
    

    public Service() {}
    
    public Service(int category_id, int service_type_id, int frequency_id, String price) {
        this.category_id = category_id;
        this.service_type_id = service_type_id;
        this.frequency_id = frequency_id;
        this.price = price;
    }
    
    // Full constructor with display fields
    public Service(int service_id, int category_id, int service_type_id, int frequency_id, 
                  String price, String category_name, String service_type, String frequency, String status) {
        this.service_id = service_id;
        this.category_id = category_id;
        this.service_type_id = service_type_id;
        this.frequency_id = frequency_id;
        this.price = price;
        this.category_name = category_name;
        this.service_type = service_type;
        this.frequency = frequency;
        this.status = status;
    }

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getService_type_id() {
		return service_type_id;
	}

	public void setService_type_id(int service_type_id) {
		this.service_type_id = service_type_id;
	}

	public int getFrequency_id() {
		return frequency_id;
	}

	public void setFrequency_id(int frequency_id) {
		this.frequency_id = frequency_id;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategory_name() {
		return category_name;
	}

	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
