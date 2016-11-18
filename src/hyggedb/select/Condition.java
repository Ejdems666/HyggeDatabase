package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Condition extends ClauseAssembler implements Clause {
    protected ArrayList<String> values = new ArrayList<>();

    public Condition(String alias, String clause, String value) {
        super(alias);
        appendClause(clause);
        values.add("*"+value);
    }
    public Condition(String alias, String clause, Integer value) {
        super(alias);
        appendClause(clause);
        values.add(value.toString());
    }

    public Condition or(String clause, String value) {
        this.clause.append(" OR ");
        appendClause(clause);
        values.add("*"+value);
        return this;
    }
    public Condition and(String clause, String value) {
        this.clause.append(" AND ");
        appendClause(clause);
        values.add("*"+value);
        return this;
    }
    public Condition or(String clause, Integer value) {
        this.clause.append(" OR ");
        appendClause(clause);
        values.add(value.toString());
        return this;
    }
    public Condition and(String clause, Integer value) {
        this.clause.append(" AND ");
        appendClause(clause);
        values.add(value.toString());
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

    public ArrayList<String> getValues() {
        return values;
    }
}
