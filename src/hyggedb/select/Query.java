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
    private PreparedStatement query;
    private int parameterCount = 1;

    public Query(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getResult(Selection selection) {
        QueryAssembler queryAssembler = new QueryAssembler(selection);
        try {
            String sql = queryAssembler.assemble();
            query = connection.prepareStatement(sql);
            parameterCount = 1;
            injectWhereConditions(selection);
            injectConditionsValues(selection.getHaving());
            return query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void injectWhereConditions(Selection selection) throws SQLException {
        injectConditionsValues(((Condition) selection.getClause(" WHERE ")));
        for (Join join : selection.getJoins()) {
            injectConditionsValues(((Condition) join.getClause(" WHERE ")));
        }
    }
    private void injectConditionsValues(Condition condition) throws SQLException {
        if (condition == null) return;
        ArrayList<String> conditionValues = condition.getValues();
        for (int i = 0; i < conditionValues.size(); i++) {
            try {
                int parsedValue = Integer.parseInt(conditionValues.get(i));
                query.setInt(parameterCount,parsedValue);
            } catch (NumberFormatException e) {
                query.setString(parameterCount,conditionValues.get(i).substring(1));
            }
            parameterCount++;
        }
    }
}
