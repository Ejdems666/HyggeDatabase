
import hyggedb.select.Join;
import hyggedb.select.SelectionAssembler;
import hyggedb.select.Selection;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Ejdems on 18/11/2016.
 */
public class SelectionAssemblerTestCase extends HyggeDbTestCase {
    private Selection selection;
    private SelectionAssembler selectionAssembler;

    @Test
    public void testSelectAll() {
        selection = new Selection("user");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT * FROM user", selectionAssembler.assemble());
    }

    @Test
    public void testSelectAllFromMainTable() {
        selection = new Selection("user","*");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT user.* FROM user", selectionAssembler.assemble());
    }

    @Test
    public void testSelectSomeFromMainTable() {
        selection = new Selection("user"," id");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT user.id FROM user", selectionAssembler.assemble());
    }
    @Test
    public void testSelectSomeFromMainTableUsingArray() {
        selection = new Selection("user",new String[]{"id "," name"});
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT user.id,user.name FROM user", selectionAssembler.assemble());
    }

    @Test
    public void testSelectAllFromJoinTable() {
        selection = new Selection("user","");
        selection.join("job","irregular_id","id").addColumns("*");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT job.* FROM user INNER JOIN job ON user.irregular_id = job.id", selectionAssembler.assemble());
    }

    @Test
    public void testGeneratedJoin() {
        selection = new Selection("user");
        selection.join("LEFT","job");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals("SELECT * FROM user LEFT JOIN job ON user.job_id = job.id", selectionAssembler.assemble());
    }

    @Test
    public void testGeneratedMtoNJoin() {
        selection = new Selection("user");
        selection.join("job_user","id","user_id").join("job");
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals(
                "SELECT * FROM user " +
                        "INNER JOIN job_user ON user.id = job_user.user_id " +
                        "INNER JOIN job ON job_user.job_id = job.id",
                selectionAssembler.assemble()
        );
    }

    @Test
    public void testMtoNJoinWithFilters() {
        selection = new Selection("user");
        Join job_user = selection.join("job_user","id","user_id");
        job_user.where("accepted!=?","yesterday");
        // andWhere() or orWhere() is necessary here
        job_user.join("job").andWhere("name LIKE ?","%super%").and("salary>?",5000);
        selectionAssembler = new SelectionAssembler(selection);
        assertEquals(
                "SELECT * FROM user " +
                        "INNER JOIN job_user ON user.id = job_user.user_id " +
                        "INNER JOIN job ON job_user.job_id = job.id " +
                        "WHERE job_user.accepted!=? AND job.name LIKE ? AND job.salary>?",
                selectionAssembler.assemble()
        );
    }
}
