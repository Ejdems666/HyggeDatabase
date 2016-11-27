package hyggedb.select;

import java.sql.Connection;
import java.util.HashMap;

/**
 * Created by Ejdems on 17/11/2016.
 */
public abstract class TableQuery {
    protected String tableName;
    protected HashMap<String, Clause> clauses = new HashMap<>();

    public TableQuery(String tableName) {
        this.tableName = tableName;
    }

    public GroupBy groupBy(String clause) {
        return groupBy(clause.split(","));
    }

    public GroupBy groupBy(String[] clauses) {
        GroupBy groupBy = new GroupBy(tableName, clauses);
        this.clauses.put(" GROUP BY ", groupBy);
        return groupBy;
    }

    public GroupBy groupBy(Function function) {
        GroupBy groupBy = new GroupBy(tableName, function);
        this.clauses.put(" GROUP BY ", groupBy);
        return groupBy;
    }

    public OrderBy orderBy(String clause) {
        return orderBy(clause.split(","));
    }

    public OrderBy orderBy(String[] clauses) {
        OrderBy orderBy = new OrderBy(tableName, clauses);
        this.clauses.put(" ORDER BY ", orderBy);
        return orderBy;
    }

    public OrderBy orderBy(Function function, String mode) {
        OrderBy orderBy = new OrderBy(tableName, function, mode);
        this.clauses.put(" ORDER BY ", orderBy);
        return orderBy;
    }

    public Condition where(String clause, Object value) {
        return createCondition("", " WHERE ", clause, value);
    }

    public Condition where(Function function, String operator, Object value) {
        return createCondition("", " WHERE ", function, operator, value);
    }

    public Condition having(String clause, Object value) {
        return createCondition("", " HAVING ", clause, value);
    }

    public Condition having(Function function, String operator, Object value) {
        return createCondition("", " WHERE ", function, operator, value);
    }

    protected Condition createCondition(String prefix, String key, String clause, Object value) {
        Condition where = new Condition(prefix, tableName, clause, value);
        clauses.put(key, where);
        return where;
    }

    protected Condition createCondition(String prefix, String key, Function function, String operator, Object value) {
        Condition where = new Condition(prefix, tableName, function, operator, value);
        clauses.put(key, where);
        return where;
    }

    public Join join(String toTable) {
        return join("INNER", toTable, toTable + "_id", "id");
    }

    public Join join(String toTable, String fromColumn, String toColumn) {
        return join("INNER", toTable, fromColumn, toColumn);
    }

    public Join join(String joinType, String toTable) {
        return join(joinType, toTable, toTable + "_id", "id");
    }

    public abstract Join join(String joinType, String toTable, String fromColumn, String toColumn);

    public Clause getClause(String clauseType) {
        return clauses.get(clauseType);
    }

    public void setWhere(Condition clause) {
        clauses.put(" WHERE ",clause);
    }

    public String getTableName() {
        return tableName;
    }
}
