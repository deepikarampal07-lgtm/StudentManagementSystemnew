import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ---- Apne MySQL ke hisaab se yeh values change karo ----
    private static final String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password"; // yahan apna password daalo
    // ---------------------------------------------------------

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. Jar file add karo classpath mein.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
