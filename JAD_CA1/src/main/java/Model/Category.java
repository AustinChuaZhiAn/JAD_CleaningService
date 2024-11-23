package Model;

public class Category {
    private int category_id;
    private String category_name;
    private String description;
    private int img_id;
    
    // Default constructor
    public Category() {}
    
    // Constructor
    public Category(int category_id, String category_name, String description, int img_id) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
        this.img_id = img_id;
    }
    
    // Getters and Setters
    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }
    
    public String getCategory_name() { return category_name; }
    public void setCategory_name(String category_name) { this.category_name = category_name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getImg_id() { return img_id; }
    public void setImg_id(int img_id) { this.img_id = img_id; }
}