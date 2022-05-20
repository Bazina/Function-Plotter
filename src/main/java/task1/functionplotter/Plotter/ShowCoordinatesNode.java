package task1.functionplotter.Plotter;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.text.DecimalFormat;

public class ShowCoordinatesNode extends StackPane {

    public ShowCoordinatesNode(double x, double y) {

        final Label label = createDataThresholdLabel(x, y);

        setOnMouseEntered(mouseEvent -> {
            setScaleX(1);
            setScaleY(1);
            getChildren().setAll(label);
            setCursor(Cursor.NONE);
            toFront();
        });
        setOnMouseExited(mouseEvent -> {
            getChildren().clear();
            setCursor(Cursor.CROSSHAIR);
        });
    }

    /***
     * Creates a label with the coordinates of the mouse.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the label with the coordinates.
     */
    private Label createDataThresholdLabel(double x, double y) {
        DecimalFormat df = new DecimalFormat("0.##");
        final Label label = new Label("(" + df.format(x) + "; " + df.format(y) + ")");
        label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
        label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
        return label;
    }
}
