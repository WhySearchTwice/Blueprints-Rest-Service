package com.whysearchtwice.BlueprintsRestService;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

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
        server.shutdown(true);
    }

    /**
     * Retrieve the GUID for an known-existing user. Should return a JSON doc
     * containing the userguid
     * 
     * @throws IOException
     * @throws HttpException
     */
    public void testRetrieveGuidExisting() throws HttpException, IOException {
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
        assertTrue(response.equals("{\"userguid\": \"1234567890\"}"));

    }

    /**
     * Retrieve the GUID for an known-nonexisting user. Should return a new guid
     * 
     * @throws IOException
     * @throws HttpException
     */
    public void testRetrieveGuidNonexisting() throws HttpException, IOException {
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod("http://localhost:8080/user/I-Dont-Exist");

        // Get response code
        int responseCode = client.executeMethod(method);
        assertTrue("Unexpected response code", responseCode == 200);

        // Get response body
        InputStream responseBodyStream = method.getResponseBodyAsStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(responseBodyStream, writer);
        String response = writer.toString();
        assertTrue("Not a valid guid", response.length() == 52);

    }

    /**
     * Send a request to /page/open to log a new page being opened. Test this
     * has succeeded by retrieving the a timerange around this page.
     * 
     * @throws IOException
     * @throws HttpException
     */
    @SuppressWarnings({ "deprecation" })
    public void testOpenNewPage() throws HttpException, IOException {
        String testString = "{\"tabId\": 1, \"windowId\": 2, \"deviceGuid\": \"123\", \"pageOpenTime\": \"1356572763012\", \"userGuid\": \"1234567890\", \"url\": \"http://google.com\"}";

        HttpClient client = new HttpClient();
        HttpMethod postMethod = new PostMethod("http://localhost:8080/page/open");
        postMethod.setRequestHeader(new Header("Content-type", "application/json; charset=\"utf-8\""));
        ((PostMethod) postMethod).setRequestBody(testString);

        // Get response code
        int responseCode = client.executeMethod(postMethod);
        assertTrue("Invalid Response Code", responseCode == 200);

        // Get response body
        InputStream responseBodyStream = postMethod.getResponseBodyAsStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(responseBodyStream, writer);
        String response = writer.toString();
        assertTrue("Unexpected message returned from page open action", response.equals("{\"message\":\"Page view stored\"}"));

        // Test that the insert was successful by retrieving the time range
        HttpMethod getMethod = new GetMethod("/1234567890/timerange/start/1356572763000/stop/1356572823000");

        // Get response code
        responseCode = client.executeMethod(getMethod);
        assertTrue(responseCode == 200);

        // Get response body
        responseBodyStream = getMethod.getResponseBodyAsStream();
        writer = new StringWriter();
        IOUtils.copy(responseBodyStream, writer);
        response = writer.toString();

        // Convert string from JSON and check contents
        JSONArray jsonArr = (JSONArray) JSONValue.parse(response);
        assertTrue(jsonArr.size() == 1);
        System.out.println(jsonArr.get(1));
    }
}
