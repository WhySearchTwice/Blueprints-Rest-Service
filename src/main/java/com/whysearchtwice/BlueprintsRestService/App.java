package com.whysearchtwice.BlueprintsRestService;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

public class App {
    public static void main(String[] args) {
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
        Vertex juno2 = g.getVertices("name","juno").iterator().next();
        for (Vertex vertex : juno2.query().vertices()) {
            System.out.println(vertex.getProperty("name"));
        }
    }
}
