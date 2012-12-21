package com.whysearchtwice.blueprints_rest_service.jersey;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

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
     * Constructor to specify the baseUrl and portNumber before the server is
     * started.
     */
    public JerseyServer(String baseUrl, int portNumber) {
        this.baseUrl = baseUrl;
        this.portNumber = portNumber;

        // Create a new server using this new-found information
        createServer();
    }

    private HttpServer createServer() {
        System.out.println("Creating grizzly server");

        ResourceConfig rc = new PackagesResourceConfig("com.whysearchtwice.BlueprintsRestService");
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
            throw new Exception("Web server has not been initialized");
        }

        System.out.println("Starting the web server");
        this.webserver.start();
        System.out.println("Web server started");
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

        System.out.println("Stopping the web server");
        this.webserver.stop();
        System.out.println("Web server stopped");
    }
}
