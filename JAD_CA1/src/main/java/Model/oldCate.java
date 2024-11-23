package Model;

public class Category {
    private int category_id;
    private String category_name;
    private String description;
    private Integer img_id;    // Use Integer not int
    private String img_url;
    
    // Default constructor
    public Category() {}
    
    // Full constructor
    public Category(int category_id, String category_name, String description, Integer img_id, String img_url) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.description = description;
        this.img_id = img_id;
        this.img_url = img_url;
    }
    
    // Getters and Setters
    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }
    
    public String getCategory_name() { return category_name; }
    public void setCategory_name(String category_name) { this.category_name = category_name; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Integer getImg_id() { return img_id; }
    public void setImg_id(Integer img_id) { this.img_id = img_id; }
    
    public String getImg_url() { return img_url; }
    public void setImg_url(String img_url) { this.img_url = img_url; }
    
    @Override
    public String toString() {
        return "Category{" +
                "category_id=" + category_id +
                ", category_name='" + category_name + '\'' +
                ", description='" + description + '\'' +
                ", img_id=" + img_id +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}