/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.modules;

import java.util.List;

/**
 * A class Module represents ModuleDegree
 * 
 * @author Admin
 */
public class Module extends DegreeModule {

    // Attributes for Module class
    /**
     * type of the module
     */
    public String type;

    /**
     * Constructor
     * 
     * @param name    name of Module
     * @param id      id of Module
     * @param groupId group id of Module
     * @param code    code of Module
     * @param type    type of Module
     * @param rule    rule of Module
     * @param minCre  minimum credits of Module
     * @param maxCre  maximum credits of Module
     */
    public Module(Name name, String id, String groupId, String code,
            String type, Rule rule, int minCre, int maxCre) {
        super(name, id, groupId, code, minCre);
        this.type = type;
        this.rule = rule;
    }

    /**
     * Get type of ModuleDegree
     * 
     * @return type of ModuleDegree
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get rule of ModuleDegree
     * 
     * @return rule of ModuleDegree
     */
    public Rule getRule() {
        return this.rule;
    }
}
