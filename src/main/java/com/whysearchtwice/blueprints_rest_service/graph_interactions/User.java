package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class User {
    private TitanGraph graph;

    public User(TitanGraph graph) {
        this.graph = graph;
    }

    public String getUserEmailByGuid(String guid) {
        Vertex v = graph.getVertices("userguid", guid).iterator().next();
        if (v != null) {
            return String.format("{\"username\": \"%s\"}", v.getProperty("username"));
        } else {
            return "No results found";
        }
    }

    public String getUserGuidByEmail(String email) {
        Vertex v = graph.getVertices("username", email).iterator().next();
        if (v != null) {
            return String.format("{\"userguid\": \"%s\"}", v.getProperty("userguid"));
        } else {
            return "No results found";
        }
    }
}
