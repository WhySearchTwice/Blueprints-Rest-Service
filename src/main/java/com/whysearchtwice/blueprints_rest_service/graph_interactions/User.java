package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class User {
    private TitanGraph graph;

    /**
     * Logger for this class
     */
    private static Log log;

    public User(TitanGraph graph) {
        // Setup Logging
        log = LogFactory.getLog(User.class);

        this.graph = graph;
    }

    public String getUserEmailByGuid(String guid) {
        log.debug("Retrieving username by guid: " + guid);

        Vertex v = graph.getVertices("userguid", guid).iterator().next();
        if (v != null) {
            return String.format("{\"username\": \"%s\"}", v.getProperty("username"));
        } else {
            return "No results found";
        }
    }

    public String getUserGuidByEmail(String email) {
        log.debug("Retrieving guid by email: " + email);

        Vertex v = graph.getVertices("username", email).iterator().next();
        if (v != null) {
            return String.format("{\"userguid\": \"%s\"}", v.getProperty("userguid"));
        } else {
            return "No results found";
        }
    }
}
