package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Selection extends TableQuery{
    private ArrayList<Join> joins = new ArrayList<>();
    private Clause limit = null;

    public Selection(String tableName) {
        super(tableName);
        clauses.put("SELECT ",new Column(tableName,"*"));
    }
    public Selection(String tableName, AggregateFunction aggregateFunction) {
        super(tableName);
        clauses.put("SELECT ", new Column(tableName, aggregateFunction));
    }
    public Selection(String tableName,String column) {
        super(tableName);
        if(column.isEmpty()) {
            clauses.put("SELECT ", new Column(tableName,""));
        } else {
            clauses.put("SELECT ", new Column(tableName, column.split(",")));
        }
    }
    public Selection(String tableName,String[] columns) {
        super(tableName);
        this.clauses.put("SELECT ", new Column(tableName,columns));
    }

    public Column addColumns(String columns) {
        return addColumns(columns.split(","));
    }
    public Column addColumns(String[] columns) {
        Column select = ((Column) clauses.get("SELECT "));
        for (String column : columns) {
            select.add(column);
        }
        return select;
    }
    public Column addColumns(AggregateFunction function) {
        return ((Column) clauses.get("SELECT ")).add(function);
    }

    /**
     * ... INNER JOIN [toTable] ON baseTable.[fromColumn] = toTable.[toColumn]
     * @return Selection object of newly joined tableName: [tableName].
     * Here you can apply WHERE, GROUP BY, ORDER BY statements and ADD COLUMNS all specific to the joined table
     */
    public Join join(String toTable, String fromColumn, String toColumn) {
        return join(toTable,fromColumn,toColumn,"INNER JOIN");
    }
    /**
     * ... [joinType] [toTable] ON baseTable.[fromColumn] = toTable.[toColumn]
     * @return Selection object of newly joined tableName: [tableName].
     * Here you can apply WHERE, GROUP BY, ORDER BY statements and ADD COLUMNS all specific to the joined table
     */
    public Join join(String toTable, String fromColumn, String toColumn, String joinType) {
        Join join =  new Join(
                joinType,
                toTable,
                tableName + "." + fromColumn,
                toColumn
        );
        joins.add(join);
        return join;
    }

    public Selection limit(int from, int amount) {
        limit = new Limit(from,amount);
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Clause getLimit() {
        return limit;
    }

    public ArrayList<Join> getJoins() {
        return joins;
    }
}
