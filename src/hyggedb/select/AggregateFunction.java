package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class AggregateFunction {
    private String function;
    private String parameters;
    private String alias;

    public AggregateFunction(String function, String parameters, String alias) {
        this.function = function.trim();
        this.parameters = parameters;
        this.alias = alias.trim();
    }

    public String getFunction() {
        return new StringBuilder()
                .append(function).append("(")
                .append(parameters)
                .append(")")
                .append(" AS ").append(alias).toString();
    }

    public String getAlias() {
        return alias;
    }
}
