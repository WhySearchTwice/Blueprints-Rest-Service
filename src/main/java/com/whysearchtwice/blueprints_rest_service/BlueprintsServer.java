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
    public static void main(String[] args) {
        // Create the titan graph
        TitanConnector conn = new TitanConnector("/tmp/titan");

        JerseyServer webserver = new JerseyServer("http://localhost/", 8080);

        try {
            webserver.start();

            // Wait for the user to press enter before stopping the web server
            System.in.read();

            webserver.stop();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
