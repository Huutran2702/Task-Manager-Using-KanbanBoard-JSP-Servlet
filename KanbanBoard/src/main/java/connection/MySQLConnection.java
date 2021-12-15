package connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private String jdbcURLFormat = "jdbc:mysql://localhost:3306/%s?useUnicode=true&characterEncoding=utf-8";
    private String jdbcUsername = "root";
    private String jdbcPassword = "mytolove123";
    private String databaseName;
    private static MySQLConnection instance;

    private MySQLConnection() {
    }


    public static MySQLConnection getInstance() {
        if (instance == null)
            instance = new MySQLConnection();
        return instance;
    }

    public static void init(String databaseName) {
        getInstance().databaseName = databaseName;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(String.format(jdbcURLFormat, databaseName), jdbcUsername, jdbcPassword);
        } catch ( ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
