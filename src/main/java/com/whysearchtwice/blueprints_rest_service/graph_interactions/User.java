package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class User {
    private TitanGraph graph;

    public User(TitanGraph graph) {
        this.graph = graph;
    }

    public String getUserEmailByGuid(String guid) {
        for (Vertex v : graph.getVertices("userguid", guid)) {
            return (String) v.getProperty("username");
        }
        return "Not Found";
    }

    public String getUserGuidByEmail(String email) {
        for (Vertex v : graph.getVertices("username", email)) {
            return (String) v.getProperty("userguid");
        }
        return "Not Found";
    }
}
