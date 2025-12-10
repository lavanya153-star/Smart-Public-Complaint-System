package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("Register servlet called ✅");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, password);

            pst.executeUpdate();

            System.out.println("Data inserted successfully ✅");

            response.sendRedirect("http://localhost:8080/Smart-Public-Complaint-System/frontend/user/login.html");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().print("ERROR: " + e.getMessage());
        }
    }

}
