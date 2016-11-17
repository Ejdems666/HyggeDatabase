package hyggedb.select;

import java.util.ArrayList;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Where extends ClauseAssembler implements Clause {
    private ArrayList<String> values = new ArrayList<>();

    public Where(String alias, String clause, String value) {
        super(alias);
        appendClause(clause);
        values.add("*"+value);
    }
    public Where(String alias, String clause, Integer value) {
        super(alias);
        appendClause(clause);
        values.add(value.toString());
    }

    public void or(String clause, String value) {
        this.clause.append(" OR ");
        appendClause(clause);
        values.add("*"+value);
    }
    public void and(String clause, String value) {
        this.clause.append(" AND ");
        appendClause(clause);
        values.add("*"+value);
    }
    public void or(String clause, Integer value) {
        this.clause.append(" OR ");
        appendClause(clause);
        values.add(value.toString());
    }
    public void and(String clause, Integer value) {
        this.clause.append(" AND ");
        appendClause(clause);
        values.add("*"+value);
    }

    public ArrayList<String> getValues() {
        return values;
    }
}
