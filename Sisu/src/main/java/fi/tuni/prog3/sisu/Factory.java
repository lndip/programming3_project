package fi.tuni.prog3.sisu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import fi.tuni.prog3.modules.*;
import fi.tuni.prog3.modules.Module;
import fi.tuni.prog3.modules.DegreeModule.Rule;

/**
 * Class to handle featching API from SISU
 */
public class Factory implements iAPI {

    private static Gson gson = new Gson();

    private static final String DegreeSearchUrlFormat = "https://sis-tuni.funidata.fi/kori/api/module-search?curriculumPeriodId=uta-lvv-%d&universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000";

    private static final String SisuModuleApiFormat = "https://sis-tuni.funidata.fi/kori/api/modules/by-group-id?groupId=%s&universityId=tuni-university-root-id";

    private static final String SisuCourseApiFormat = "https://sis-tuni.funidata.fi/kori/api/course-units/by-group-id?groupId=%s&universityId=tuni-university-root-id";

    // public static CourseUnit createCourse(String courseFileName) throws Exception
    // {
    // JsonObject json = null;

    // try {
    // SisuFileReader reader = new SisuFileReader();
    // reader.readFromFile(courseFileName);
    // json = reader.getJsonObject();
    // } catch (Exception e) {
    // throw e;
    // }

    // try {
    // String name =
    // json.getAsJsonObject("name").getAsJsonPrimitive("en").getAsString();
    // String id = json.getAsJsonObject("id").getAsString();
    // String groupId = json.getAsJsonObject("groupId").getAsString();
    // int minCredits =
    // json.getAsJsonObject("credits").getAsJsonPrimitive("min").getAsInt();
    // int maxCredits =
    // json.getAsJsonObject("credits").getAsJsonPrimitive("max").getAsInt();
    // String code = json.getAsJsonObject("code").getAsString();
    // // CourseUnit course = new CourseUnit(name, id, groupId, code, minCredits,
    // // maxCredits);
    // // return course;
    // return null;
    // } catch (Exception e) {
    // throw e;
    // }

    // }

    /**
     * Get Degree from Module_Search URL
     * 
     * @param curriculumPeriod the period year to get the Degrees
     * @param lang             the language to get the Degrees
     * @return a map with key is name, value is groupID of the Degrees
     * @throws Exception when reading the API's URL fails
     */
    public static TreeMap<String, String> getDegreeFromSearch(Integer curriculumPeriod, String lang) throws Exception {
        TreeMap<String, String> result = new TreeMap<String, String>();

        try {
            String stringJson = iAPI.readUrl(String.format(DegreeSearchUrlFormat, curriculumPeriod));
            JsonObject json = new Gson().fromJson(stringJson, JsonObject.class);

            JsonArray degrees = json.getAsJsonArray("searchResults");

            for (JsonElement degree : degrees) {
                JsonObject degreeJson = degree.getAsJsonObject();
                if (!degreeJson.get("lang").getAsString().equals(lang)) {
                    continue;
                }

                String name = degreeJson.get("name").getAsString();
                String groupId = degreeJson.get("groupId").getAsString();
                result.put(name, groupId);
            }

        } catch (Exception e) {
            throw e;
        }

        return result;
    }

    /**
     * Read the degree Module from Module_Search URL
     * 
     * @param groupId the Group ID to query
     * @return Module Object
     * @throws IOException when reading the API's URL fails
     */
    public static Module readDegreeModule(String groupId) throws IOException {
        String stringJson = iAPI.readUrl(String.format(SisuModuleApiFormat, groupId));
        return gson.fromJson(stringJson, Module[].class)[0];

    }

    /**
     * Get Track of Degree from Module_Search API
     * 
     * @param degreeId degree id of Module
     * @return a map with key is name, value is id of Track of Degree of the given
     *         Degree
     * @throws IOException when reading the API's URL fails
     */
    public static TreeMap<String, String> getDegreeTrack(String degreeId) throws IOException {

        TreeMap<String, String> result = new TreeMap<String, String>();
        Module degree = readDegreeModule(degreeId);

        if (degree.rule.rules == null) {
            return null;
        }

        for (DegreeModule.Rule r : degree.rule.rules) {
            DegreeModule subModule = readDegreeModule(r.moduleGroupId);

            if (subModule.name.en == null) {
                result.put(subModule.name.fi, subModule.id);
            } else {
                result.put(subModule.name.en, subModule.id);
            }
        }

        return result;
    }

