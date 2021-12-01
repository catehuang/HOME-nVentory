package servlets;

import dataaccess.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.AccountService;

public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");

        AccountService as = new AccountService();
        User user = as.get(email);
        RoleDB roleDB = new RoleDB();
        Role role = new Role();

        request.setAttribute("user", user);
        request.setAttribute("roleList", roleDB.getAll());

        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login_user = (String) session.getAttribute("email");
        String email = (String) (request.getParameter("email"));
        String password = (String) (request.getParameter("password"));
        String firstname = (String) (request.getParameter("firstname"));
        String lastname = (String) (request.getParameter("lastname"));
        int role = Integer.parseInt(request.getParameter("role"));
        String active_string = (String) request.getParameter("active");

        boolean active = false;
        if (active_string != null) {
            active = true;
        }
        //String email, boolean active, String firstName, String lastName, String password
        User user = new User(email, active, firstname, lastname, password);
        user.setRole(new Role(role));

        AccountService as = new AccountService();

        try 
        {
            as.update(email, password, active, firstname, lastname, role, login_user);
            request.setAttribute("message", "updated");
        } 
        catch (Exception ex) 
        {
            request.setAttribute("user", user);
            request.setAttribute("message", ex.getMessage());
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        return;
    }
}
