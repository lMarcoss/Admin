package com.system.response;


import javax.ws.rs.core.Response;

/**
 * Created by IntelliJ IDEA.
 * Author: Marcos Santiago Leonardo
 * Date: 09/06/2018
 * Time: 18:08
 */
public class CommonResponse {
    public static Response responseError(Throwable e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
    }

    public static Response responseSuccess(Object entityObject) {
        return Response.status(Response.Status.OK).entity(entityObject).build();
    }

}
