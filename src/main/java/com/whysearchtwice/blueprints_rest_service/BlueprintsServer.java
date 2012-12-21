package com.whysearchtwice.blueprints_rest_service;

import java.io.IOException;


import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.whysearchtwice.blueprints_rest_service.graph_interactions.TitanConnector;
import com.whysearchtwice.blueprints_rest_service.jersey.JerseyServer;

public class BlueprintsServer {
    public static void main(String[] args) {
        // Create the titan graph
        TitanConnector conn = new TitanConnector("/tmp/titan");
        
        JerseyServer webserver = new JerseyServer("http://localhost/", 8080);

        try {
            webserver.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            System.in.read();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            webserver.stop();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void titanDemo() {
        // Create the Graph
        TitanGraph g = TitanFactory.open("/tmp/titan");

        // Create some Indices
        g.createKeyIndex("name", Vertex.class);

        // Create some Vertices
        Vertex juno = g.addVertex(null);
        juno.setProperty("name", "juno");
        Vertex turnus = g.addVertex(null);
        turnus.setProperty("name", "turnus");
        Vertex hercules = g.addVertex(null);
        hercules.setProperty("name", "hercules");
        Vertex dido = g.addVertex(null);
        dido.setProperty("name", "dido");
        Vertex troy = g.addVertex(null);
        troy.setProperty("name", "troy");
        Vertex jupiter = g.addVertex(null);
        jupiter.setProperty("name", "jupiter");

        // Create some Edges
        Edge edge = g.addEdge(null, juno, turnus, "knows");
        edge.setProperty("since", 2010);
        edge.setProperty("stars", 5);
        edge = g.addEdge(null, juno, hercules, "knows");
        edge.setProperty("since", 2011);
        edge.setProperty("stars", 1);
        edge = g.addEdge(null, juno, dido, "knows");
        edge.setProperty("since", 2011);
        edge.setProperty("stars", 5);
        g.addEdge(null, juno, troy, "likes").setProperty("stars", 5);

        // Example query using an existing reference
        for (Vertex vertex : juno.query().vertices()) {
            System.out.println(vertex.getProperty("name"));
        }

        // Example query using an index
        Vertex juno2 = g.getVertices("name", "juno").iterator().next();
        for (Vertex vertex : juno2.query().vertices()) {
            System.out.println(vertex.getProperty("name"));
        }
    }
}
