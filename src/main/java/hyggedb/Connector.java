package hyggedb;

/**
 * Created by Ejdems on 16/11/2016.
 */
public interface Connector {
    public java.sql.Connection getConnection();
    public void close();
}
