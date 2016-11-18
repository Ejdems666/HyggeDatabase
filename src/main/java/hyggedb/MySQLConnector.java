package hyggedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class MySQLConnector implements Connector {
    private static final String IP = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "cba_jdbc_qb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private Connection conn = null;

    public MySQLConnector() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE;
        this.conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
    }
    public Connection getConnection() {
        return this.conn;
    }

    @Override
    public void close() {
        try {
            getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
