package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class GroupBy extends ClauseAssembler implements Clause {
    public GroupBy(String alias, Function function) {
        super(alias);
        clause.append(function.getAlias());
    }
    public GroupBy(String alias, String[] elements) {
        super(alias);
        appendClause(elements[0]);
        for (int i = 1; i < elements.length; i++) {
            add(elements[i]);
        }
    }

    public GroupBy add(String element) {
        clause.append(",");
        appendClause(element);
        return this;
    }

    public GroupBy add(Function function) {
        clause.append(",");
        clause.append(function.getAlias());
        return null;
    }
}
