package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Limit implements Clause {
    private final int from;
    private final int amount;

    public Limit(int from, int amount) {
        this.from = from;
        this.amount = amount;
    }

    @Override
    public String getClause() {
        StringBuilder limit = new StringBuilder();
        limit.append(" LIMIT ").append(from).append(",").append(amount);
        return limit.toString();
    }
}
