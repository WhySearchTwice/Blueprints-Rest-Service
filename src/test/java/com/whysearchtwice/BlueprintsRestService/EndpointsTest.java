package com.whysearchtwice.BlueprintsRestService;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import com.whysearchtwice.blueprints_rest_service.BlueprintsServer;

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
     * Storage for the server that will be tested
     */
    BlueprintsServer server;

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

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    public void setUp() {
        server = new BlueprintsServer("http://localhost/", 8080, "/tmp/titan");
        server.loadXmlData("oneHourTestData.xml");
    }

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#tearDown()
     */
    public void tearDown() {
        server.shutdown();
    }

    /**
     * Rigourous Test :-)
     * 
     * @throws IOException
     * @throws HttpException
     */
    public void testApp() throws HttpException, IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://localhost:8080/user/anEmailAddress");
        int responseCode = client.executeMethod(method);
        assertTrue(responseCode == 200);
    }
}
