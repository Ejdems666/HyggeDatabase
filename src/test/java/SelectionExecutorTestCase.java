import hyggedb.HyggeDb;
import hyggedb.select.Function;
import hyggedb.select.Selection;
import hyggedb.select.SelectionExecutor;
import org.junit.Test;

/**
 * Created by Ejdems on 19/11/2016.
 */
public class SelectionExecutorTestCase extends HyggeDbTestCase {
    private Selection selection;
    private SelectionExecutor executor;
    private HyggeDb db;

    public void setUp() throws Exception {
        db = new HyggeDb();
        executor = db.getSelectionExecutor();
    }

    @Test
    public void testEmptyColumns() {
        selection = new Selection("user");
        assertEqualValues(new String[]{},executor.getAllColumns(selection).toArray());
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
}
