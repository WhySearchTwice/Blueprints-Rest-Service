package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import java.util.Iterator;
import java.util.UUID;

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

        Vertex v = graph.getVertex(guid);
        if (v != null) {
            return String.format("{\"username\": \"%s\"}", v.getProperty("username"));
        } else {
            return "No results found";
        }
    }

    public String getUserGuidByEmail(String email) {
        log.debug("Retrieving guid by email: " + email);

        Iterator<Vertex> container = graph.getVertices("username", email).iterator();
        if (container.hasNext()) {
            return String.format("{\"userguid\": \"%s\"}", container.next().getProperty("userguid"));
        } else {
            // No result found, create a new user
            String newUserGuid = UUID.randomUUID().toString();
            Vertex newUser = graph.addVertex(newUserGuid);
            newUser.setProperty("username", email);
            newUser.setProperty("userguid", newUserGuid);

            return String.format("{\"userguid\": \"%s\"}", newUserGuid);
        }
    }
}
