package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servlets.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/submitComplaint")
public class SubmitComplaintServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // User must be logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            response.sendRedirect("/Smart-Public-Complaint-System/frontend/user/login.html");
            return;
        }

        // Reading form values
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        String department = request.getParameter("department");


        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO complaints(name, email, address, category, department, description, status, adminResponse) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);

            ps.setString(5, department);

            ps.setString(7, "Pending");       // default status
            ps.setString(8, "");              // adminResponse empty

            ps.executeUpdate();

            // success popup
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<script>");

            out.println("window.location='/Smart-Public-Complaint-System/frontend/user/home.html';");
            out.println("</script>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
