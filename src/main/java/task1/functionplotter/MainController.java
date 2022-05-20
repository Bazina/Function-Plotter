package task1.functionplotter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * The controller for the main view.
 */
public class MainController implements Initializable {

    // References for the components in the FXML file.
    @FXML
    private ImageView exit;

    @FXML
    private AnchorPane MethodPane, UserManualPane, CurrentMethodPane;

    @FXML
    private JFXButton UserButton;

    @FXML
    private DialogPane UserManual;

    @FXML
    private ScrollPane Scroll;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // The scroll pane settings.
        Scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        Scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        CurrentMethodPane = MakePane();
        MethodPane.getChildren().add(CurrentMethodPane);


        // Sets the settings for the exit button.
        exit.setOnMouseClicked(e -> System.exit(0));
        exit.setOnMouseMoved(e -> exit.setBlendMode(BlendMode.HARD_LIGHT));
        exit.setOnMouseExited(e -> exit.setBlendMode(null));

        // Opens the user manual if the user wants it and closing if he pressed OK.
        UserManualPane.setVisible(false);
        Node OK = UserManual.lookupButton(ButtonType.OK);
        OK.setOnMouseClicked(e -> UserManualPane.setVisible(false));
        UserButton.setOnMouseClicked(e -> UserManualPane.setVisible(true));
    }

    /***
     * Loads the method fxml when calling it.
     * @return the view in an anchor pane.
     */
    private AnchorPane MakePane() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Graph.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (AnchorPane) root;
    }
}
