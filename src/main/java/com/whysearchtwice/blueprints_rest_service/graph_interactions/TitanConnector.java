package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;

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
}
