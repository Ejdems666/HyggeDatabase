package hyggedb;

import hyggedb.select.SelectionExecutor;

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

    public SelectionExecutor getSelectionExecutor() {
        return new SelectionExecutor(dbConnector.getConnection());
    }
}
