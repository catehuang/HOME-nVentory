package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.AccountService;



public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        request.setAttribute("email", email);
        getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = getServletContext().getRealPath("/WEB-INF");
        String email = request.getParameter("email");
        String forgot_option = request.getParameter("forgot_option");
        
        AccountService as = new AccountService();

        as.forgotPassword(email, path);

        request.setAttribute("email", email);
        request.setAttribute("message", "show");
        
        getServletContext().getRequestDispatcher("/WEB-INF/forgot.jsp").forward(request, response);
        return;
    }

}
