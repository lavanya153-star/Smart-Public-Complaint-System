package servlets;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/complaint_system",
                    "root",
                    "lav@123ramu1"
            );

            System.out.println("✅ Database Connected Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
