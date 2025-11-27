import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/grievance_system";
        String user = "root"; // replace with your MySQL username
        String password = "lav@123ramu1"; // replace with your MySQL password

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Connected to MySQL successfully!");

            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
           // e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Connection failed.");
           // e.printStackTrace();
        }
    }
}