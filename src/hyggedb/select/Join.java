package hyggedb.select;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Join extends TableQuery implements Clause{
    private String joinType;
    private String fromColumn;
    private String toColumn;

    public Join(String joinType, String toTable, String fromColumn, String toColumn) {
        super(toTable);
        this.joinType = joinType;
        this.fromColumn = fromColumn;
        this.toColumn = toColumn;
    }

    public Column addColumns(String column) {
        Column select = new Column(tableName,column.split(","));
        clauses.put("SELECT ",select);
        return select;
    }
    public Column addColumns(String[] columns) {
        Column select = new Column(tableName,columns);
        clauses.put("SELECT ",select);
        return select;
    }
    public Column addColumns(Function function) {
        Column select = new Column(tableName,function);
        clauses.put("SELECT ",select);
        return select;
    }

    @Override
    public String getClause() {
        StringBuilder sql = new StringBuilder();
        sql.append(" ").append(joinType).append(" ")
                .append(tableName).append(" ")
                .append(" ON ")
                .append(fromColumn)
                .append(" = ")
                .append(tableName).append(".").append(toColumn);
        return sql.toString();
    }
}
