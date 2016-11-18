package tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Ejdems on 17/11/2016.
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ClausesTestCase.class,QueryAssemblerTestCase.class);

        for (Failure failure : result.getFailures()) {
            System.out.println("failure: " + failure.toString());
        }

        System.out.println("test success: " + result.wasSuccessful());
    }
}
