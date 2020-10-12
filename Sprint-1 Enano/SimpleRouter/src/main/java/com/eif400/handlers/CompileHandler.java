package com.eif400.handlers;

import com.eif400.compiler.CompilerJava;
import fi.iki.elonen.NanoHTTPD;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
public class CompileHandler extends RouterNanoHTTPD.DefaultHandler {

    @Override
    public String getText() {
        var j = new JSONObject();
        j.put("Name", "CompilerHandler");
        return j.toJSONString();
    }

    @Override
    public NanoHTTPD.Response.IStatus getStatus() {
        return NanoHTTPD.Response.Status.OK;
    }

    @Override
    public String getMimeType() {
        return "application/json";
    }

    @Override
    public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource, Map<String, String> urlParams, NanoHTTPD.IHTTPSession session) {
        var aux = new HashMap<String, String>();
        var parser = new JSONParser();
        var json = new JSONObject();
        var jsonOut = new JSONObject();
        String out = "";
        try {
            session.parseBody(aux);
            json = (JSONObject) parser.parse(aux.get("postData"));
            out = CompilerJava.compileProcess(json.get("code").toString());
        } catch (IOException | ParseException | NanoHTTPD.ResponseException ex) {
            System.err.print(ex);
        }
        jsonOut.put("out", out);
        var response = newFixedLengthResponse(jsonOut.toJSONString());
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("content-type", "json");
        return response;
    }

}
