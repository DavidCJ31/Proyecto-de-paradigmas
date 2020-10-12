package com.eif400.simplerouter;

import com.eif400.dataBase.DataBase;
import com.eif400.handlers.CompileHandler;
import com.eif400.handlers.MembersHandler;
import java.io.IOException;
import java.util.*;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.router.RouterNanoHTTPD;
import static fi.iki.elonen.NanoHTTPD.Response;

import static fi.iki.elonen.NanoHTTPD.SOCKET_READ_TIMEOUT;

import fi.iki.elonen.router.RouterNanoHTTPD.IndexHandler;
import org.apache.log4j.BasicConfigurator;

/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
public class SimpleRouter extends RouterNanoHTTPD {

    static int PORT = 8080;

    public SimpleRouter(int port) throws IOException {
        super(port);
        addMappings();
        start(SOCKET_READ_TIMEOUT, false);
        System.out.format("*** Router running on port %d ***%n", port);
    }

    @Override
    public void addMappings() {
        addRoute("/", IndexHandler.class);
        addRoute("/members", MembersHandler.class);
        addRoute("/compile", CompileHandler.class);

    }
    private final List<String> ALLOWED_SITES = Arrays.asList("same-site", "same-origin");

    @Override
    public Response serve(IHTTPSession session) {

        Map<String, String> request_headers = session.getHeaders();

        System.out.format("*** SimpleRouter:serve request headers= %s ***%n", request_headers);
        String origin = "none";
        boolean cors_allowed = request_headers != null
                && "cors".equals(request_headers.get("sec-fetch-mode"))
                && ALLOWED_SITES.indexOf(request_headers.get("sec-fetch-site")) >= 0
                && (origin = request_headers.get("origin")) != null;
        var response = super.serve(session);

        if (cors_allowed) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.addHeader("content-type", "json");
        }
        return response;
    }

    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        PORT = args.length == 0 ? 8080 : Integer.parseInt(args[0]);
        new SimpleRouter(PORT);

    }
}
