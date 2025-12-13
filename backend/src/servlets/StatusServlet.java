package com.smart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servlets.DBConnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/status")
public class StatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Complaint Status</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body class='bg-light'>");

        out.println("<div class='container mt-5'>");
        out.println("<div class='row justify-content-center'>");
        out.println("<div class='col-md-8 bg-white p-4 shadow rounded'>");

        out.println("<h3 class='text-center mb-4'>Complaint Status</h3>");

        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT category, description, status FROM complaints WHERE email = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);

            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                out.println("<div class='alert alert-danger text-center'>No complaint found for this email.</div>");
            } else {

                out.println("<table class='table table-bordered'>");
                out.println("<thead class='table-primary'>");
                out.println("<tr>"

                        + "<th>Description</th>"
                        + "<th>Status</th>"
                        + "</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                do {
                    out.println("<tr>");

                    out.println("<td>" + rs.getString("description") + "</td>");
                    out.println("<td><b>" + rs.getString("status") + "</b></td>");
                    out.println("</tr>");
                } while (rs.next());

                out.println("</tbody></table>");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        out.println("<br><a href='/Smart-Public-Complaint-System/frontend/user/home.html' class='btn btn-secondary w-100'>Back to Home</a>");

        out.println("</div></div></div>");
        out.println("</body></html>");
    }
}
