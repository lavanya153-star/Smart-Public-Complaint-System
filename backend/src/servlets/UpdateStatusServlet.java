package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servlets.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Fetch values from form
            String idParam = request.getParameter("id");
            String status = request.getParameter("status");

            // Validation
            if (idParam == null || status == null || idParam.isEmpty() || status.isEmpty()) {
                out.println("<h3 style='color:red;'>Invalid request! Missing ID or status.</h3>");
                return;
            }

            int id = Integer.parseInt(idParam);

            // DB update using try-with-resources
            try (Connection con = DBConnection.getConnection();
                 PreparedStatement pst = con.prepareStatement(
                         "UPDATE complaints SET status = ? WHERE id = ?")) {

                pst.setString(1, status);
                pst.setInt(2, id);

                int rows = pst.executeUpdate();

                if (rows > 0) {
                    // Redirect back to complaints page
                    response.sendRedirect("viewComplaints");
                } else {
                    out.println("<h3 style='color:red;'>Error: Complaint not found!</h3>");
                }
            }

        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Error updating status!</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
            e.printStackTrace();
        }
    }
}
