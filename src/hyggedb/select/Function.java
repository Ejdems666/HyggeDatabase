package hyggedb.select;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class Function{
    private String function;
    private String parameters;
    private String alias;

    public Function(String function, String parameters, String alias) {
        this.function = function;
        this.parameters = parameters;
        this.alias = alias;
    }

    public String getFunction() {
        return function + "(" + parameters + ")" + " AS " + alias;
    }

    public String getAlias() {
        return alias;
    }
}
