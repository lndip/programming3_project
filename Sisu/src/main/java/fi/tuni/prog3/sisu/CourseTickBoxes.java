package fi.tuni.prog3.sisu;

import fi.tuni.prog3.modules.CourseUnit;
import fi.tuni.prog3.modules.DegreeModule;
import fi.tuni.prog3.modules.TreeDegreeModule;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class to display the check box for a user to select a courseUnit is
 * completed
 */
public class CourseTickBoxes {

    private User user;
    private VBox vbox = new VBox();
    private CourseTreeView courseTreeView;

    /**
     * Constructor for a new CourseTickBoxes
     * 
     * @param pane    the pane to display the CourseTickBoxes
     * @param user    the current user
     * @param layoutX the horizontal layout of the CourseTickBoxes
     * @param layoutY the vertical layout of the CourseTickBoxes
     */
    public CourseTickBoxes(Pane pane, User user, int layoutX, int layoutY) {
        pane.getChildren().add(vbox);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);
        this.user = user;
    }

    /**
     * Set courseTreeView
     * 
     * @param courseTreeView the course tree view
     */
    public void setCourseTreeView(CourseTreeView courseTreeView) {
        this.courseTreeView = courseTreeView;
    }

    /**
     * Handle a spererate check box for each course
     * 
     * @param selectedTreeDegreeModule the TreeDreeModule root represents for each
     *                                 course
     */
    public void handleCourses(TreeDegreeModule selectedTreeDegreeModule) {

        // TreeDegreeModule selectedTreeDegreeModule =
        // this.courseTreeView.treeDegreeModule;
        vbox.getChildren().clear();

        if (selectedTreeDegreeModule.subdegreeModule == null) {
            System.out.println("EMPTY");
            return;
        }

        for (TreeDegreeModule subModule : selectedTreeDegreeModule.subdegreeModule) {
            if (subModule.subdegreeModule == null) {

                DegreeModule course = subModule.degreeModule;

                CheckBox cb;
                cb = new CheckBox(subModule.toString());

                if (course.isChosen) {
                    cb.setSelected(true);
                }
                cb.setOnMouseClicked(e -> {
                    course.isChosen = !course.isChosen;
                    user.addChoosenCourse(course.id);
                    this.courseTreeView.updateTreeViewRoot();

                });

                // Subwindow for course information when the mouse hovers through
                final Stage courseInfoStage = new Stage();
                courseInfoStage.setX(400);
                courseInfoStage.setY(350);
                Label infoName = new Label("Name: " + subModule.toString());
                Label infoCode = new Label("Code: " + course.code);

                VBox courseInfoLayout = new VBox();
                courseInfoLayout.setAlignment(Pos.CENTER);
                courseInfoLayout.getChildren().addAll(infoName, infoCode);
                courseInfoStage.setScene(new Scene(courseInfoLayout, 350, 150));
                courseInfoStage.setTitle(subModule.toString());

                cb.setOnMouseEntered(e -> {
                    courseInfoStage.show();
                });

                cb.setOnMouseExited(e -> {
                    courseInfoStage.close();
                });

                vbox.getChildren().add(cb);
            }
        }
    }

}
