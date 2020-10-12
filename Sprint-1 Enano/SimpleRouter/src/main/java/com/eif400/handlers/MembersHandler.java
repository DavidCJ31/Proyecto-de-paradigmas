
package com.eif400.handlers;

import com.google.gson.GsonBuilder;
import com.eif400.dataBase.DataBase;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
    public class MembersHandler extends RouterNanoHTTPD.DefaultHandler {

        @Override
        public String getText() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(DataBase.getProjectData());
        }

        @Override
        public String getMimeType() {
            return "application/json";
        }

        @Override
        public NanoHTTPD.Response.IStatus getStatus() {
            return NanoHTTPD.Response.Status.OK;
        }
    }