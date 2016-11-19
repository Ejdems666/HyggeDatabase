package hyggedb.select;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class SelectionExecutor {
    private Connection connection;
    private PreparedStatement query;
    private int parameterCount = 1;

    public SelectionExecutor(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getResult(Selection selection) {
        SelectionAssembler selectionAssembler = new SelectionAssembler(selection);
        try {
            String sql = selectionAssembler.assemble();
            query = connection.prepareStatement(sql);
            parameterCount = 1;
            injectCondition(selection," WHERE ");
            injectCondition(selection," HAVING ");
            return query.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String> getAllColumns(Selection selection){
        ArrayList<String> columns = new ArrayList<>();
        columns.addAll(((Column) selection.getClause("SELECT ")).getColumns());
        for (Join join : selection.getJoins()) {
            columns.addAll(((Column) join.getClause("SELECT ")).getColumns());
        }
        return columns;
    }
    private void injectCondition(Selection selection,String conditionType) throws SQLException {
        injectConditionsValues(((Condition) selection.getClause(conditionType)));
        for (Join join : selection.getJoins()) {
            injectConditionsValues(((Condition) join.getClause(conditionType)));
        }
    }
    private void injectConditionsValues(Condition condition) throws SQLException {
        if (condition == null) return;
        for (Object conditionValue : condition.getValues()) {
            if(conditionValue instanceof Integer) {
                query.setInt(parameterCount, ((Integer) conditionValue));
            } else {
                query.setString(parameterCount, ((String) conditionValue));
            }
            parameterCount++;
        }
    }
}
