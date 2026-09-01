package inventorymanagementsystem.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Single point of contact with MySQL/XAMPP. Every DAO calls
 * {@link #getConnection()} instead of opening its own connection.
 *
 * XAMPP defaults used below:
 *   host = localhost, port = 3306, user = root, password = "" (empty)
 * Change USER/PASSWORD here if you've secured your XAMPP MySQL install.
 *
 * Setup required in NetBeans before this will compile & run:
 *   1. Download "MySQL Connector/J" (mysql-connector-j-x.x.x.jar).
 *   2. Right-click the project -> Properties -> Libraries -> Add JAR/Folder,
 *      and select the downloaded jar.
 *   3. Start XAMPP, turn on the MySQL module, and import
 *      inventory_management.sql via phpMyAdmin (or the mysql CLI).
 */
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

    /**
     * Returns a live connection, opening a new one if none exists yet or
     * the previous one was closed. Callers should still use try-with-resources
     * on the Connection/Statement/ResultSet they obtain from it.
     */
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
