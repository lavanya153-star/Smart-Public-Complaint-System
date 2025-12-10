package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servlets.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/viewComplaints")
public class ViewComplaintsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT id, name, email, address, category, description, status FROM complaints";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>All Complaints</title>");
            out.println("<style>");
            out.println("table { width: 90%; border-collapse: collapse; margin: 20px auto; }");
            out.println("th, td { padding: 10px; border: 1px solid #333; text-align: center; }");
            out.println("th { background-color: #4CAF50; color: white; }");
            out.println(".btn { padding: 6px 12px; background: blue; color: white; border: none; cursor: pointer; }");
            out.println(".btn:hover { background: darkblue; }");
            out.println("h2 { text-align: center; }");
            out.println(".back { margin-left: 50px; }");
            out.println("</style>");
            out.println("</head>");

            out.println("<body>");
            out.println("<h2>All Complaints</h2>");

            out.println("<table>");
            out.println("<tr>"
                    + "<th>ID</th>"
                    + "<th>Name</th>"
                    + "<th>Email</th>"
                    + "<th>Address</th>"
                    + "<th>Category</th>"
                    + "<th>Description</th>"
                    + "<th>Status</th>"
                    + "<th>Action</th>"
                    + "</tr>");

            while (rs.next()) {
                int id = rs.getInt("id");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("email") + "</td>");
                out.println("<td>" + rs.getString("address") + "</td>");
                out.println("<td>" + rs.getString("category") + "</td>");
                out.println("<td>" + rs.getString("description") + "</td>");

                out.println("<td>" + rs.getString("status") + "</td>");

                out.println("<td>"
                        + "<form action='updateStatus' method='post'>"
                        + "<input type='hidden' name='id' value='" + id + "'>"
                        + "<select name='status'>"
                        + "<option value='Pending'>Pending</option>"
                        + "<option value='In Progress'>In Progress</option>"
                        + "<option value='Completed'>Completed</option>"
                        + "</select>"
                        + " <input class='btn' type='submit' value='Update'>"
                        + "</form>"
                        + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("<div class='back'>");
            out.println("<a href='/Smart-Public-Complaint-System/frontend/admin/adminDashboard.html" + "'>Back to Dashboard</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<h3 style='color:red; text-align:center;'>Error loading complaints!</h3>");
            out.println("<pre>" + e.getMessage() + "</pre>");
        }
    }
}