    /**
     * Get CourseUnit from CourseUnit API
     * 
     * @param courseID course ID of the CourseUnit
     * @return CourseUnit object
     * @throws IOException when reading the API's URL fails
     */
    public static CourseUnit getCourseUnit(String courseID) throws IOException {
        String stringJson = iAPI.readUrl(String.format(SisuCourseApiFormat, courseID));
        // System.out.println(stringJson);
        CourseUnit course = gson.fromJson(stringJson, CourseUnit[].class)[0];
        return course;
    }

    /**
     * Read the courseUnits
     * 
     * @param root_id The D of the CourseUnit
     * @return TreeDegreeModule with node (courseUnit) having no childen
     * @throws IOException when reading the API from URL fails
     */
    public static TreeDegreeModule readTreeCourseUnit(String root_id) throws IOException {
        return new TreeDegreeModule(getCourseUnit(root_id), null);
    }

    /**
     * Read all the DegreeModule from the specified inital DegreeModule's ID
     * 
     * @param degreeModuleId The inital ID of the DegreeModule
     * @return the inital DegreeModule object
     * @throws IOException when reading the API from URL fails
     */
    public static TreeDegreeModule readTreeDegreeModule(String degreeModuleId) throws IOException {
        Module degreeModule = readDegreeModule(degreeModuleId);
        List<TreeDegreeModule> subModules = new ArrayList<>();

        Rule rule = degreeModule.rule;
        List<Rule> rules = null;

        if (rule == null) {
            return new TreeDegreeModule(degreeModule, null);

        }
        while (true) {

            // for DegreeModule has no tracks
            if (rule.type.compareTo("CreditsRule") == 0) {
                degreeModule.credits = rule.credits;
                rule = rule.rule;
                continue;
            }

            // for DegreeModule is Module or CourseUnit
            if (rule.type.compareTo("CompositeRule") == 0) {
                if (rule.rules.get(0).type.compareTo("CourseUnitRule") == 0
                        || rule.rules.get(0).type.compareTo("ModuleRule") == 0
                        || rule.rules.get(0).type.compareTo("AnyCourseUnitRule") == 0
                        || rule.rules.get(0).type.compareTo("AnyModuleRule") == 0) {
                    rules = rule.rules;
                    break;
                }
                rule = rule.rules.get(0);
            }
        }

        // loop through the rule class to find the subModules.
        for (Rule r : rules) {
            if (r.type.compareTo("CourseUnitRule") == 0) {
                TreeDegreeModule temp = readTreeCourseUnit(r.courseUnitGroupId);
                subModules.add(temp);
            }
            if (r.type.compareTo("ModuleRule") == 0) {
                if (r.moduleGroupId.length() != 40) {
                    continue;
                }
                TreeDegreeModule temp = readTreeDegreeModule(r.moduleGroupId);
                subModules.add(temp);
            }
        }

        return new TreeDegreeModule(degreeModule, subModules);
    }

    /**
     * Read all the DegreeModule from the specified inital DegreeModule's ID and
     * Track's ID
     * 
     * @param degreeModuleId The inital ID of the DegrÔOeeModule
     * @param trackModuleId  The ID of the TrackModule
     * @return the inital DegreeModule object
     * @throws IOException when reading the API from URL fails
     */
    public static TreeDegreeModule readTreeDegreeModuleFromTrack(String degreeModuleId, String trackModuleId)
            throws IOException {
        Module degreeModule = readDegreeModule(degreeModuleId);
        TreeDegreeModule readTrack = readTreeDegreeModule(trackModuleId);
        List<TreeDegreeModule> subTrack = new ArrayList<>();
        subTrack.add(readTrack);

        return new TreeDegreeModule(degreeModule, subTrack);
    }
}
