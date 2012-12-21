package com.whysearchtwice.blueprints_rest_service.jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/user/")
public class User {
    @GET
    @Produces("application/json")
    @Path("/{email}")
    public String getUser(@PathParam("email") String email) {
        System.out.println("1");
        return "";
    }

    @PATCH
    @Consumes("application/json")
    @Path("/{guid}")
    public void updateUser(@PathParam("guid") String email, String content) {
        System.out.println("2");
    }

    @GET
    @Produces("application/json")
    @Path("/{guid}/timerange/start/{startTime}/stop/{stoptime}")
    public String getUserPages(@PathParam("guid") String guid, @PathParam("startTime") String startTime, @PathParam("stopTime") String stopTime) {
        System.out.println("3");
        return "";
    }
}
