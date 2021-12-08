package servlets;

import dataaccess.CategoryDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.User;
import services.AccountService;

public class CategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);
        Category category = new Category();
        CategoryDB categoriesDB = new CategoryDB();

        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");

            if (action.equals("edit")) {
                int category_id = Integer.parseInt(request.getParameter("edit_id"));

                try {
                    category = categoriesDB.get(category_id);
                    request.setAttribute("selected_category", category);
                    request.setAttribute("display", "edit_page");
                } catch (Exception ex) {
                    Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // get all categories for displaying dropdown list
        List<Category> categoryList = categoriesDB.getAll();
        request.setAttribute("categoryList", categoryList);

        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
        return;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get login user information
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);

        Category category = new Category();
        CategoryDB categoriesDB = new CategoryDB();
        String categoryName = request.getParameter("categoryName");

        if (categoryName == null || categoryName.equals("")) {
            request.setAttribute("message", "empty");
        } else {
            if (request.getParameter("action") != null) {
                String action = request.getParameter("action");
                category.setCategoryName(categoryName);
                
                System.out.println(action);
                
                switch (action) {
                    case "add":
                        try {
                            categoriesDB.insert(category);
                            request.setAttribute("message", "added");
                        } catch (Exception ex) {
                            request.setAttribute("category", category);
                            request.setAttribute("display", "edit_page");
                            request.setAttribute("message", ex.getMessage());
                            System.out.println(ex);
                        }
                        break;

                    case "update":
                        int update_id = Integer.parseInt(request.getParameter("update_id"));
                        category.setCategoryId(update_id);
                        try {
                            categoriesDB.update(category);
                            request.setAttribute("message", "updated");
                        } catch (Exception ex) {
                            request.setAttribute("selected_category", category);
                            request.setAttribute("display", "edit_page");
                            request.setAttribute("message", ex.getMessage());
                        }
                        break;
                }
            }
        }
    
        // get all categories for displaying dropdown list
        List<Category> categoryList = categoriesDB.getAll();
        request.setAttribute("categoryList", categoryList);

        getServletContext().getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
        return;
    }
}