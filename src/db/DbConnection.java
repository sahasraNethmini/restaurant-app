package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbConnection {

    private static DbConnection dbConnection;
    private Connection connection;

    private DbConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/restaurant", "root", "Root");

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static DbConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DbConnection();
        }
        return dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
