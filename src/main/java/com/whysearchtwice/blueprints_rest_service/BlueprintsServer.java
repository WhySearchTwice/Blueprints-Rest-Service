package com.whysearchtwice.blueprints_rest_service;

import java.io.IOException;

import com.whysearchtwice.blueprints_rest_service.graph_interactions.TitanConnector;
import com.whysearchtwice.blueprints_rest_service.jersey.JerseyServer;

/**
 * Main executable class that creates the graph and passes it to the RESTful web
 * server. Can either use a local graph or a graph in Cassandra. Supports
 * loading XML into graph for test cases.
 * 
 * @author Tony Grosinger
 */
public class BlueprintsServer {
    /**
     * The connection to the database created by the constructor
     */
    private static TitanConnector conn;

    /**
     * The web server created by the constructor
     */
    private static JerseyServer webserver;

    /**
     * Constructor that will start the service using a file backend
     * 
     * @param baseUrl
     *            Address which should be used as the base url for the web
     *            server
     * @param portNumber
     *            Port number the web server should run on
     * @param database
     *            The file which should be used as the backend
     */
    public BlueprintsServer(String baseUrl, int portNumber, String database) {
        // Create the titan graph
        if (database == null) {
            conn = new TitanConnector();
        } else {
            conn = new TitanConnector("/tmp/titan");
        }

        // Create the web server
        webserver = new JerseyServer("http://localhost/", 8080);

        // Start the web server
        try {
            webserver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor that will start the service using the Cassandra backend
     * 
     * @param baseUrl
     *            Address which should be used as the base url for the web
     *            server
     * @param portNumber
     *            Port number the web server should run on
     */
    public BlueprintsServer(String baseUrl, int portNumber) {
        this(baseUrl, portNumber, null);
    }

    /**
     * This is a factory method which will create an instance of itself using
     * the parameters in the arguments
     * 
     * @param args
     *            Should contain two or three arguments containing the baseUrl,
     *            port, and optionally the database file to use
     */
    public static void main(String[] args) {
        BlueprintsServer server = null;

        if (args.length == 3) {
            int portNumber = Integer.parseInt(args[1]);
            server = new BlueprintsServer(args[0], portNumber, args[2]);
        } else if (args.length == 2) {
            int portNumber = Integer.parseInt(args[1]);
            server = new BlueprintsServer(args[0], portNumber);
        } else {
            System.out.println("Invalid arguments. Expecting <baseUrl> <portNumber> <titanDir> with last param optional");
        }

        // Wait for the user to press enter before stopping the web server
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.shutdown();
    }

    /**
     * Load the data from an XML file into the Titan graph.
     * 
     * @param filename
     *            Location of the file to be loaded into the graph
     */
    public void loadXmlData(String filename) {
        conn.loadXmlData(filename);
    }

    /**
     * Shuts down the web server after it has been created and started
     */
    public void shutdown() {
        try {
            webserver.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
