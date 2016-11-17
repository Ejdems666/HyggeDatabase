package tests;

import hyggedb.select.GroupBy;
import hyggedb.select.Selection;
import hyggedb.select.Where;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class ClausesTestCase extends TestCase {
    private Selection selection;

    @Test
    public void testColumnClause() {
        selection = new Selection("user","");
        assertEquals("",selection.getClause("SELECT ").getClause());
    }

    @Override
    public void setUp() {
        selection = new Selection("user");
    }

    @Test
    public void testLimitClause() {
        setUp();
        assertNull(selection.getLimit());
        selection.limit(10,20);
        assertEquals(" LIMIT 10,20",selection.getLimit().getClause());
    }

    @Test
    public void testWhereClause() {
        setUp();
        assertNull(selection.getClause(" WHERE "));
        Where where = selection.where("name=?",3);
        assertEquals("user.name=?",where.getClause());
        assertEquals(3,Integer.parseInt(where.getValues().get(0)));
    }

    @Test
    public void testGroupByClause() {
        setUp();
        assertNull(selection.getClause(" GROUP BY "));
        GroupBy groupBy = selection.groupBy("name ");
        assertEquals("user.name",groupBy.getClause());
        groupBy = selection.groupBy(" name ,id ");
        assertEquals("user.name,user.id",groupBy.getClause());
    }

    @Test
    public void testOrderByClause() {
        setUp();
        assertNull(selection.getClause(" ORDER BY "));
        GroupBy groupBy = selection.groupBy("name ");
        assertEquals("user.name",groupBy.getClause());
        groupBy = selection.groupBy(" name ASC, id DESC");
        assertEquals("user.name ASC,user.id DESC",groupBy.getClause());
    }
}
