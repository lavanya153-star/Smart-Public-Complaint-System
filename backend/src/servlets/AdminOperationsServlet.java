package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/adminOperations")
public class AdminOperationsServlet extends HttpServlet {

    // Utility method for DB connection
    private Connection getConnection() throws Exception {
        return DBConnection.getConnection();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        switch (action) {

            case "addDepartment":
                addDepartment(request, response);
                break;

            case "sendResponse":
                sendResponse(request, response);
                break;

            default:
                response.getWriter().println("Invalid Action!");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String action = request.getParameter("action");

        switch (action) {

            case "viewDepartments":
                viewDepartments(request, response);
                break;

            case "deleteDepartment":
                deleteDepartment(request, response);
                break;

            case "notifications":
                userNotifications(request, response);
                break;

            default:
                response.getWriter().println("Invalid Action!");
        }
    }

    // -------------------------------------------
    //  FEATURE 1 — ADD DEPARTMENT
    // -------------------------------------------
    private void addDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("department");

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO departments(name) VALUES(?)");
            ps.setString(1, name);
            ps.executeUpdate();

            resp.sendRedirect("frontend/admin/departments.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    //  FEATURE 1 — VIEW DEPARTMENTS
    // -------------------------------------------
    private void viewDepartments(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");

        try {
            Connection con = getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM departments");

            PrintWriter out = resp.getWriter();

            out.println("<h2>Departments</h2>");
            out.println("<table border='1' width='100%'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Delete</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td><a href='adminOperations?action=deleteDepartment&id=" + rs.getInt("id") + "'>Delete</a></td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    //  FEATURE 1 — DELETE DEPARTMENT
    // -------------------------------------------
    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM departments WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();

            resp.sendRedirect("adminOperations?action=viewDepartments");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    //  FEATURE 2 — SEND RESPONSE TO USER
    // -------------------------------------------
    private void sendResponse(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int cid = Integer.parseInt(req.getParameter("cid"));
        String responseText = req.getParameter("response");

        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE complaints SET adminResponse=?, responseTime=NOW() WHERE id=?"
            );

            ps.setString(1, responseText);
            ps.setInt(2, cid);
            ps.executeUpdate();

            resp.sendRedirect("/Smart-Public-Complaint-System/frontend/admin/adminDashboard.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------
    //  FEATURE 3 — USER NOTIFICATIONS
    // -------------------------------------------
    private void userNotifications(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        String user = (String) session.getAttribute("email");

        resp.setContentType("text/html");

        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT id, description, adminResponse, responseTime FROM complaints " +
                            "WHERE email=? AND adminResponse IS NOT NULL AND adminResponse <> '' " +
                            "ORDER BY responseTime DESC"
            );

            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();

            PrintWriter out = resp.getWriter();
            out.println("<h2>Your Notifications</h2>");

            out.println("<table border='1' width='100%'>");
            out.println("<tr><th>Complaint</th><th>Response</th><th>Time</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("description") + "</td>");
                out.println("<td>" + rs.getString("adminResponse") + "</td>");
                out.println("<td>" + rs.getString("responseTime") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
