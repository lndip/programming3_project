/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.modules;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fi.tuni.prog3.sisu.iAPI;

/**
 * An abstract class for storing information on Modules and Courses.
 */
public abstract class DegreeModule {

    /**
     * constructor for credit information
     */
    public static class Credit {
        public Integer min;
        public Integer max;
    }

    /**
     * constructor for rule information
     */
    public static class Rule {
        public String type;
        public String localId;
        public Rule rule;
        public List<Rule> rules;

        public String moduleGroupId;
        public String courseUnitGroupId;

        public Rule credits;
    }

    /**
     * Constructor for name of DegreeModule
     */
    public static class Name {
        public String en;
        public String fi;
        public String sv;
    }

    /**
     * groupdID of DegreeModule
     */
    public String groupId;
    /**
     * Name of DegreeModule
     */
    public Name name;
    /**
     * id of DegreeModule
     */
    public String id;
    /**
     * Code of DegreeModule
     */
    public String code;
    /**
     * credits of Rule of DegreeModule
     */
    public Rule credits;
    /**
     * Rule of DegreeModule
     */
    public Rule rule = null;
    /**
     * minimum credits of DegreeModule
     */
    public int minCredits;
    /**
     * Flag whether the Module is courseUnit or Module
     */
    public boolean isChosen; // if the object is CourseUnit

    /**
     * A constructor for initializing the member variables.
     * 
     * @param name       name of the Module or Course.
     * @param id         id of the Module or Course.
     * @param groupId    group id of the Module or Course.
     * @param code       code of the Module or Course
     * @param minCredits minimum credits of the Module or Course.
     */
    public DegreeModule(Name name, String id, String groupId, String code, int minCredits) {

        this.name = name;
        this.id = id;
        this.groupId = groupId;
        this.minCredits = minCredits;
    }

}
