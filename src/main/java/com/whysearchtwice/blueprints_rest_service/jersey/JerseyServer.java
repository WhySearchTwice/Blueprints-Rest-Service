package com.whysearchtwice.blueprints_rest_service.jersey;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.whysearchtwice.blueprints_rest_service.graph_interactions.TitanConnector;

/**
 * Web server using Jersey to make Titan graph accessible through REST
 * end-points. Designed to be interchangeable with any REST library.
 * 
 * @author Tony Grosinger
 */
public class JerseyServer {
    /**
     * Location where the server will be accessible
     */
    private String baseUrl;

    /**
     * The port to run the web server on.
     */
    private int portNumber;

    /**
     * The webserver which will be built using the baseUrl and portNumber
     */
    private HttpServer webserver;

    /**
     * The connection used by the REST endpoints to interact with the graph
     */
    public static TitanConnector connection;

    /**
     * Logger for this class
     */
    private static Log log;

    /**
     * Constructor to specify the baseUrl and portNumber before the server is
     * started.
     */
    public JerseyServer(String baseUrl, int portNumber, TitanConnector conn) {
        // Setup Logging
        log = LogFactory.getLog(JerseyServer.class);

        this.baseUrl = baseUrl;
        this.portNumber = portNumber;
        JerseyServer.connection = conn;

        // Create a new server using this new-found information
        createServer();
    }

    private HttpServer createServer() {
        log.debug("Creating grizzly server");

        ResourceConfig rc = new PackagesResourceConfig("com.whysearchtwice.blueprints_rest_service.jersey");
        URI baseUri = UriBuilder.fromUri(baseUrl).port(portNumber).build();

        try {
            this.webserver = GrizzlyServerFactory.createHttpServer(baseUri, rc);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Once web server has been created, this method will start it
     * 
     * @throws Exception
     */
    public void start() throws Exception {
        if (webserver == null || !(webserver instanceof HttpServer)) {
            log.error("Web server has not been initialized");
            throw new Exception("Web server has not been initialized");
        }

        log.debug("Starting the web server");
        this.webserver.start();
        log.debug("Web server started");
    }

    /**
     * Once the web server has been created, this method will stop it
     * 
     * @throws Exception
     */
    public void stop() throws Exception {
        if (webserver == null || !(webserver instanceof HttpServer)) {
            throw new Exception("Web server has not been initialized");
        }

        log.debug("Stopping the web server");
        this.webserver.stop();
        log.debug("Web server stopped");
    }
}
