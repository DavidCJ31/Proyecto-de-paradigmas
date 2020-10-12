package com.eif400.dataBase;

import com.eif400.projectData.ProjectData;
import java.util.ArrayList;
import static org.dizitart.no2.Document.createDocument;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteId;

/*
    Carlos Zhou Zheng
    Jose Martinez Sarmiento
    David Morales Hidalgo
    Manuel Guzman Rodriguez
    David Cordero Jimenez
 */
public class DataBase {

    private static Nitrite db;
    private static ProjectData projectData;
    private final static String DBDIR = "./dataBase/DataBase.db";
    private final static String COLLECTIONNAME = "projectData";
    private final static String USER = "UNA";
    private final static String PASSWORD = "admin";

    public DataBase() {

    }

    public static void createDB() {
        //Create db
        db = Nitrite.builder().compressed().filePath(DBDIR).openOrCreate(USER, PASSWORD);
        //Create project data collection
        var collection = db.getCollection(COLLECTIONNAME);
        //Instance project data
        var pj = new ProjectData();
        //Create a document to populate data
        var doc = createDocument("project", pj.getProject())
                .put("course", pj.getCourse())
                .put("instance", pj.getInstance())
                .put("cycle", pj.getCycle())
                .put("organization", pj.getOrganization())
                .put("projectSite", pj.getProjectSite())
                .put("team", pj.getTeam())
                .put("code", pj.getCode())
                .put("members", pj.getMembers());
        //Insert the document
        var writeResult = collection.insert(doc);
        db.close();
    }

    public static ProjectData getProjectData() {
        db = Nitrite.builder().compressed().filePath(DBDIR).openOrCreate(USER, PASSWORD);
        var collection = db.getCollection(COLLECTIONNAME);
        var nitriteId = NitriteId.createId(Long.parseLong("85287138468300"));
        var d = collection.getById(nitriteId);
        if (d != null) {
            projectData = new ProjectData(
                    d.get("project", String.class),
                    d.get("course", String.class),
                    d.get("instance", String.class),
                    d.get("cycle", String.class),
                    d.get("organization", String.class),
                    d.get("projectSite", String.class),
                    d.get("team", String.class),
                    d.get("code", String.class),
                    d.get("members", ArrayList.class)
            );
        }
        db.close();
        return projectData;
    }

}
