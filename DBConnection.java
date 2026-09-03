package inventorymanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "inventory_management";

    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    private static Connection connection = null;

    // Static utility class - never instantiate.
    private DBConnection() {
    }

   // Execption handler to check the connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(DRIVER_CLASS);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found on the classpath. "
                    + "Add mysql-connector-j-x.x.x.jar under Project Properties > Libraries.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Could not connect to '" + DB_NAME + "'. "
                    + "Is XAMPP running and is the MySQL module started?");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection = null;
        }
    }

    /** Quick health check used by the Settings screen and app startup. */
    public static boolean testConnection() {
        try {
            Connection c = getConnection();
            return c != null && !c.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
