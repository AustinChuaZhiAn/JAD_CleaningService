package Model;

public class Category {
	private String category_name;
	private String description;
	private int img_id;
	private String img_url;

	public Category(String category_name, String description, int img_id, String img_url) {
		super();
		this.category_name = category_name;
		this.description = description;
		this.img_id = img_id;
		this.img_url = img_url;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getImg_id() {
		return img_id;
	}
	public void setImg_id(int img_id) {
		this.img_id = img_id;
	}
	

}
