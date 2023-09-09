package fi.tuni.prog3.sisu;

import fi.tuni.prog3.modules.TreeDegreeModule;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * JavaFX Sisu to handle the main window
 */
public class Sisu extends Application {
    /**
     * Class for controlling the comboItem to store a display name and its value
     */
    public static class comboItem {
        /**
         * key and value to store for comboItem
         */
        public String key, value;

        /**
         * Constructs a new comboItem
         * 
         * @param DisplaceValue the key of the comboItem
         * @param Value         the value of the conmboItem
         */
        public comboItem(String DisplaceValue, String Value) {
            this.key = DisplaceValue;
            this.value = Value;
        }

        /**
         * Convert the value of key to String
         * 
         * @return the value of key of comboItem
         */
        @Override
        public String toString() {
            return this.key;
        }
    }

    // GUI attributes
    private static final int WINDOW_HEIGHT = 720;
    private static final int WINDOW_WIDTH = 1200;
    private static final int LEFT_MARGIN = 200;
    private GUIController GUIcontroller;
    private String root_id;
    private User user = new User();

    @Override
    public void start(Stage stage) throws IOException {

        this.GUIcontroller = new GUIController();

        // Tab #1: Get student information. Set language and degree
        Tab tab_1 = new Tab("Student");
        Pane tab1 = new Pane();

        Label degreeLabel = new Label();
        degreeLabel.setText("Choose degree language and study year");
        degreeLabel.setLayoutX(LEFT_MARGIN);
        degreeLabel.setLayoutY(270);

        ComboBox<comboItem> langComboBox = new ComboBox<>();
        langComboBox.getItems().add(new comboItem("English", "en"));
        langComboBox.getItems().add(new comboItem("Finnish", "fi"));
        langComboBox.setPromptText("Degree language");
        langComboBox.setLayoutX(LEFT_MARGIN);
        langComboBox.setLayoutY(300);

        ComboBox<String> yearComboBox = new ComboBox<>();
        for (int i = 2019; i <= 2025; i++) {
            yearComboBox.getItems().add(String.format("%d", i));
        }
        yearComboBox.setPromptText("Academic year");
        yearComboBox.setLayoutX(LEFT_MARGIN + 200);
        yearComboBox.setLayoutY(300);

        Label studentLabelNew = new Label();
        studentLabelNew.setText("ADD NEW STUDENT INFORMATION");
        studentLabelNew.setLayoutX(LEFT_MARGIN + 50);
        studentLabelNew.setLayoutY(220);

        TextField studentNameFieldNew = new TextField();
        studentNameFieldNew.setPromptText("Enter student name");
        studentNameFieldNew.setPrefWidth(150);
        studentNameFieldNew.setLayoutX(LEFT_MARGIN);
        studentNameFieldNew.setLayoutY(350);

        TextField studentNumFieldNew = new TextField();
        studentNumFieldNew.setPromptText("Enter student number");
        studentNumFieldNew.setPrefWidth(150);
        studentNumFieldNew.setLayoutX(LEFT_MARGIN + 200);
        studentNumFieldNew.setLayoutY(350);

        Label studentLabelLoad = new Label();
        studentLabelLoad.setText("LOAD STUDENT INFORMATION FROM DATABASE");
        studentLabelLoad.setLayoutX(LEFT_MARGIN + 450);
        studentLabelLoad.setLayoutY(220);

        Label message = new Label();
        message.setLayoutX(LEFT_MARGIN + 500);
        message.setLayoutY(350);

        TextField studentNumFieldLoad = new TextField();
        studentNumFieldLoad.setPromptText("Enter student number");
        studentNumFieldLoad.setPrefWidth(150);
        studentNumFieldLoad.setLayoutX(LEFT_MARGIN + 510);
        studentNumFieldLoad.setLayoutY(300);

        Button beginButton = new Button("Begin");
        beginButton.setLayoutX(WINDOW_WIDTH / 4 + 50);
        beginButton.setLayoutY(400);

        Button loadButton = new Button("Load from database");
        loadButton.setLayoutX(WINDOW_WIDTH / 2 + 120);
        loadButton.setLayoutY(400);

        Button exitButton1 = new Button("EXIT");
        exitButton1.setLayoutX(WINDOW_WIDTH - 100);
        exitButton1.setLayoutY(WINDOW_HEIGHT - 100);

        Line separator = new Line();
        separator.setStartX(LEFT_MARGIN + 375);
        separator.setStartY(200);
        separator.setEndX(LEFT_MARGIN + 375);
        separator.setEndY(450);

        tab1.getChildren().add(degreeLabel);
        tab1.getChildren().add(studentLabelNew);
        tab1.getChildren().add(studentLabelLoad);
        tab1.getChildren().add(studentNameFieldNew);
        tab1.getChildren().add(studentNumFieldNew);
        tab1.getChildren().add(studentNumFieldLoad);
        tab1.getChildren().add(message);
        tab1.getChildren().add(beginButton);
        tab1.getChildren().add(loadButton);
        tab1.getChildren().add(exitButton1);
        tab1.getChildren().add(langComboBox);
        tab1.getChildren().add(yearComboBox);
        tab1.getChildren().add(separator);

        tab_1.setContent(tab1);

        // Tab #2: Structure of studies
        Tab tab_2 = new Tab("Structure of studies");
        Pane tab2 = new Pane();

        ComboBox<comboItem> degreeComboBox = new ComboBox<>();
        degreeComboBox.setPromptText("Please choose a degree");
        degreeComboBox.setPrefWidth(WINDOW_WIDTH / 2);
        degreeComboBox.setLayoutX(0);
        degreeComboBox.setLayoutY(0);

        ComboBox<comboItem> trackComboBox = new ComboBox<>();
        trackComboBox.setPromptText("Please choose a track");
        trackComboBox.setPrefWidth(WINDOW_WIDTH / 2);
        trackComboBox.setLayoutX(WINDOW_WIDTH / 2);
        trackComboBox.setLayoutY(0);

        Button exitButton2 = new Button("Save and EXIT");
        exitButton2.setLayoutX(WINDOW_WIDTH - 150);
        exitButton2.setLayoutY(WINDOW_HEIGHT - 100);

        CourseTickBoxes tickBoxesController = new CourseTickBoxes(tab2, this.user, 600, 300);
        CourseTreeView courseTreeView = new CourseTreeView(tab2, 0, 27, WINDOW_WIDTH / 2, 500, tickBoxesController);
        tickBoxesController.setCourseTreeView(courseTreeView);

        tab2.getChildren().add(degreeComboBox);
        tab2.getChildren().add(trackComboBox);
        tab2.getChildren().add(exitButton2);

        tab_2.setContent(tab2);

        // Add the tabs to the scene for display
        TabPane tabPane = new TabPane(tab_1, tab_2);
        Scene scene = new Scene(tabPane, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.setTitle("SISU GUI");

        stage.show();

        // Add actions to elements
        exitButton1.setOnAction((event) -> {
            stage.close();
        });

        exitButton2.setOnAction((event) -> {
            // Add funtion to save student information in a file
            user.printJsonToFile();
            stage.close();
        });

        // Begin button's action trigers the API reader
        beginButton.setOnAction((event) -> {
            try {
                GUIcontroller.setDegreeComboBox(degreeComboBox, yearComboBox.getValue(),
                        langComboBox.getValue().value);
                courseTreeView.clearTreeView();
            } catch (Exception e) {
                System.out.println("Cannot find Degree");
            }

            tabPane.getSelectionModel().select(1);

            degreeComboBox.setPromptText("Please choose a degree.");
            trackComboBox.setPromptText("Please choose a track.");

            user.setUser(new User.StudentInformation(studentNumFieldNew.getText(),
                    studentNameFieldNew.getText()));
        });

        // Load button's action triggers loading student info from database
        loadButton.setOnAction((event) -> {
            String studentNum = studentNumFieldLoad.getText();
            if (studentNum.equals("")) {
                message.setText("Invalid student number!");
            } else {
                try {
                    User.StudentInformation studentInfo = user.searchJson(studentNum);

                    if (Objects.isNull(studentInfo)) {
                        message.setText("Student is not found!");
                    } else {
                        this.user.setUser(studentInfo);

                        message.setText("Info of " + studentInfo.studentName + " loaded");
                        courseTreeView.loadUser(user);

                        tabPane.getSelectionModel().select(1);
                    }

                } catch (IOException ex) {
                }

            }
        });

        degreeComboBox.setOnAction((event) -> {
            try {
                this.root_id = degreeComboBox.getSelectionModel().getSelectedItem().value;
                GUIcontroller.setTrackComboBox(trackComboBox, this.root_id);

                if (trackComboBox.getItems().isEmpty()) {
                    courseTreeView.populateData(root_id);
                }

                this.user.setSelectedDegree(degreeComboBox.getSelectionModel().getSelectedItem().value);

            } catch (Exception e) {
            }
        });

        trackComboBox.setOnAction((event) -> {
            if (trackComboBox.getSelectionModel().getSelectedItem() != null)
                this.root_id = trackComboBox.getSelectionModel().getSelectedItem().value;

            if (!trackComboBox.getItems().isEmpty()) {
                try {
                    courseTreeView.populateData(degreeComboBox.getSelectionModel().getSelectedItem().value,
                            this.root_id);
                    this.user.setSelectedTrack(trackComboBox.getSelectionModel().getSelectedItem().value);
                } catch (IOException e) {
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

}