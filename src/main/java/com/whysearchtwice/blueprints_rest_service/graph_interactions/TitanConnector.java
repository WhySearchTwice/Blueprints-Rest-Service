package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;

/**
 * A connection to the Titan graph that can be used to perform queries and
 * updates on the Titan graph
 * 
 * @author Tony Grosinger
 */
public class TitanConnector {
    /**
     * The graph object that this class will manipulate
     */
    private TitanGraph graph;

    /**
     * Open a Titan graph using a file store as the backend (not persistent)
     * 
     * @param tempLocation
     *            - Location to save temporary database
     */
    public TitanConnector(String tempLocation) {
        this.graph = TitanFactory.open(tempLocation);
    }

    /**
     * Open a Titan graph using Cassandra as the backend.
     */
    public TitanConnector() {
        Configuration conf = new BaseConfiguration();
        conf.setProperty("storage.backend", "cassandra");
        conf.setProperty("storage.hostname", "127.0.0.1");
        this.graph = TitanFactory.open(conf);
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
