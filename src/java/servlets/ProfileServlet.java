package servlets;

import dataaccess.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.AccountService;

public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        
        AccountService as = new AccountService();
        User user = as.get(email);
        RoleDB roleDB = new RoleDB(); 
        Role role = new Role();
        
        request.setAttribute("user", user);
        request.setAttribute("roleList", roleDB.getAll());
        request.setAttribute("role", role);

        
        
        
        
        
        
        
        getServletContext().getRequestDispatcher("/WEB-INF/profile.jsp").forward(request, response);
        return;
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

}
