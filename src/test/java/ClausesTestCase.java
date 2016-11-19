
import hyggedb.select.Function;
import hyggedb.select.GroupBy;
import hyggedb.select.Selection;
import hyggedb.select.Condition;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

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
        assertNull(selection.getLimit());
        selection.limit(10,20);
        assertEquals(" LIMIT 10,20",selection.getLimit().getClause());
    }

    @Test
    public void testCondition(){
        assertNull(selection.getClause(" WHERE "));
        Condition where = selection.where("name!=?",3).and("id>?",6).or("id<?",2);
        assertEquals("user.name!=? AND user.id>? OR user.id<?",where.getClause());
        assertEqualValues(new String[]{"3","6","2"},where);
    }

    @Test
    public void testConditionWithFunction(){
        assertNull(selection.getClause(" HAVING "));
        Function sum = new Function("sum","id","sum");
        Condition having = selection.having(sum,">?",3).and(sum,"<?",6);
        assertEquals("sum>? AND sum<?",having.getClause());
        assertEqualValues(new String[]{"3","6"},having);
    }

    private void assertEqualValues(String[] expected, Condition condition) {
        ArrayList<Object> actual = condition.getValues();
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],actual.get(i).toString());
        }
    }

    @Test
    public void testGroupByClause() {
        assertNull(selection.getClause(" GROUP BY "));
        GroupBy groupBy = selection.groupBy("name ");
        assertEquals("user.name",groupBy.getClause());
        groupBy = selection.groupBy(" name ,id ");
        assertEquals("user.name,user.id",groupBy.getClause());
    }

    @Test
    public void testOrderByClause() {
        assertNull(selection.getClause(" ORDER BY "));
        GroupBy groupBy = selection.groupBy("name ");
        assertEquals("user.name",groupBy.getClause());
        groupBy = selection.groupBy(" name ASC, id DESC");
        assertEquals("user.name ASC,user.id DESC",groupBy.getClause());
    }
}
