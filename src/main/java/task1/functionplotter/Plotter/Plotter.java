package task1.functionplotter.Plotter;

import javafx.scene.layout.AnchorPane;
import task1.functionplotter.Parser.EquationParser;
import task1.functionplotter.Parser.InputHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/***
 * Class to create the plot.
 */
public class Plotter implements Initializable {
    public AnchorPane MainPane;
    @FXML
    private LineChart<Double, Double> lineGraph;

    @FXML
    private TextField Equation, IntervalFrom, IntervalTo;

    @FXML
    private ToggleButton PlotDerivative;

    private Graph mathsGraph;

    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    }

    /***
     * Starts the plot progress.
     */
    public void Plot() throws IOException {
        if (mathsGraph != null) mathsGraph.clear();
        EquationParser.SetEquation(Equation.getText().strip());

        try {
            EquationParser.Evaluate(BigDecimal.valueOf(1));
        } catch (Exception e) {
            InputHandler.WrongInput("Wrong Equation", "Please Write a Valid Equation");
            return;
        }

        if (InputHandler.TextField(IntervalFrom, false) || InputHandler.TextField(IntervalTo, false))
            return;

        if (Objects.equals(Equation.getText().strip(), "") || Objects.equals(IntervalFrom.getText().strip(), "")
                || Objects.equals(IntervalTo.getText().strip(), "")) {
            InputHandler.WrongInput("Missing Data", "Please Write all Inputs");
            return;
        }

        lineGraph.setVisible(true);
        try {
            mathsGraph = new Graph(lineGraph,
                    Integer.parseInt(IntervalFrom.getText()), Integer.parseInt(IntervalTo.getText()));
            mathsGraph.plotFunction();
            if (PlotDerivative.isSelected())
                mathsGraph.plotDerivative();
        } catch (Exception e) {
            InputHandler.WrongInput("Missing Data", "Please Write A Valid Equation");
        }
    }
}
