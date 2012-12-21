package com.whysearchtwice.blueprints_rest_service.jersey.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;

@Path("/page/{action}")
public class Page {
    @POST
    @Consumes("application/json")
    public void reportPageEvent(@PathParam("action") String action, String content) {
        if(action.equals("open")) {
            openPage(content);
        } else if (action.equals("close")) {
            closePage(content);
        } else {
            System.out.println("What are you doing?!");
        }
    }
    
    public void openPage(String content) {
        System.out.println("Opened a page");
        System.out.println(content);
    }
    
    private void closePage(String content) {
        System.out.println("Closed a page");
    }
}
