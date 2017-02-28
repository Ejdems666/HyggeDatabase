package hyggedb.select;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Condition extends ClauseAssembler implements Clause {
    protected ArrayList<Object> values = new ArrayList<>();

    public Condition(String prefix,String alias, String clause, Collection<Object> values) {
        super(alias);
        this.clause.append(prefix);
        appendInClause(clause,values);
    }
    public Condition(String prefix,String alias, String clause, Object value) {
        super(alias);
        this.clause.append(prefix);
        appendClause(clause);
        values.add(value);
    }
    public Condition(String prefix, String alias, Function function, String operator, Object value) {
        super(alias);
        this.clause.append(prefix);
        clause.append(function.getAlias()).append(operator);
        values.add(value);
    }
    public Condition(String alias, String clause, Object value) {
        this("",alias,clause,value);
    }
    public Condition(String alias, Function function, String operator, Object value) {
        this("",alias,function,operator,value);
    }
    public Condition(String alias, String clause, Collection<Object> values) {
        this("",alias,clause,values);
    }

    public Condition or(String clause, Object value) {
        this.clause.append(" OR ");
        appendClause(clause);
        values.add(value);
        return this;
    }
    public Condition and(String clause, Object value) {
        this.clause.append(" AND ");
        appendClause(clause);
        values.add(value);
        return this;
    }

    public Condition or(Condition condition) {
        this.clause.append(" OR ");
        clause.append(condition.getClause());
        values.addAll(condition.getValues());
        return this;
    }
    public Condition and(Condition condition) {
        this.clause.append(" AND ");
        clause.append(condition.getClause());
        values.addAll(condition.getValues());
        return this;
    }

    public Condition or(Function function, String operator, Object value) {
        clause.append(" OR ");
        clause.append(function.getAlias()).append(operator);
        values.add(value);
        return this;
    }
    public Condition and(Function function, String operator, Object value) {
        clause.append(" AND ");
        clause.append(function.getAlias()).append(operator);
        values.add(value);
        return this;
    }

    public Condition or(String clause, Collection<Object> values) {
        this.clause.append(" OR ");
        appendInClause(clause, values);
        return this;
    }

    private void appendInClause(String clause, Collection<Object> values) {
        appendClause(clause);
        this.clause.append(" IN (");
        for (int i = 0; i < values.size(); i++) {
            this.clause.append("?");
            if (i != values.size()-1) {
                this.clause.append(",");
            }
        }
        this.clause.append(")");
        this.values.addAll(values);
    }

    public Condition and(String clause, Collection<Object> values) {
        this.clause.append(" AND ");
        appendInClause(clause, values);
        return this;
    }

    public ArrayList<Object> getValues() {
        return values;
    }
}
