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
        User user = as.get(email);

        InventoryService is = new InventoryService();
        List<Item> items = null;

        Category categories = new Category();
        CategoryDB categoriesDB = new CategoryDB();
        List<Category> categoryList = categoriesDB.getAll();

        try {
            items = is.getAll(email);
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        String mode = "add";

        if (action != null && action.equals("edit")) {
            mode = "edit";
            String key = request.getParameter("key");
            int itemID = Integer.parseInt(key);
            Item item;
            try {
                item = is.get(itemID);
                request.setAttribute("item", item);
            } catch (Exception ex) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.setAttribute("mode", mode);
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
        String email = (String) session.getAttribute("email");
        String action = request.getParameter("action");

        AccountService as = new AccountService();
        User user = as.get(email);

        Item item = new Item();
        InventoryService is = new InventoryService();

        CategoryDB categoriesDB = new CategoryDB();
        Category categories = new Category();
        List<Category> categoryList = categoriesDB.getAll();

        int categoryID;
        String itemName;
        String price;
        String key = "";
    
        categoryID = Integer.parseInt(request.getParameter("category"));
        itemName = request.getParameter("item");
        price = request.getParameter("price");
        key = request.getParameter("key");
        int itemID = 0;
        
        if (key != null && !key.equals(""))
        {
            itemID = Integer.parseInt(key);
        }
        
        try {
            switch (action) {
                case "create":
                    try {
                        // Integer itemId, String itemName, double price
                        is.insert(categoryID, itemName, price, email);
                        request.setAttribute("message", "added");
                    } catch (Exception ex) {
                        item.setCategory(categoriesDB.get(categoryID));
                        item.setItemName(itemName);

                        if (price == null || price.equals("")) {
                            item.setPrice(0);
                        } else {
                            item.setPrice(Double.parseDouble(price));
                        }

                        request.setAttribute("item", item);
                        request.setAttribute("message", ex.getMessage());
                    }
                    break;
                case "update":
                    //Integer itemID, String itemName, double price, String owner
                    try
                    {
                        is.update(categoryID, categoryID, itemName, price, email);
                        request.setAttribute("message", "updated");
                    }
                    catch (Exception ex)
                    {
                        item.setCategory(categoriesDB.get(categoryID));
                        item.setItemName(itemName);

                        if (price == null || price.equals("")) {
                            item.setPrice(0);
                        } else {
                            item.setPrice(Double.parseDouble(price));
                        }

                        request.setAttribute("item", item);
                        request.setAttribute("message", ex.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        is.delete(itemID, user);
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

        List<Item> items = null;

        try {
            items = is.getAll(email);
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
