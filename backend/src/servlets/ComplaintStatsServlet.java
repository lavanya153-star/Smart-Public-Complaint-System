package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/getComplaintStats")
public class ComplaintStatsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        int total = 0, pending = 0, inProgress = 0, completed = 0;

        try {
            Connection con = DBConnection.getConnection();

            // Total Complaints
            PreparedStatement ps1 = con.prepareStatement("SELECT COUNT(*) FROM complaints");
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) total = rs1.getInt(1);

            // Pending
            PreparedStatement ps2 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='Pending'");
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) pending = rs2.getInt(1);

            // In Progress
            PreparedStatement ps3 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='In Progress'");
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) inProgress = rs3.getInt(1);

            // Completed
            PreparedStatement ps4 = con.prepareStatement("SELECT COUNT(*) FROM complaints WHERE status='Completed'");
            ResultSet rs4 = ps4.executeQuery();
            if (rs4.next()) completed = rs4.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return JSON response
        out.print("{"
                + "\"total\":" + total + ","
                + "\"pending\":" + pending + ","
                + "\"inProgress\":" + inProgress + ","
                + "\"completed\":" + completed
                + "}");
        out.flush();
    }
}
