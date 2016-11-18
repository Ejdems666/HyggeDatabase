package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Selection extends TableQuery{
    private ArrayList<Join> joins = new ArrayList<>();
    private Clause limit = null;
    private Condition having = null;

    public Selection(String tableName) {
        super(tableName);
        clauses.put("SELECT ",new Column(tableName,"*"));
    }
    public Selection(String tableName, Function function) {
        super(tableName);
        clauses.put("SELECT ", new Column(tableName, function));
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
    public Column addColumns(Function function) {
        return ((Column) clauses.get("SELECT ")).add(function);
    }


    public Join join(String toTable) {
        return join("INNER",toTable,toTable+"_id","id");
    }
    public Join join(String toTable, String fromColumn, String toColumn) {
        return join("INNER",toTable,fromColumn,toColumn);
    }
    public Join join(String joinType, String toTable) {
        return join(joinType,toTable,toTable+"_id","id");
    }
    public Join join(String joinType, String toTable, String fromColumn, String toColumn) {
        Join join =  new Join(
                joinType + " JOIN",
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
