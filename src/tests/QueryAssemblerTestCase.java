package tests;

import hyggedb.select.QueryAssembler;
import hyggedb.select.Selection;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Ejdems on 18/11/2016.
 */
public class QueryAssemblerTestCase extends TestCase {
    private Selection selection;
    private QueryAssembler queryAssembler;

    @Test
    public void testSelectAll() {
        selection = new Selection("user");
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT * FROM user",queryAssembler.assemble());
    }

    @Test
    public void testSelectAllFromMainTable() {
        selection = new Selection("user","*");
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT user.* FROM user",queryAssembler.assemble());
    }

    @Test
    public void testSelectSomeFromMainTable() {
        selection = new Selection("user"," id");
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT user.id FROM user",queryAssembler.assemble());
    }
    @Test
    public void testSelectSomeFromMainTableUsingArray() {
        selection = new Selection("user",new String[]{"id "," name"});
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT user.id,user.name FROM user",queryAssembler.assemble());
    }

    @Test
    public void testSelectAllFromJoinTable() {
        selection = new Selection("user","");
        selection.join("job","irregular_id","id").addColumns("*");
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT job.* FROM user INNER JOIN job ON user.irregular_id = job.id",queryAssembler.assemble());
    }

    @Test
    public void testGeneratedJoin() {
        selection = new Selection("user");
        selection.join("LEFT","job");
        queryAssembler = new QueryAssembler(selection);
        assertEquals("SELECT * FROM user LEFT JOIN job ON user.job_id = job.id",queryAssembler.assemble());
    }
}
