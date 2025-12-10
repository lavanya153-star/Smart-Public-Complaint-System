package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            String role = (String) session.getAttribute("role");
            session.invalidate();

            if ("admin".equals(role)) {
                response.sendRedirect("/Smart-Public-Complaint-System/frontend/admin/adminLogin.html");
            } else {
                response.sendRedirect("/Smart-Public-Complaint-System/frontend/user/register.html");
            }
        } else {
            response.sendRedirect("/Smart-Public-Complaint-System/frontend/user/register.html");
        }
    }
}
