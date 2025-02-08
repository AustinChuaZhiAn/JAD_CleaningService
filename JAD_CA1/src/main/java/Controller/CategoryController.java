package Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.*;
import java.io.IOException;
import java.util.*;

import java.net.URLEncoder;
import java.util.ResourceBundle;
import Model.*;
import utils.ImgurUtil;
import utils.ImgurUtil.ImgurResponse;


@WebServlet("/CategoryController")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,    // 1 MB
    maxFileSize = 1024 * 1024 * 10,     // 10 MB
    maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 11L;
    private final Client client;
    private final String baseUrl;
    private ImageDAO imageDAO;

    public CategoryController() {
        super();
        client = ClientBuilder.newClient();
        baseUrl = "http://localhost:3000/api/categories";
        imageDAO = new ImageDAOImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.print(action);
        try {
            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "view":
                    WebTarget target = client.target(baseUrl);
                    Category[] categories = target.request(MediaType.APPLICATION_JSON)
                                               .get(Category[].class);
                    request.setAttribute("Categories", Arrays.asList(categories));
                    request.getRequestDispatcher("/View/ServiceCategories.jsp").forward(request, response);
                    break;
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
                    listCategories(request, response);
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

            ImgurResponse imgurResponse = ImgurUtil.uploadImage(filePart);
            int imageId = imageDAO.insertImage(imgurResponse.getUrl(), imgurResponse.getDeleteHash());

            Category category = new Category();
            category.setCategory_name(categoryName);
            category.setDescription(description);
            category.setImg_id(imageId);

            WebTarget target = client.target(baseUrl);
            Response resp = target.request(MediaType.APPLICATION_JSON)
                                .post(Entity.entity(category, MediaType.APPLICATION_JSON));

            if (resp.getStatus() == Response.Status.CREATED.getStatusCode()) {
                response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
            } else {
                cleanup(imgurResponse.getDeleteHash(), imageId);
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to create category", "UTF-8"));
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
            
            WebTarget target = client.target(baseUrl + "/" + categoryId);
            Category currentCategory = target.request(MediaType.APPLICATION_JSON)
                                          .get(Category.class);
            
            if (currentCategory == null) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
                return;
            }

            Part filePart = request.getPart("image");
            if (filePart != null && filePart.getSize() > 0) {
                ImgurResponse imgurResponse = ImgurUtil.uploadImage(filePart);
                int newImageId = imageDAO.insertImage(imgurResponse.getUrl(), imgurResponse.getDeleteHash());
                
                int oldImageId = currentCategory.getImg_id();
                Image oldImage = imageDAO.getImageById(oldImageId);
                
                currentCategory.setImg_id(newImageId);
                currentCategory.setCategory_name(categoryName);
                currentCategory.setDescription(description);
                
                Response resp = target.request(MediaType.APPLICATION_JSON)
                                    .put(Entity.entity(currentCategory, MediaType.APPLICATION_JSON));
                
                if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                    if (oldImage != null) {
                        cleanup(oldImage.getDeletehash(), oldImageId);
                    }
                    response.sendRedirect(request.getContextPath() + "/CategoryController?action=list");
                } else {
                    cleanup(imgurResponse.getDeleteHash(), newImageId);
                    response.sendRedirect(request.getContextPath() + 
                        "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Failed to update category", "UTF-8"));
                }
            } else {
                currentCategory.setCategory_name(categoryName);
                currentCategory.setDescription(description);
                
                Response resp = target.request(MediaType.APPLICATION_JSON)
                                    .put(Entity.entity(currentCategory, MediaType.APPLICATION_JSON));
                
                if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
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
            WebTarget target = client.target(baseUrl + "/" + categoryId);
            Category category = target.request(MediaType.APPLICATION_JSON)
                                   .get(Category.class);
            
            if (category == null) {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
                return;
            }

            Image image = imageDAO.getImageById(category.getImg_id());
            Response resp = target.request().delete();
            
            if (resp.getStatus() == Response.Status.OK.getStatusCode()) {
                if (image != null) {
                    cleanup(image.getDeletehash(), image.getImg_id());
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
        WebTarget target = client.target(baseUrl);
        Category[] categories = target.request(MediaType.APPLICATION_JSON)
                                   .get(Category[].class);
        request.setAttribute("categories", Arrays.asList(categories));
        request.getRequestDispatcher("/View/admin/Categories.jsp").forward(request, response);
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
            WebTarget target = client.target(baseUrl + "/" + categoryId);
            Category category = target.request(MediaType.APPLICATION_JSON)
                                   .get(Category.class);
            
            if (category != null) {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/View/admin/CategoryForm.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + 
                    "/View/admin/CategoryForm.jsp?error=" + URLEncoder.encode("Category not found", "UTF-8"));
            }
        } catch (Exception e) {
            handleError(request, response, e);
        }
    }

    private void cleanup(String deleteHash, int imageId) {
        try {
            ImgurUtil.deleteImage(deleteHash);
            imageDAO.deleteImage(imageId);
        } catch (Exception e) {
            System.err.println("Cleanup failed: " + e.getMessage());
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