package com.whysearchtwice.BlueprintsRestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class AnotherResource {
    @GET
    @Produces("text/plain")
    public String getAnotherMessage() {
        return "Test Hello";
    }
}
