import hyggedb.HyggeDb;
import hyggedb.MySQLConnector;
import hyggedb.select.Condition;
import hyggedb.select.Function;
import hyggedb.select.Selection;
import hyggedb.select.SelectionExecutor;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Ejdems on 19/11/2016.
 */
public class SelectionExecutorTestCase{
    private Selection selection;
    private static SelectionExecutor executor;
    private static HyggeDb db;

    @BeforeClass
    public static void setUpClass() throws Exception {
        db = new HyggeDb(new MySQLConnector());
        executor = db.getSelectionExecutor();
    }

    @Test
    public void testEmptyColumns() {
        selection = new Selection("user");
        assertEqualValues(new String[]{},executor.getAllColumns(selection).toArray());
    }
    private void assertEqualValues(String[] expected, Object[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],actual[i].toString());
        }
    }

    @Test
    public void testAllFromSelectionColumns() {
        selection = new Selection("user","*");
        assertEqualValues(new String[]{},executor.getAllColumns(selection).toArray());
    }

    @Test
    public void testSpecificSelectionColumns() {
        selection = new Selection("user","id,name");
        assertEqualValues(new String[]{"user.id","user.name"},executor.getAllColumns(selection).toArray());
    }

    @Test
    public void testFunctionInSelectionsColumns() {
        Function function = new Function("sum","id","id_sum");
        selection = new Selection("user",function);
        assertEqualValues(new String[]{function.getAlias()},executor.getAllColumns(selection).toArray());
    }

    @Test
    public void testSpecificJoinsAndSelectionsColumns() {
        Function function = new Function("sum","id","id_sum");
        selection = new Selection("user","name");
        selection.join("job").addColumns("salary,name");
        assertEqualValues(new String[]{"user.name","job.salary","job.name"},executor.getAllColumns(selection).toArray());
    }

    @Test
    public void testInSelection() throws Exception {
            selection = new Selection("user");
            Collection<Object> inValues = new ArrayList<>();
            inValues.add(1);
            inValues.add(2);
            Condition where = selection.where("status",inValues);
            selection.orderBy("name");
            ResultSet rs = executor.getResult(selection);
            int i = 0;
            while (rs.next()) {
                assertEquals("tester"+i,rs.getString("name"));
                i++;
            }
    }
}
