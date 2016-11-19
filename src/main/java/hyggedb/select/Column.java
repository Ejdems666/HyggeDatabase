package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Column extends ClauseAssembler implements Clause {
    private ArrayList<String> columns = new ArrayList<>();

    public Column(String alias,Function function) {
        super(alias);
        addAggregateFunction(function);
    }

    public Column(String alias,String column) {
        super(alias);
        column = column.trim();
        if(column.equals("*")) {
            clause.append(column);
        } else if(!column.isEmpty()){
            addToColumnList(column);
            appendClause(column);
        }
    }

    private void addToColumnList(String column) {
        this.columns.add(alias+"."+column);
    }

    public Column(String alias,String[] columns) {
        super(alias);
        addToColumnList(columns[0].trim());
        appendClause(columns[0]);
        for (int i = 1; i < columns.length; i++) {
            add(columns[i]);
        }
    }

    public Column add(String element) {
        addToColumnList(element.trim());
        clause.append(",");
        appendClause(element);
        return this;
    }

    public Column add(Function function) {
        clause.append(",");
        addAggregateFunction(function);
        return this;
    }
    private void addAggregateFunction(Function function) {
        columns.add(function.getAlias());
        clause.append(function.getFunction());
    }

    public ArrayList<String> getColumns() {
        return columns;
    }
}
