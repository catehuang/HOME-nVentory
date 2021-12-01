package servlets;

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

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        String mode = "add";

        AccountService as = new AccountService();
        
        List<User> users = as.getAll();

        request.setAttribute("users", users);
        request.setAttribute("as", as);

        if (action != null && !action.equals("") && action.equals("edit")) {
            mode = "edit";
            User user = new User();
            String username = request.getParameter("key");
            user = as.get(username);
            request.setAttribute("selectedUser", user);
            session.setAttribute("originalUser", username);
        }

        request.setAttribute("mode", mode);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String action = request.getParameter("action");

        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");

        AccountService as = new AccountService();
        User user = new User();
        
        String mode = "add";

        try {
            switch (action) {
                case "add":
                    try 
                    {
                        as.insert(email, password, true, firstname, lastname, 2);
                        request.setAttribute("message", "added");
                    }
                    catch (Exception ex)
                    {
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
                        request.setAttribute("user", user);
                        request.setAttribute("message", ex.getMessage());
                        mode = "edit";
                    }
                    break;
                case "update":
                    String original = (String) session.getAttribute("originalUser");
                    try
                    {
                        //String email, String password, boolean active, String firstname, String lastname, int role, String original
                        as.update(email, password, true, firstname, lastname, 2, original);
                        request.setAttribute("message", "updated");
                    }
                    catch (Exception ex)
                    {
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
                        request.setAttribute("selectedUser", user);
                        request.setAttribute("message", ex.getMessage());  
                        mode = "edit";
                    }
                    break;
                case "delete":
                    String deleteThis = request.getParameter("key");
                    String adminUser = (String) session.getAttribute("email");
                    try
                    {
                        as.delete(deleteThis, adminUser);
                        request.setAttribute("message", "deleted");
                    }
                    catch (Exception ex)
                    {
                        request.setAttribute("message", ex.getMessage()); 
                    }
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<User> users = as.getAll();
        request.setAttribute("users", users);
        request.setAttribute("mode", mode);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }
}
