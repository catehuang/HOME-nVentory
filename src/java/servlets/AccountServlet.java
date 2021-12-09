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
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        
        AccountService as = new AccountService();
        String active_string = request.getParameter("active");
        Role role = as.get(login_user).getRole();

        boolean active = false;
        if (active_string != null) {
            active = true;
        }
        //String email, boolean active, String firstName, String lastName, String password
        User user = new User(email, active, firstname, lastname, password);
        user.setRole(role);

        try 
        {
            as.update(email, password, active, firstname, lastname, role.getRoleId(), login_user);
            request.setAttribute("message", "updated");
            session.setAttribute("email", email);
        } 
        catch (Exception ex) 
        {
            request.setAttribute("message", ex.getMessage());
            Logger.getLogger(AccountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (user.getActive() != true)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        request.setAttribute("user", user);
        RoleDB roleDB = new RoleDB();
        request.setAttribute("roleList", roleDB.getAll());
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        return;
    }
}
