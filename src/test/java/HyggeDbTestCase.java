import hyggedb.select.Condition;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by Ejdems on 19/11/2016.
 */
public abstract class HyggeDbTestCase extends TestCase{
    protected void assertEqualValues(String[] expected, Object[] actual) {
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],actual[i].toString());
        }
    }

}
