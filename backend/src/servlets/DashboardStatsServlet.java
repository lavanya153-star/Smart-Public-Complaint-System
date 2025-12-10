package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servlets.DBConnection;

import java.io.IOException;
import java.sql.*;

@WebServlet("/dashboardStats")
public class DashboardStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int total = 0, pending = 0, progress = 0, completed = 0;

        try {
            Connection con = DBConnection.getConnection();

            // Total
            PreparedStatement pst1 = con.prepareStatement("SELECT COUNT(*) FROM complaints");
            ResultSet rs1 = pst1.executeQuery();
            if (rs1.next()) total = rs1.getInt(1);

            // Pending
            PreparedStatement pst2 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='Pending'");
            ResultSet rs2 = pst2.executeQuery();
            if (rs2.next()) pending = rs2.getInt(1);

            // In Progress
            PreparedStatement pst3 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='In Progress'");
            ResultSet rs3 = pst3.executeQuery();
            if (rs3.next()) progress = rs3.getInt(1);

            // Completed
            PreparedStatement pst4 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='Completed'");
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) completed = rs4.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redirect with values in URL
        response.sendRedirect("frontend/admin/adminDashboard.html"
                + "?total=" + total
                + "&pending=" + pending
                + "&progress=" + progress
                + "&completed=" + completed);
    }
}
