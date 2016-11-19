package hyggedb.select;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Join extends TableQuery implements Clause{
    private String joinType;
    private String fromColumn;
    private String toColumn;
    private Selection baseSelection;

    public Join(Selection baseSelection, String joinType, String toTable, String fromColumn, String toColumn) {
        super(toTable);
        this.baseSelection = baseSelection;
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

    public Condition andWhere(String clause, Object value) {
        return createCondition(" AND "," WHERE ",clause, value);
    }
    public Condition andWhere(Function function, String operator, Object value) {
        return createCondition(" AND "," WHERE ",function, operator, value);
    }
    public Condition orWhere(String clause, Object value) {
        return createCondition(" OR "," WHERE ",clause, value);
    }
    public Condition orWhere(Function function, String operator, Object value) {
        return createCondition(" OR ", " WHERE ",function, operator, value);
    }
    public Condition andHaving(String clause, Object value) {
        return createCondition(" AND "," HAVING ",clause, value);
    }
    public Condition andHaving(Function function, String operator, Object value) {
        return createCondition(" AND "," HAVING ",function, operator, value);
    }
    public Condition orHaving(String clause, Object value) {
        return createCondition(" OR "," HAVING ",clause, value);
    }
    public Condition orHaving(Function function, String operator, Object value) {
        return createCondition(" OR ", " HAVING ",function, operator, value);
    }

    @Override
    public Join join(String joinType, String toTable, String fromColumn, String toColumn) {
        Join join =  new Join(
                baseSelection,
                joinType + " JOIN",
                toTable,
                tableName + "." + fromColumn,
                toColumn
        );
        baseSelection.addJoin(join);
        return join;
    }

    @Override
    public String getClause() {
        StringBuilder sql = new StringBuilder();
        sql.append(" ").append(joinType).append(" ")
                .append(tableName)
                .append(" ON ")
                .append(fromColumn)
                .append(" = ")
                .append(tableName).append(".").append(toColumn);
        return sql.toString();
    }
}
