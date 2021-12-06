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
import models.*;
import services.AccountService;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        AccountService as = new AccountService();
        InventoryService is = new InventoryService();

        // get information of login user
        User user = as.get(email);
        request.setAttribute("user", user);
        
        // get all items belong to the login user
        List<Item> items = null;
        try {
            items = is.getAll(email);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }        
        request.setAttribute("items", items);
        
        // get all categories for displaying dropdown list
        CategoryDB categoriesDB = new CategoryDB();
        List<Category> categoryList = categoriesDB.getAll();
        request.setAttribute("categoryList", categoryList);
        
        // utility for converting Category model
        Category categories = new Category();
        request.setAttribute("categories", categories);
        
        if (request.getParameter("action") != null)
        {
            String action = request.getParameter("action");
            
            switch (action)
            {
                // Retrieve item information for further update
                case "retrieve":
                    Item item;
                    int itemID = Integer.parseInt(request.getParameter("itemID"));
                    try {
                        item = is.get(itemID);
                        request.setAttribute("item", item);
                        request.setAttribute("display", "edit_page");
                    } catch (Exception ex) {
                        Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // utilities
        CategoryDB categoriesDB = new CategoryDB();
        InventoryService is = new InventoryService();
        AccountService as = new AccountService();
        Item item = new Item();
        
        // get login user information
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        User user = as.get(email);
        
        if (request.getParameter("action") != null)
        {
            String action = request.getParameter("action");
            int categoryID;
            String itemName;
            String price;
            int itemID;
            
            switch (action)
            {
                case "add":
                    categoryID = Integer.parseInt(request.getParameter("category"));
                    itemName = request.getParameter("itemName");
                    price = request.getParameter("price");
                    
                    try {
                        is.insert(categoryID, itemName, price, email);
                        request.setAttribute("message", "added");
                    } catch (Exception ex) {
                        Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                        
                        if (price == null || price.equals("")) {
                            item.setPrice(0);
                        } else {
                            item.setPrice(Double.parseDouble(price));
                        }

                        request.setAttribute("item", item);
                        request.setAttribute("message", ex.getMessage());
                        request.setAttribute("display", "edit_page");
                    }
                    break;
                case "update":
                    itemID = Integer.parseInt(request.getParameter("itemID"));
                    categoryID = Integer.parseInt(request.getParameter("category"));
                    itemName = request.getParameter("itemName");
                    price = request.getParameter("price");
                    
                    try {
                        is.update(itemID, categoryID, itemName, price, email);
                        request.setAttribute("message", "updated");
                    } catch (Exception ex) {
                        Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                        
                        item.setCategory(categoriesDB.get(categoryID));
                        item.setItemName(itemName);

                        if (price == null || price.equals("")) {
                            item.setPrice(0);
                        } else {
                            item.setPrice(Double.parseDouble(price));
                        }

                        request.setAttribute("message", ex.getMessage());

                        if (! ex.getMessage().equals("not_belong_to_you")) {
                            request.setAttribute("item", item);
                            request.setAttribute("display", "edit_page");
                        }
                    }
                    break;
                case "delete":
                    itemID = Integer.parseInt(request.getParameter("itemID"));
                    try {
                        is.delete(itemID, user);
                        request.setAttribute("message", "deleted");
                    } catch (Exception ex) {
                        request.setAttribute("message", ex.getMessage());
                    }
                    break;
            }
        }    

        // for displaying inventory page
        request.setAttribute("user", user);
        
        // get all items belong to the login user
        List<Item> items = null;
        try {
            items = is.getAll(email);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }        
        request.setAttribute("items", items);
        
        // get all categories for displaying dropdown list
        List<Category> categoryList = categoriesDB.getAll();
        request.setAttribute("categoryList", categoryList);
        
        // utility for converting Category model
        Category categories = new Category();
        request.setAttribute("categories", categories);
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }
}
