package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLWriter;

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

    public User userInteractions;

    /**
     * Open a Titan graph using a file store as the backend (not persistent)
     * 
     * @param tempLocation
     *            - Location to save temporary database
     */
    public TitanConnector(String tempLocation) {
        this.graph = TitanFactory.open(tempLocation);

        createConnections();
    }

    /**
     * Open a Titan graph using Cassandra as the backend.
     */
    public TitanConnector() {
        Configuration conf = new BaseConfiguration();
        conf.setProperty("storage.backend", "cassandra");
        conf.setProperty("storage.hostname", "127.0.0.1");
        this.graph = TitanFactory.open(conf);

        createConnections();
    }

    private void createConnections() {
        userInteractions = new User(graph);
    }

    public void loadXmlData(String filename) {
        try {
            InputStream in = new FileInputStream(filename);
            GraphMLReader.inputGraph(graph, in);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void dumpXmlData(String filename) {
        try {
            OutputStream out = new FileOutputStream(filename);
            GraphMLWriter.outputGraph(graph, out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void titanDemo() {
        // Create the Graph
        TitanGraph g = TitanFactory.open("/tmp/titan");

        // Create some Indices
        g.createKeyIndex("type", Vertex.class);
        g.createKeyIndex("pageOpenTime", Vertex.class);
        g.createKeyIndex("username", Vertex.class);
        g.createKeyIndex("userguid", Vertex.class);
        
        Vertex user = g.addVertex(null);
        user.setProperty("type", "user");
        user.setProperty("username", "tony@grosinger.net");
        user.setProperty("userguid", "1234567890");
        
        Vertex page1 = g.addVertex(null);
        page1.setProperty("type", "pageView");
        page1.setProperty("title", "Google Mail");
        page1.setProperty("userguid", "1234567890");
        page1.setProperty("url", "https://mail.google.com/mail/u/0/?shva=1#inbox");
        page1.setProperty("pageOpenTime", new Long("1356128851000"));
        page1.setProperty("pageCloseTime", new Long("1356132451000"));
        page1.setProperty("tabId", 1);
        page1.setProperty("windowId", 100);
        
        Vertex page2 = g.addVertex(null);
        page2.setProperty("type", "pageView");
        page2.setProperty("title", "Google Calendar");
        page2.setProperty("userguid", "1234567890");
        page2.setProperty("url", "https://www.google.com/calendar/render");
        page2.setProperty("pageOpenTime", new Long("1356128851000"));
        page2.setProperty("pageCloseTime", new Long("1356132451000"));
        page2.setProperty("tabId", 2);
        page2.setProperty("windowId", 100);
        
        g.addEdge(null, user, page1, "viewed");
        g.addEdge(null, user, page2, "viewed");

        // Example query using an existing reference
        for (Vertex vertex : user.query().vertices()) {
            System.out.println(vertex.getProperty("title"));
        }

        // Example query using an index
        Vertex user2 = g.getVertices("username", "tony@grosinger.net").iterator().next();
        for (Vertex vertex : user2.query().vertices()) {
            System.out.println(vertex.getProperty("title"));
        }
    }
}
