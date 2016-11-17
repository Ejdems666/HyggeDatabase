package examples;

import hyggedb.HyggeDb;
import hyggedb.select.Function;
import hyggedb.select.Join;
import hyggedb.select.Query;
import hyggedb.select.Selection;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Ejdems on 16/11/2016.
 */
public class Selects {
    public static void main(String[] args) {
        HyggeDb db = null;
        try {
            db = new HyggeDb();
            Query qb = db.getSelectQuery();
            Selection selection;

            // get
            selection = new Selection("user");
            printData(qb.getResult(selection));

            // Basic Query
            selection = new Selection("user");
            selection.where("id>?",3).and("id<?",6);
            printData(qb.getResult(selection));

            // Basic Query with LIKE
            selection = new Selection("user");
            selection.where("name LIKE ?","test%");
            printData(qb.getResult(selection));

            // More complicated example with aggregate function adn groupBy
            selection = new Selection("user", "name");
            selection.addColumns(new Function("sum","id","id"));
            selection.where("name!=?","tester");
            selection.groupBy("name");
            printData(qb.getResult(selection));

            // Example with join
            selection = new Selection("user","");
            // filtering ON JOINED TABLE, same API as selection
            Join join = selection.join("job","job_id","id");
            join.addColumns("name");
            join.where("name!=?","plumber");
            printJoinData(qb.getResult(selection));

            System.out.println("\n-----------------------------------------------------------------\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    private static void printJoinData(ResultSet rs) throws SQLException {
        System.out.println("\n-----------------------------------------------------------------\n");
        while (rs.next()) {
//            System.out.println("name: " + rs.getString("name") + " job : " + rs.getString("job.name"));
            System.out.println("job : " + rs.getString("job.name"));
        }
    }

    private static void printData(ResultSet rs) throws SQLException {
        System.out.println("\n-----------------------------------------------------------------\n");
        while (rs.next()) {
            System.out.println("name: " + rs.getString("name") + " id: " + rs.getInt("id"));
        }
    }
}
