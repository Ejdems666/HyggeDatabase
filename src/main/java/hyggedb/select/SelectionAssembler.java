package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class SelectionAssembler {
    private StringBuilder sql;
    private Selection selection;

    public SelectionAssembler(Selection selection) {
        this.selection = selection;
        sql = new StringBuilder();
    }

    public String assemble() {
        appendClause("SELECT ",",");
        sql.append(" FROM ").append(selection.getTableName());
        appendJoinClauses();
        appendClause(" WHERE ","");
        appendClause(" GROUP BY ",",");
        appendClause(" HAVING ","");
        appendClause(" ORDER BY ",",");
        appendLimit();
        return sql.toString();
    }
    private void appendClause(String clauseType, String separator) {
        StringBuilder clause = new StringBuilder();
        if(selection.getClause(clauseType) != null) {
            clause.append(selection.getClause(clauseType).getClause());
        }
        for (Join join : selection.getJoins()) {
            if (join.getClause(clauseType) != null) {
                if (clause.length() > 0) clause.append(separator);
                clause.append(join.getClause(clauseType).getClause());
            }
        }
        if (clause.length() > 0) {
            sql.append(clauseType).append(clause);
        }
    }
    private void appendJoinClauses() {
        for (Join join : selection.getJoins()) {
            sql.append(join.getClause());
        }
    }

    private void appendLimit() {
        if(selection.getLimit() != null) {
            sql.append(selection.getLimit().getClause());
        }
    }
}
