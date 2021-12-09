package servlets;

import dataaccess.ItemDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import models.User;
import services.AccountService;
import services.InventoryService;

public class SearchServlet extends HttpServlet {

    @Override
    @SuppressWarnings("null")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);

        // get all items belong to the login user
        InventoryService is = new InventoryService();
        List<Item> items = null;
        try {
            items = is.getAll();
        } catch (Exception ex) {
            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        ArrayList<Item> matchedItems = new ArrayList<Item>();
        if (items != null && request.getParameter("keyword") != null)
        {
            String keyword = request.getParameter("keyword").toLowerCase();
            
            for (Item item : items) 
            {  
                if (item.getItemName().toLowerCase().contains(keyword))
                {
                    matchedItems.add(item);
                }
            }
            items = matchedItems;
        }
 
        request.setAttribute("items", items);
        
        getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               
        getServletContext().getRequestDispatcher("/WEB-INF/search.jsp").forward(request, response);
        return;
    }

}
