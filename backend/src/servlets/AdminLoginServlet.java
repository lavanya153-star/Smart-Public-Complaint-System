package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hardcoded admin login
        if (username.equals("admin") && password.equals("admin123")) {

            HttpSession session = request.getSession();
            session.setAttribute("admin", username);

            // ⭐ ADD THIS
            session.setAttribute("role", "admin");

            response.sendRedirect("frontend/admin/adminDashboard.html");
        } else {
            response.setContentType("text/html");
            response.getWriter()
                    .println("<script>alert('Invalid Credentials'); window.location='frontend/admin/adminLogin.html';</script>");
        }
    }
}
