
package com.eif400.projectData;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.simple.JSONArray;
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
public class ProjectData implements Serializable {

    private String project, course, instance, cycle, organization, projectSite, team, code;
    private ArrayList<Member> members;

    public ProjectData(String project, String course, String instance, String cycle, String organization, String projectSite, String team, String code, ArrayList<Member> members) {
        this.project = project;
        this.course = course;
        this.instance = instance;
        this.cycle = cycle;
        this.organization = organization;
        this.projectSite = projectSite;
        this.team = team;
        this.code = code;
        this.members = members;
    }

    public ProjectData() {
        this.members = new ArrayList<>();
        loadJSON();
    }

    private void loadJSON() {
        var jsonParser = new JSONParser();
        try (var reader = new FileReader("Integrantes.json")) {
            //Read JSON file
            var obj = jsonParser.parse(reader);
            //JSON objects
            var projectData = (JSONObject) obj;
            var Jteam = (JSONObject) projectData.get("team");
            var Jmembers = (JSONArray) Jteam.get("members");
            //Init attributes
            init(projectData, Jteam, Jmembers);
        } catch (FileNotFoundException e) {
            System.out.print(e);
        } catch (IOException | ParseException e) {
            System.out.print(e);
        }
    }

    private void init(JSONObject projectData, JSONObject team, JSONArray members) {
        //Init project data
        this.project = (String) projectData.get("project");
        this.course = (String) projectData.get("course");
        this.instance = (String) projectData.get("instance");
        this.cycle = (String) projectData.get("cycle");
        this.organization = (String) projectData.get("organization");
        this.projectSite = (String) projectData.get("projectSite");
        //Init team data
        this.team = (String) team.get("team");
        this.code = (String) team.get("code");
        //Init members data
        members.forEach(member -> initMembers((JSONObject) member));
    }

    private void initMembers(JSONObject mem) {
        var member = new Member(
                (String) mem.get("firstName"),
                (String) mem.get("surnames"),
                (String) mem.get("id")
        );
        this.members.add(member);
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProjectSite() {
        return projectSite;
    }

    public void setProjectSite(String projectSite) {
        this.projectSite = projectSite;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
