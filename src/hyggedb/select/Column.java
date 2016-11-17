package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Column extends ClauseAssembler implements Clause {
    private ArrayList<String> columns = new ArrayList<>();

    public Column(String alias,AggregateFunction function) {
        super(alias);
        addAggregateFunction(function);
    }

    public Column(String alias,String column) {
        super(alias);
        column = column.trim();
        if(column.equals("*")) {
            clause.append(column);
        } else if(!column.isEmpty()){
            this.columns.add(column);
            appendClause(column);
        }
    }

    public Column(String alias,String[] columns) {
        super(alias);
        this.columns.add(columns[0].trim());
        appendClause(columns[0]);
        for (int i = 1; i < columns.length; i++) {
            add(columns[i]);
        }
    }

    public Column add(String element) {
        columns.add(element.trim());
        clause.append(",");
        appendClause(element);
        return this;
    }

    public Column add(AggregateFunction function) {
        clause.append(",");
        addAggregateFunction(function);
        return this;
    }
    private void addAggregateFunction(AggregateFunction function) {
        columns.add(function.getAlias());
        clause.append(function.getFunction());
    }
}
