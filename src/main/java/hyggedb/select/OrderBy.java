package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class OrderBy extends ClauseAssembler implements Clause {
    public OrderBy(String alias, Function function, String mode) {
        super(alias);
        clause.append(function.getAlias()).append(" ").append(mode);
    }
    public OrderBy(String alias, String[] clauses) {
        super(alias);
        appendClause(clauses[0]);
        for (int i = 1; i < clauses.length; i++) {
            add(clauses[i]);
        }
    }

    public OrderBy add(String element) {
        clause.append(",");
        appendClause(element);
        return this;
    }

    public OrderBy add(Function function, String mode) {
        clause.append(",");
        clause.append(function.getAlias()).append(" ").append(mode);
        return null;
    }
}
