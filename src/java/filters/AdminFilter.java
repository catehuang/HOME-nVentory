package filters;

import dataaccess.UserDB;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.*;


public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                
        HttpSession session = httpRequest.getSession();
        String email = (String) session.getAttribute("email");
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
       
        if (user.getRole().getRoleId() != 1)
        {
            System.out.println("Not Admin");
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendRedirect("inventory");
            return;
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    
}
