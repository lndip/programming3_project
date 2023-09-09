package fi.tuni.prog3.sisu;

import fi.tuni.prog3.modules.CourseUnit;
import fi.tuni.prog3.modules.DegreeModule;
import fi.tuni.prog3.modules.TreeDegreeModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class for displaying the tree view of the degree structure after a user
 * chooses
 */
public class CourseTreeView {

    /**
     * The tree degree module
     */
    public TreeDegreeModule treeDegreeModule;

    /**
     * TreeView element to demonstrate the tree degree module
     */
    public TreeView<TreeDegreeModule> treeView = new TreeView<>();
    /**
     * A Tickboxes to handle the courses' checked boxes
     */
    private CourseTickBoxes tickBoxesController;

    /**
     * Constructor of CourseTreeView
     * 
     * @param pane       The pane that contains the CourseTreeView
     * @param layoutX    The horizontal layout value
     * @param layoutY    The vertical layout value
     * @param prefWidth  The preferred width of the space for displaying the tree
     *                   view
     * @param prefHeight The preferred height of the space for displaying the tree
     *                   view
     * @param tickboxes  The tick boxes
     */
    public CourseTreeView(Pane pane,
            int layoutX,
            int layoutY,
            int prefWidth,
            int prefHeight,
            CourseTickBoxes tickboxes) {
        VBox vbox = new VBox(this.treeView);
        vbox.setMinSize(prefWidth, prefHeight);
        vbox.setLayoutX(layoutX);
        vbox.setLayoutY(layoutY);

        pane.getChildren().add(vbox);
        EventHandler<MouseEvent> mouseEventHandle = (MouseEvent event) -> {
            handleMouseClicked(event);
        };

        this.treeView.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEventHandle);

        this.tickBoxesController = tickboxes;
    }

    /**
     * Clear the tree view
     */
    public void clearTreeView() {
        this.treeView.setRoot(null);
    }

    /**
     * Expand the tree view
     * 
     * @param item the TreeDegreeModule to expand the tree
     * @return inital TreeDegreeModule
     */
    private TreeItem<TreeDegreeModule> expandTreeView(TreeItem<TreeDegreeModule> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (TreeItem<TreeDegreeModule> child : item.getChildren()) {
                expandTreeView(child);
            }
        }

        return item;
    }

    /**
     * Build the tree view recursively for the GUI using the root of
     * TreeDegreeModule
     * 
     * @param root The root of TreeDegreeModule
     * @return the expanded root for the treeView
     */
    public TreeItem<TreeDegreeModule> getTreeViewRoot(TreeDegreeModule root) {
        TreeItem<TreeDegreeModule> treeViewRoot = new TreeItem<>(root);

        for (TreeDegreeModule sub : root.subdegreeModule) {
            if (sub.subdegreeModule != null) {
                treeViewRoot.getChildren().add(getTreeViewRoot(sub));
            } else {
                if (sub.degreeModule.isChosen) {
                    TreeItem<TreeDegreeModule> courseNode = new TreeItem<>(sub);
                    treeViewRoot.getChildren().add(courseNode);
                }
            }
        }

        return expandTreeView(treeViewRoot);
    }

    /**
     * Populate data given degreeID of a degree without tracks
     * 
     * @param degree_id degreeID
     * @throws IOException when unable to read data
     */
    public void populateData(String degree_id) throws IOException {
        this.treeDegreeModule = Factory.readTreeDegreeModule(degree_id);
        this.treeView.setRoot(getTreeViewRoot(this.treeDegreeModule));
        System.out.println("Populate data done");
    }

    /**
     * Populate data given degreeID of a degree with chosen track
     * 
     * @param degree_id degreeID
     * @param track_id  trackID
     * @throws IOException when unable to read data
     */
    public void populateData(String degree_id, String track_id) throws IOException {

        for (TreeDegreeModule subModule : Factory.readTreeDegreeModule(degree_id).subdegreeModule) {
            if (subModule.degreeModule.id.equals(track_id)) {
                System.out.println(subModule.toString());
                List<TreeDegreeModule> trackModule = new ArrayList<>();
                trackModule.add(subModule);
                System.out.println((trackModule.size()));
                this.treeDegreeModule = new TreeDegreeModule(Factory.readDegreeModule(degree_id), trackModule);
                this.treeView.setRoot(getTreeViewRoot(this.treeDegreeModule));
                System.out.println("Populate data done");
                break;
            }
        }
    }

    /**
     * Update the tree view root
     */
    public void updateTreeViewRoot() {
        this.treeView.setRoot(getTreeViewRoot(this.treeDegreeModule));
    }

    private TreeDegreeModule getSelectedTreeModule() {
        return this.treeView.getSelectionModel().getSelectedItem().getValue();
    }

    private void handleMouseClicked(MouseEvent event) {
        Node node = event.getPickResult().getIntersectedNode();
        // Accept clicks only on node cells, and not on empty spaces of the TreeView
        if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
            this.tickBoxesController.handleCourses(getSelectedTreeModule());
        }
    }

    /**
     * Loads the information about the current user (if available) and sets all the
     * chosen courses (if available)
     * 
     * @param user the current user
     * @throws IOException when reading the API's URL is failed
     */
    public void loadUser(User user) throws IOException {
        User.StudentInformation studentInfo = user.getStudentInformation();
        if (studentInfo.trackId == null) {
            populateData(studentInfo.degreeId);
        } else {
            populateData(studentInfo.degreeId, studentInfo.trackId);
        }
        setChoosenCourseFromUser(this.treeDegreeModule, new HashSet<>(studentInfo.chosenCourses));
        updateTreeViewRoot();
    }

    /**
     * Choose the courses that have been selected by the specified user
     * 
     * @param root          the root node of the TreeDegreeModule
     * @param choosenCourse the set of courses that have been choosen
     */
    public void setChoosenCourseFromUser(TreeDegreeModule root, HashSet<String> choosenCourse) {
        if (choosenCourse.contains(root.degreeModule.id)) {
            choosenCourse.remove(root.degreeModule.id);
            root.degreeModule.isChosen = true;
        }

        if (root.subdegreeModule == null) {
            return;
        }

        for (TreeDegreeModule subModule : root.subdegreeModule) {
            setChoosenCourseFromUser(subModule, choosenCourse);
        }
    }

}
