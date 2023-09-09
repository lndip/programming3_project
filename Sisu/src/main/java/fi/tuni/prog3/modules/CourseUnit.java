/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.modules;

/**
 * A class extend DegreeModule for CourseUnit module
 */
public class CourseUnit extends DegreeModule {
    /**
     * Construct
     * 
     * @param name    name of CourseUnit
     * @param id      id of CourseUnit
     * @param groupId group id of CourseUnit
     * @param code    code of CourseUnit
     * @param minCre  minimum credits of CourseUnit
     * @param maxCre  maximum credits of CourseUnit
     */
    public CourseUnit(Name name, String id, String groupId,
            String code, int minCre, int maxCre) {
        super(name, id, groupId, code, minCre);
    }

    /**
     * Method to get the name of the course unit
     * 
     * @return name of the course unit
     */
    public Name getName() {
        return name;
    }
}
