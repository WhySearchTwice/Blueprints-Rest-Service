package com.whysearchtwice.BlueprintsRestService;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

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
        HttpMethod method = new GetMethod("http://localhost:8080/user/tony@grosinger.net");
        
        // Get response code
        int responseCode = client.executeMethod(method);
        assertTrue(responseCode == 200);
        
        // Get response body
        InputStream responseBodyStream = method.getResponseBodyAsStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(responseBodyStream, writer);
        String response = writer.toString();
        assertTrue(response.equals("1234567890"));
        
    }
}
