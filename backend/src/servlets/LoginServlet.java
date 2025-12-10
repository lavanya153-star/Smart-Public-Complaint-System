package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();
                session.setAttribute("email", email);

                session.setAttribute("role", "user");


                response.sendRedirect(
                        request.getContextPath() + "/frontend/user/submitComplaint.html"
                );

            } else {
                // ❌ Wrong Login – Stay on login page
                response.sendRedirect(
                        request.getContextPath() + "/frontend/user/login.html"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
