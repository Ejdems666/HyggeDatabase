
import hyggedb.select.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class ClausesTestCase extends HyggeDbTestCase {
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
        assertEqualValues(new String[]{"3","6","2"}, where.getValues().toArray());
    }

    @Test
    public void testConditionWithFunction(){
        assertNull(selection.getClause(" HAVING "));
        Function sum = new Function("sum","id","sum");
        Condition having = selection.having(sum,">?",3).and(sum,"<?",6);
        assertEquals("sum>? AND sum<?",having.getClause());
        assertEqualValues(new String[]{"3","6"},having.getValues().toArray());
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
    public void testGroupByWithFunction() {
        Function sum = new Function("sum","id","sum");
        GroupBy groupBy = selection.groupBy(sum);
        assertEquals("sum",groupBy.getClause());
        groupBy.add(sum);
        assertEquals("sum,sum",groupBy.getClause());
    }

    @Test
    public void testOrderByClause() {
        assertNull(selection.getClause(" ORDER BY "));
        GroupBy groupBy = selection.groupBy("name ");
        assertEquals("user.name",groupBy.getClause());
        groupBy = selection.groupBy(" name ASC, id DESC");
        assertEquals("user.name ASC,user.id DESC",groupBy.getClause());
    }

    @Test
    public void testOrderByWithFunction() {
        Function sum = new Function("sum","id","sum");
        OrderBy orderBy = selection.orderBy(sum,"ASC");
        assertEquals("sum ASC",orderBy.getClause());
        orderBy.add(sum,"DESC");
        assertEquals("sum ASC,sum DESC",orderBy.getClause());
    }
}
