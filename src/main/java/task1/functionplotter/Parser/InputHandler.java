package task1.functionplotter.Parser;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/***
 * Class to handle all the inputs and data that coming from user.
 */
public class InputHandler {

    /***
     * Shows a warning message on the screen when called.
     * @param title the title of the warning.
     * @param msg any instruction to put in the body of the message.
     */
    public static void WrongInput(String title, String msg) {
        Alert.AlertType type = Alert.AlertType.WARNING;
        Alert alert = new Alert(type, msg);

        // Prevents user's default actions like mouse right-click.
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText(title);
        alert.showAndWait();
    }

    /***
     * Handles the significant figures input from user.
     * @param interval the text field of significant figures to handle its text.
     * @param Mode it is true when we want integer values and false if you want double.
     * @return false if there is no errors.
     */
    public static Boolean TextField(TextField interval, boolean Mode) {
        double num;

        // If the user didn't write anything then use the default and there is no error.
        if (interval.getText().equals("")) return false;

        // If the user entered non-numbers like characters.
        try {
            num = Double.parseDouble(interval.getText().strip());
        } catch (Exception e) {
            WrongInput("Wrong Data", "Please Write Numbers Only.");
            return true;
        }

        // If the user entered double-type number then it is an error in case if we want integer.
        if (num % 1 != 0 && Mode) {
            WrongInput("Wrong Data", "Please Write Integer Numbers.");
            return true;
        }
        return false;
    }
}
