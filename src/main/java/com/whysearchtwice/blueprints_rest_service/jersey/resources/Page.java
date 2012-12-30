package com.whysearchtwice.blueprints_rest_service.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.whysearchtwice.blueprints_rest_service.jersey.JerseyServer;

/**
 * REST endpoints for the Page resource. For use with the Jersey web server
 * 
 * @author Tony Grosinger
 */
@Path("/page/{action}")
public class Page {
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String reportPageEvent(@PathParam("action") String action, String content) {
        JSONObject data = (JSONObject) JSONValue.parse(content);

        // Test that required values exist
        String[] requiredValues = { "tabId", "windowId", "userGuid", "pageOpenTime", "deviceGuid", "url" };
        boolean requiredValuesPresent = testExists(requiredValues, data);

        if (!requiredValuesPresent) {
            return "{\"error\": \"Missing required field\"}";
        }

        if (action.equals("open")) {
            openPage(data);
        } else if (action.equals("close")) {
            closePage(data);
        } else {
            System.out.println("What are you doing?!");
        }

        return "{\"message\":\"Page view stored\"}";
    }

    public void openPage(JSONObject data) {
        JerseyServer.connection.pageInteractions.storePageOpenEvent(data);
    }

    private void closePage(JSONObject data) {
        JerseyServer.connection.pageInteractions.storePageCloseEvent(data);
    }

    private boolean testExists(String[] keys, JSONObject data) {
        for (String key : keys) {
            if (!data.containsKey(key) || data.get(key) == null) {
                return false;
            }
        }
        return true;
    }
}
