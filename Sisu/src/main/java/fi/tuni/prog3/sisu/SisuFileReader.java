package fi.tuni.prog3.sisu;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Class to read the json file to json object format
 */
public class SisuFileReader implements iReadAndWriteToFile {
    private JsonObject jsonObject;

    /**
     * Get the json object
     * 
     * @return the current json object
     */
    public JsonObject getJsonObject() {
        return jsonObject;
    }

    /**
     * Read the json file
     * 
     * @param fileName the file to read
     * @return true if successful or false otherwise
     * @throws Exception when an error occurs
     */
    public boolean readFromFile(String fileName) throws Exception {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            return false;
        }
        return true;
    };

    /**
     * Write to the json file
     * 
     * @param file the file to write
     * @throws Exception when an error occurs
     */
    @Override
    public boolean writeToFile(String file) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeToFile'");
    }
}