package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/submitPage")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // If no login session
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Smart-Public-Complaint-System/frontend/user/register.html");
            return;
        }

        // Logged in → allow access to submit complaint page
        response.sendRedirect("Smart-Public-Complaint-System/frontend/user/submitComplaint.html");
    }
}
