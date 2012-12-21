package com.whysearchtwice.blueprints_rest_service.jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/device/")
public class Device {

    @POST
    @Consumes("application/json")
    @Path("/")
    public void createNewDevice() {
        System.out.println("1");
    }
    
    @GET
    @Produces("application/json")
    @Path("/{guid}")
    public String getDeviceDetails(@PathParam("guid") String guid) {
        System.out.println("2");
        return guid;
    }
    
    @PATCH
    @Path("/{guid}/name/{newName}")
    public void createNewDevice(@PathParam("guid") String guid, @PathParam("newName") String newName) {
        System.out.println("3");
    }
}
