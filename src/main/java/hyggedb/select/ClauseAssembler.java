package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public abstract class ClauseAssembler {
    protected String alias;
    protected StringBuilder clause;

    public ClauseAssembler(String alias) {
        this.alias = alias;
        clause = new StringBuilder();
    }

    protected void appendClause(String clause) {
        if (!alias.isEmpty()) this.clause.append(alias).append(".");
        this.clause.append(clause.trim());
    }

    public String getClause() {
        return clause.toString();
    }
}
