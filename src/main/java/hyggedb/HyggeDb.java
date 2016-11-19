package hyggedb;

import hyggedb.select.SelectQuery;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class HyggeDb {
    private final Connector dbConnector;

    public HyggeDb() throws Exception {
        dbConnector = new MySQLConnector();
    }

    public void close() {
        dbConnector.close();
    }

    public SelectQuery getSelectQuery() {
        return new SelectQuery(dbConnector.getConnection());
    }
}
