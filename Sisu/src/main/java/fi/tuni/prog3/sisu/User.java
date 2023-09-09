package fi.tuni.prog3.sisu;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * A class to handle user information and facilitates user-related methods
 */

public class User {
    private Gson gson = new Gson();
    private StudentInformation userInfo;

    /**
     * A class to store student information
     */
    public static class StudentInformation {

        public String studentNumber;
        public String studentName;
        public String degreeId = null;
        public String trackId = null;
        public HashSet<String> chosenCourses = new HashSet<>();

        /**
         * Constructor
         * 
         * @param studentNumber the student number
         * @param studentName   the student name
         */
        public StudentInformation(String studentNumber, String studentName) {
            this.studentNumber = studentNumber;
            this.studentName = studentName;
        }

        /**
         * Method to retrieve Student Number
         * 
         * @return String - the student number
         */
        public String getStudentNumber() {
            return this.studentNumber;
        }

        /**
         * Method to retrieve Student Number
         * 
         * @return String - the student number
         */
        public String getStudentName() {
            return this.studentName;
        }

        /**
         * Method to retrieve Student's all chosen courses
         * 
         * @return ArrayList of String - all chosen courses
         */
        public ArrayList<String> getChosenCourses() {
            return new ArrayList<>(this.chosenCourses);
        }
    }

    /**
     * Set the current student
     * 
     * @param user the current student
     */
    public void setUser(StudentInformation user) {
        this.userInfo = user;
    }

    /**
     * Get the current student
     * 
     * @return the current student
     */
    public StudentInformation getStudentInformation() {
        return this.userInfo;
    }

    /**
     * Add a completed course
     * 
     * @param course_id the course ID
     */
    public void addChoosenCourse(String course_id) {
        this.userInfo.chosenCourses.add(course_id);
    }

    /**
     * Set the student's selected degree
     * 
     * @param degreeId the degree ID
     */
    public void setSelectedDegree(String degreeId) {
        this.userInfo.degreeId = degreeId;
    }

    /**
     * Set the student's selected track
     * 
     * @param trackId the track ID
     */
    public void setSelectedTrack(String trackId) {
        this.userInfo.trackId = trackId;
    }

    /**
     * Read and Write student information to the JSON file
     */
    public void printJsonToFile() {
        try {
            Type jsonType = new TypeToken<HashMap<String, StudentInformation>>() {
            }.getType();

            FileReader jsonReader = new FileReader("src/main/resources/students.json");
            HashMap<String, StudentInformation> students = gson.fromJson(
                    jsonReader, jsonType);
            students.put(this.userInfo.studentNumber, this.userInfo);

            FileWriter jsonWriter = new FileWriter("src/main/resources/students.json");
            gson.toJson(students, jsonWriter);
            jsonWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Search the JSON file using Student Number
     * 
     * @param studentNumber the student number
     * @return a StudentInFo of the Student according to the Student Number
     * @throws IOException when opening the JSON file failed
     */
    public StudentInformation searchJson(String studentNumber) throws IOException {
        Type jsonType = new TypeToken<HashMap<String, StudentInformation>>() {
        }.getType();

        FileReader jsonReader = new FileReader("src/main/resources/students.json");
        HashMap<String, StudentInformation> students = gson.fromJson(
                jsonReader, jsonType);

        // Seach the student in map based on StudentNumber
        StudentInformation student = students.get(studentNumber);
        return student;
    }
}
