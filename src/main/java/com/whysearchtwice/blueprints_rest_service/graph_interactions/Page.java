package com.whysearchtwice.blueprints_rest_service.graph_interactions;

import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Vertex;

public class Page {
    private TitanGraph graph;

    /**
     * Logger for this class
     */
    private static Log log;

    public Page(TitanGraph graph) {
        // Setup Logging
        log = LogFactory.getLog(Page.class);

        this.graph = graph;
    }

    public void storePageOpenEvent(JSONObject data) {
        // Find user vertex
        String userGuid = (String) data.get("userGuid");
        Vertex userVertex = null;
        Iterator<Vertex> container = graph.getVertices("userguid", userGuid).iterator();
        if (container.hasNext()) {
            userVertex = container.next();
        } else {
            // TODO: Create a new user when it does not exist
        }
        
        if(userVertex != null) {
            
        }
    }

    public void storePageCloseEvent(JSONObject data) {

    }
}
