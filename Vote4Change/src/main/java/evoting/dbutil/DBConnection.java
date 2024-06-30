package evoting.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection conn = null;
    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Driver loaded...");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//ROSHANPRO:1521/xe", "evoting", "evoting");
            System.out.println("Connection opened...");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not loaded!");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Problem in opening the connection!");
            ex.printStackTrace();
        }
    }
    public static Connection getConnection() {
        return conn;
    }
    public static void closeConnection() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Problem in closing the connection!");
            ex.printStackTrace();
        }
    }
}
