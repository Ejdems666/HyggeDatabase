package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class OrderBy extends ClauseAssembler implements Clause {
    public OrderBy(String alias, String[] clauses) {
        super(alias);
        appendClause(clauses[0]);
        for (int i = 1; i < clauses.length; i++) {
            add(clauses[i]);
        }
    }

    public Clause add(String clause) {
        this.clause.append(",");
        appendClause(clause);
        return this;
    }
}
