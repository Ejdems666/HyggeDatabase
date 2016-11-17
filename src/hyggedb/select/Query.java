package hyggedb.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Query {
    private Connection connection;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getResult(Selection selection) {
        QueryAssembler queryAssembler = new QueryAssembler(selection);
        try {
            String sql = queryAssembler.assemble();
            PreparedStatement query = connection.prepareStatement(sql);
            injectAllValues(query,selection);
            return query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void injectAllValues(PreparedStatement query, Selection selection) throws SQLException {
        if(selection.getClause(" WHERE ") != null) {
            injectTableValues(query, ((Where) selection.getClause(" WHERE ")));
        }
        for (Join join : selection.getJoins()) {
            injectTableValues(query, ((Where) join.getClause(" WHERE ")));
        }
    }
    private void injectTableValues(PreparedStatement query, Where where) throws SQLException {
        ArrayList<String> whereValues = where.getValues();
        for (int i = 0; i < whereValues.size(); i++) {
            try {
                int parsedValue = Integer.parseInt(whereValues.get(i));
                query.setInt(i+1,parsedValue);
            } catch (NumberFormatException e) {
                query.setString(i+1,whereValues.get(i).substring(1));
            }
        }
    }
}
