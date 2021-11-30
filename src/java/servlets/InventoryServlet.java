package servlets;

import dataaccess.CategoriesDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Categories;
import models.Items;
import models.Users;
import services.AccountService;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        AccountService as = new AccountService();
        Users user = as.get(username);

        InventoryService is = new InventoryService();
        List<Items> items = null;

        Categories categories = new Categories();
        CategoriesDB categoriesDB = new CategoriesDB();
        List<Categories> categoryList = categoriesDB.getAll();

        try {
            items = is.getAll(username);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("user", user);
        request.setAttribute("items", items);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get the username from current session
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String action = request.getParameter("action");

        AccountService as = new AccountService();
        Users user = as.get(username);

        Items item = new Items();
        InventoryService is = new InventoryService();

        CategoriesDB categoriesDB = new CategoriesDB();
        Categories categories = new Categories();
        List<Categories> categoryList = categoriesDB.getAll();

        try {
            switch (action) {
                case "create":
                    int categoryID = Integer.parseInt(request.getParameter("category"));
                    String itemName = request.getParameter("item");
                    String price = request.getParameter("price");

                    try {
                        is.insert(categoryID, itemName, price, username);
                        request.setAttribute("message", "added");
                    } catch (Exception ex) {
                        item.setCategory(categoriesDB.get(categoryID));
                        item.setItemName(itemName);
                        
                        if (price == null || price.equals("")){
                            item.setPrice(0);
                        }
                        else
                        {
                            item.setPrice(Double.parseDouble(price));
                        }
                        
                        request.setAttribute("item", item);
                        request.setAttribute("message", ex.getMessage());
                    }

                    break;
                case "delete":
                    try {
                        String key = request.getParameter("key");
                        int itemID = Integer.parseInt(key);
                        is.delete(itemID, username);
                        request.setAttribute("message", "deleted");
                    } catch (Exception ex) {
                        request.setAttribute("message", ex.getMessage());
                    }
                    break;
            }
        } catch (Exception ex) {
            request.setAttribute("invalid_input", true);
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Items> items = null;

        try {
            items = is.getAll(username);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        request.setAttribute("user", user);
        request.setAttribute("items", items);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("categories", categories);

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }
}
