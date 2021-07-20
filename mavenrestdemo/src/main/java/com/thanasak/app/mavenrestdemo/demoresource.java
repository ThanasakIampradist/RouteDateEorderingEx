/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.thanasak.app.mavenrestdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author Marchmeepool
 */
@Path("/demo")
public class demoresource {

    @GET
    @Path("/say")
    public String Say() {
        return " Thanasak Iampradist";
    }

    @GET
    @Path("/getusers")
    @Produces({MediaType.APPLICATION_JSON})// Response Type
//    @Consumes(MediaType.APPLICATION_JSON)// Request Type
    public Response Users() {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserModel("MARCH0" + i, "THANASAK0" + i));
        }

        GenericEntity<List<UserModel>> genericEntity = new GenericEntity<List<UserModel>>(users) {
        };
        return Response.ok(genericEntity, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserModel> getCustomers() {
        List<UserModel> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new UserModel("MARCH0" + i, "THANASAK0" + i));
        }
        return users;
    }
}
