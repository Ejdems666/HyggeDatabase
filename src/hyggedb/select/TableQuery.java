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

    public Where where(String clause, String value) {
        Where where = new Where(tableName, clause, value);
        clauses.put(" WHERE ",where);
        return where;
    }

    public Where where(String clause, Integer value) {
        Where where = new Where(tableName, clause, value);
        clauses.put(" WHERE ",where);
        return where;
    }

    public Clause getClause(String clauseType) {
        return clauses.get(clauseType);
    }
}
