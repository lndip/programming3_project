package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.Sisu.comboItem;
import java.util.Map;
import java.util.TreeMap;
import javafx.scene.control.ComboBox;

/**
 * A class that controls the chosen degree and track (if there are any) from the
 * corresponding study year and study language.
*/
public class GUIController {
    
    /**
     * Add appropriate degrees to the degreeComboBox according to the chosen year and language.
     * 
     * @param degreeComboBox The target degreeComboBox
     * @param year The chosen curriculum year
     * @param lang The chosen language
     * @throws java.lang.Exception when getDegreeFromSearch fails
    */
    public void setDegreeComboBox(ComboBox<comboItem> degreeComboBox,
                                  String year,
                                  String lang) throws Exception{
        degreeComboBox.getItems().clear();
        degreeComboBox.setPromptText("Please choose a degree");
        
        Integer yearInt = Integer.valueOf(year);
        
        TreeMap<String, String> degrees = Factory.getDegreeFromSearch(yearInt, lang);
        
        for(Map.Entry<String, String> entry : degrees.entrySet()){
            degreeComboBox.getItems().add(new comboItem(entry.getKey(), entry.getValue()));
        }
    }
    
    /**
     * Add appropriate tracks to the trackComboBox according to the chosen degree.
     * 
     * @param trackCombobox The target trackComboBox
     * @param degreeID The degreeID of the chosen degree
     * @throws java.lang.Exception when getDegreeTrack fails
    */
    public void setTrackComboBox(ComboBox<comboItem> trackCombobox, 
                                 String degreeID) throws Exception{
        trackCombobox.getItems().clear();
        trackCombobox.setPromptText("Please choose a track");
        
        TreeMap<String, String> tracks = Factory.getDegreeTrack(degreeID);
        
        if (tracks == null){
            trackCombobox.setPromptText("No tracks is included in this degree");
            return;
        }
        
        for(Map.Entry<String, String> entry : tracks.entrySet()){
            trackCombobox.getItems().add(new comboItem(entry.getKey(), entry.getValue()));
        }   
    }
}
