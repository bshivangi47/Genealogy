import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author name: Shivangi Bhatt
 * ClassName : ConnectionClass
 *
 */
public class ConnectionClass {
    // initialize connection variables
    private static String url = "jdbc:mysql://localhost:3306/family_tree";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "root";
    private static String password = "root";
    private static Connection con;

    /*
     * method name : getConnection
     * method purpose : Returns the connection object
     * arguments : none
     * return value : Connection object
     */
    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {

                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }

}
