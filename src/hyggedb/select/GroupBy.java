package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class GroupBy extends ClauseAssembler implements Clause {
    public GroupBy(String alias, String[] clauses) {
        super(alias);
        appendClause(clauses[0]);
        for (int i = 1; i < clauses.length; i++) {
            add(clauses[i]);
        }
    }

    public GroupBy add(String element) {
        clause.append(",");
        appendClause(element);
        return this;
    }
}
