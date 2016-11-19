package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Condition extends ClauseAssembler implements Clause {
    protected ArrayList<Object> values = new ArrayList<>();

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

    // TODO: add IN() method (and() or() with list of values) and maybe between()

    public ArrayList<Object> getValues() {
        return values;
    }
}
