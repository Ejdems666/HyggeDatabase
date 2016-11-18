package hyggedb.select;

import java.util.HashMap;

/**
 * Created by Ejdems on 17/11/2016.
 */
public abstract class TableQuery {
    protected String tableName;
    protected HashMap<String,Clause> clauses = new HashMap<>();

    public TableQuery(String tableName) {
        this.tableName = tableName;
    }

    public GroupBy groupBy(String clause) {
        return groupBy(clause.split(","));
    }
    public GroupBy groupBy(String[] clauses) {
        GroupBy groupBy = new GroupBy(tableName, clauses);
        this.clauses.put(" GROUP BY ",groupBy);
        return groupBy;
    }

    public OrderBy orderBy(String clause) {
        return orderBy(clause.split(","));
    }
    public OrderBy orderBy(String[] clauses) {
        OrderBy orderBy = new OrderBy(tableName, clauses);
        this.clauses.put(" ORDER BY ",orderBy);
        return orderBy;
    }

    public Condition where(String clause, Object value) {
        Condition where = new Condition(tableName, clause, value);
        clauses.put(" WHERE ",where);
        return where;
    }
    public Condition where(Function function, String operator, Object value) {
        Condition where = new Condition(tableName, function, operator, value);
        clauses.put(" WHERE ",where);
        return where;
    }

    public Condition having(String clause, Object value) {
        Condition having = new Condition(tableName, clause, value);
        clauses.put(" HAVING ",having);
        return having;
    }
    public Condition having(Function function, String operator, Object value) {
        Condition having = new Condition(tableName, function, operator, value);
        clauses.put(" HAVING ",having);
        return having;
    }

    public Clause getClause(String clauseType) {
        return clauses.get(clauseType);
    }
}
