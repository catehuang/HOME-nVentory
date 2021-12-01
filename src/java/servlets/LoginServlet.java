package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;
import services.AccountService;


public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        session.invalidate(); // just by going to the login page the user is logged out :-) 
        
        String query = request.getQueryString();
        /*
        if(query.equals("logout"))
        {
            request.setAttribute("message", "logout");
        }
        */
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        AccountService as = new AccountService();
        User user = as.login(email, password);
        
        if (user == null) {
            request.setAttribute("email", email);
            request.setAttribute("message", "invalid_login");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        HttpSession session = request.getSession();
        session.setAttribute("email", email);
        
        if (user.getRole().getRoleId() == 1) {
            response.sendRedirect("admin");
            return; 
        } else {
            response.sendRedirect("inventory");
            return; 
        }
    }
}
