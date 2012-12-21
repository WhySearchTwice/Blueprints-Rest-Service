package com.whysearchtwice.BlueprintsRestService;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test to check the function of all the endpoints
 * 
 * @author Tony Grosinger
 */
public class EndpointsTest extends TestCase {
    /**
     * Default constructor needed to create the test case
     * 
     * @param testName
     *            name of the test case
     */
    public EndpointsTest(String testName) {
        super(testName);
    }

    /**
     * The TestSuite determines what tests will be run. This function defaults
     * to discovering all functions within this class.
     * 
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(EndpointsTest.class);

    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        System.out.println("Setup");
    }
    
    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    public void tearDown() {
        System.out.println("Teardown");
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
        System.out.println("Something!");
    }
}
