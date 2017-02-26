package hyggedb.insert;

import java.sql.*;

/**
 * Created by Ejdems on 27/11/2016.
 */
public class QueryExecutor {
    private Connection connection;
    private PreparedStatement query;

    public QueryExecutor(Connection connection) {
        this.connection = connection;
    }

    public int insert(String sql, Object[] values) {
        try {
            query = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            injectValues(values);
            query.execute();
            ResultSet rs = query.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int update(String sql, Object[] values) {
        try {
            query = connection.prepareStatement(sql);
            injectValues(values);
            query.executeUpdate();
            return query.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private void injectValues(Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            if(values[i] instanceof Integer) {
                query.setInt(i+1, ((Integer) values[i]));
            } else if(values[i] instanceof Double){
                query.setDouble(i+1, ((Double) values[i]));
            } else if(values[i] instanceof Date){
                query.setDate(i+1, ((Date) values[i]));
            } else if(values[i] instanceof Float){
                query.setFloat(i+1, ((Float) values[i]));
            } else {
                query.setString(i+1, ((String) values[i]));
            }
        }
    }
}
