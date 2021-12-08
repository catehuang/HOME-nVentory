package servlets;

import dataaccess.RoleDB;
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

        // information of login user
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);
        
        // provide information for displaying all users
        List<User> all_users = as.getAll();
        request.setAttribute("all_users", all_users);
        
        // display role list
        RoleDB roleDB = new RoleDB();
        request.setAttribute("roleList", roleDB.getAll());
        
        // prepare information for updating
        String action = request.getParameter("action");
        if (action != null && !action.equals(""))
        {
            if (action.equals("edit"))
            {
                String selected_email =  request.getParameter("edit_key");
                User selected_user = as.get(selected_email);
                request.setAttribute("selected_user", selected_user);
                request.setAttribute("display", "edit_page");
            }
            else if (action.equals("delete"))
            {
                String delete_email = request.getParameter("delete_key");
                request.setAttribute("delete_key", delete_email);
                request.setAttribute("action", "delete");
                doPost(request,response);
                return;
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        int role_id;
        String active_string = request.getParameter("active");
        boolean active = false;
        
        if (active_string != null) {
            active = true;
        }

        AccountService as = new AccountService();
        User user = new User();
        
        String display = "";
        
        try {
            switch (action) {
                case "add":
                    role_id = Integer.parseInt(request.getParameter("role"));
                    try 
                    {
                        as.insert(email, password, active, firstname, lastname, role_id);
                        request.setAttribute("message", "added");
                    }
                    catch (Exception ex)
                    {
                        user.setEmail(email);
                        user.setPassword(password);
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
                        user.setActive(active);
                        user.setRole(new Role(role_id));
                        request.setAttribute("selected_user", user);
                        request.setAttribute("message", ex.getMessage());
                    }
                    break;
                case "update":
                    String ori_email = request.getParameter("ori_key");
                    role_id = Integer.parseInt(request.getParameter("role"));
                    try
                    {
                        //String email, String password, boolean active, String firstname, String lastname, int role, String email
                        as.update(email, password, active, firstname, lastname, role_id, ori_email);
                        request.setAttribute("message", "updated");
                    }
                    catch (Exception ex)
                    {
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setFirstName(firstname);
                        user.setLastName(lastname);
                        user.setActive(active);
                        user.setRole(new Role(role_id));
                        request.setAttribute("selected_user", user);
                        request.setAttribute("message", ex.getMessage());  
                        System.out.println(ex.getMessage());
                        display = "edit_page";
                    }
                    break;
                case "delete":
                    String delete_email = request.getParameter("delete_key");
                    String login_user = (String) session.getAttribute("email");
                    try
                    {
                        as.delete(delete_email, login_user);
                        request.setAttribute("message", "deleted");
                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage());
                        request.setAttribute("message", ex.getMessage()); 
                    }
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        // display login user name
        String login_email = (String) session.getAttribute("email");
        User login_user = as.get(login_email);
        request.setAttribute("user", login_user);
        
        // provide information for displaying all users
        List<User> all_users = as.getAll();
        request.setAttribute("all_users", all_users);
        
        // display role list
        RoleDB roleDB = new RoleDB();
        request.setAttribute("roleList", roleDB.getAll());
        
        request.setAttribute("display", display);
        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response);
        return;
    }
}
