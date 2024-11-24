package Controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.net.URLEncoder;
import Model.*;
import utils.ImgurUtil;
import utils.ImgurUtil.ImgurResponse;
import java.util.List;



@WebServlet("/CategoryController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1 MB
    maxFileSize = 1024 * 1024 * 10,     // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 11L;
    private CategoryDAO categoryDAO;
    private ImageDAO imageDAO;

    public CategoryController() {
        super();
        categoryDAO = new CategoryDAOImpl();
        imageDAO = new ImageDAOImpl();
    }


    public void init() {
        categoryDAO = new CategoryDAOImpl();
        imageDAO = new ImageDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action) {
                case "list":
                    listCategories(request, response);
                    break;
                case "create":
                    showCreateForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteCategory(request, response);
                    break;
                default:
                	List<Category> categories = categoryDAO.getAllCategory();
                    request.setAttribute("Categories", categories);
                    request.getRequestDispatcher("/View/ServiceCategories.jsp").forward(request, response);
                    break;
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "create":
                    createCategory(request, response);
                    break;
                case "edit":
                    updateCategory(request, response);
                    break;
                default:
                    listCategories(request, response);
                    break;
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void createCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String categoryName = request.getParameter("category_name");
            String description = request.getParameter("description");
            
            // Validate inputs
            if (!validateInputs(categoryName, description)) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Name and description are required", "UTF-8"));
                return;
            }

            Part filePart = request.getPart("image");
            if (filePart == null || filePart.getSize() == 0) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Image is required", "UTF-8"));
                return;
            }

            // First upload to Imgur
            ImgurResponse imgurResponse = null;
            int imageId = 0;
            try {
                imgurResponse = ImgurUtil.uploadImage(filePart);
                System.out.println("Image uploaded to Imgur: " + imgurResponse.getUrl());
                System.out.println(imgurResponse);
                System.out.print("DELETED HASH" + imgurResponse.getDeleteHash());
                
                // Insert into image table
                imageId = imageDAO.insertImage(imgurResponse.getUrl(), imgurResponse.getDeleteHash());
                System.out.println("Image record created with ID: " + imageId);
            } catch (Exception e) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to upload image: " + e.getMessage(), "UTF-8"));
                return;
            }

            // Create category with image ID
            Category category = new Category();
            category.setCategory_name(categoryName);
            category.setDescription(description);
            category.setImg_id(imageId);

            try {
                boolean success = categoryDAO.createCategory(category);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
                } else {
                    // If category creation fails, cleanup image
                    try {
                        ImgurUtil.deleteImage(imgurResponse.getDeleteHash());
                        imageDAO.deleteImage(imageId);
                    } catch (Exception e) {
                        System.err.println("Cleanup failed: " + e.getMessage());
                    }
                    response.sendRedirect(request.getContextPath() + 
                        "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to create category", "UTF-8"));
                }
            } catch (Exception e) {
                // Cleanup on error
                try {
                    ImgurUtil.deleteImage(imgurResponse.getDeleteHash());
                    imageDAO.deleteImage(imageId);
                } catch (Exception cleanupError) {
                    System.err.println("Cleanup failed: " + cleanupError.getMessage());
                }
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            String categoryName = request.getParameter("category_name");
            String description = request.getParameter("description");
            
            Category currentCategory = categoryDAO.getCategoryById(categoryId);
            if (currentCategory == null) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
                return;
            }

            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                try {
                    // Upload new image first
                    ImgurResponse imgurResponse = ImgurUtil.uploadImage(filePart);
                    
                    // Create new image record
                    int newImageId = imageDAO.insertImage(imgurResponse.getUrl(), imgurResponse.getDeleteHash());
                    
                    // Store old image info for cleanup
                    int oldImageId = currentCategory.getImg_id();
                    Image oldImage = imageDAO.getImageById(oldImageId);
                    
                    // Update category with new image ID
                    currentCategory.setImg_id(newImageId);
                    
                    // Update other fields
                    currentCategory.setCategory_name(categoryName);
                    currentCategory.setDescription(description);
                    
                    boolean success = categoryDAO.updateCategory(currentCategory);
                    if (success) {
                        // Cleanup old image after successful update
                        if (oldImage != null) {
                            try {
                                ImgurUtil.deleteImage(oldImage.getDeletehash());
                                imageDAO.deleteImage(oldImageId);
                            } catch (Exception e) {
                                System.err.println("Failed to cleanup old image: " + e.getMessage());
                            }
                        }
                        response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
                    } else {
                        // Cleanup new image if update fails
                        try {
                            ImgurUtil.deleteImage(imgurResponse.getDeleteHash());
                            imageDAO.deleteImage(newImageId);
                        } catch (Exception e) {
                            System.err.println("Failed to cleanup new image: " + e.getMessage());
                        }
                        response.sendRedirect(request.getContextPath() + 
                            "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to update category", "UTF-8"));
                    }
                } catch (Exception e) {
                    response.sendRedirect(request.getContextPath() + 
                        "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to process image: " + e.getMessage(), "UTF-8"));
                }
            } else {
                // No new image, just update category info
                currentCategory.setCategory_name(categoryName);
                currentCategory.setDescription(description);
                
                boolean success = categoryDAO.updateCategory(currentCategory);
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
                } else {
                    response.sendRedirect(request.getContextPath() + 
                        "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to update category", "UTF-8"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            
            if (category == null) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
                return;
            }

            // Get image details before deletion
            Image image = imageDAO.getImageById(category.getImg_id());
            
            // Delete category first (due to foreign key constraint)
            boolean success = categoryDAO.deleteCategory(categoryId);
            if (success) {
                // Then cleanup image
                if (image != null) {
                    try {
                        ImgurUtil.deleteImage(image.getDeletehash());
                        imageDAO.deleteImage(image.getImg_id());
                    } catch (Exception e) {
                        System.err.println("Failed to cleanup image: " + e.getMessage());
                    }
                }
                response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to delete category", "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + 
                "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8"));
        }
    }

    private void listCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ArrayList<Category> categories = categoryDAO.getAllCategory();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/View/admin/Categories.jsp").forward(request, response);
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("category", new Category());
        request.getRequestDispatcher("/View/admin/CategoryForm.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int categoryId = Integer.parseInt(request.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            
            if (category != null) {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/View/admin/CategoryForm.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
            }
        } catch (SQLException e) {
            handleError(request, response, e);
        }
    }

    private void handleError(HttpServletRequest request, HttpServletResponse response, Exception e) 
            throws ServletException, IOException {
        e.printStackTrace();
        response.sendRedirect(request.getContextPath() + 
            "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Error: " + e.getMessage(), "UTF-8"));
    }

    private boolean validateInputs(String name, String description) {
        return name != null && !name.trim().isEmpty() && 
               description != null && !description.trim().isEmpty();
    }
}