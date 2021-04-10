package com.controller;

import com.entity.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class TestController {
    @Path("/user/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser (@PathParam("name") String userName) {
        User user = new User();
        user.setAge(18);
        user.setName(userName);
        return user;
    }

    @Path("/test")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "hello jersey";
    }
}
