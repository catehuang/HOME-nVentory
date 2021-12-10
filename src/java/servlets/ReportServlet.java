package servlets;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class ReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);

        // get all users in database
        List<User> all_users = as.getAll();
        request.setAttribute("all_users", all_users);

        getServletContext().getRequestDispatcher("/WEB-INF/report.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        AccountService as = new AccountService();
        User user = as.get(email);
        request.setAttribute("user", user);

        // get all users in database
        List<User> all_users = as.getAll();
        
        if (request.getParameter("action") != null)
        {
            String action = request.getParameter("action");
            System.out.println(action);
            
            if (action.equals("Export Report For All Users"))
            {
                request.setAttribute("all_users", all_users);
                getServletContext().getRequestDispatcher("/WEB-INF/exportAllUsers.jsp").forward(request, response);
                return;
            }
            else if (action.equals("Export Report For Regular Users"))
            {
                ArrayList<User> regular_active_users = new ArrayList();
                
                for (User u : all_users)
                {
                    // regular and active users only
                    if (u.getActive() == true && u.getRole().getRoleId() == 2)
                    {
                        regular_active_users.add(u);
                    }
                }
                
                regular_active_users.sort(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName));
                all_users = regular_active_users;
                request.setAttribute("all_users", all_users);
                
                getServletContext().getRequestDispatcher("/WEB-INF/exportRegularUsers.jsp").forward(request, response);
                return;
            }
        }
    }
}
