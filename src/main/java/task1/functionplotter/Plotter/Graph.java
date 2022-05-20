package task1.functionplotter.Plotter;

import task1.functionplotter.Parser.EquationParser;
import javafx.scene.chart.XYChart;

import java.math.BigDecimal;


/***
 * Class for plotting the graph of the function.
 */
public class Graph {

    private final XYChart<Double, Double> graph;
    private final int mini;
    private final int maxi;

    public Graph(final XYChart<Double, Double> graph, final int mini, final int maxi) {
        this.graph = graph;
        this.mini = mini;
        this.maxi = maxi;
    }

    /***
     * Plots the function.
     */
    public void plotFunction() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= (maxi - mini) * 10; x += 1) {
            double result = EquationParser.Evaluate(BigDecimal.valueOf(mini + x * 0.1));
            if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                graph.getData().add(series);
                series = new XYChart.Series<>();
            } else
                plotPoint(mini + x * 0.1, EquationParser.Evaluate(BigDecimal.valueOf(mini + x * 0.1)), series);
        }
        graph.getData().add(series);
    }

    /***
     * Plots the derivative of the function.
     */
    public void plotDerivative() {
        XYChart.Series<Double, Double> series = new XYChart.Series<>();
        for (int x = 0; x <= (maxi - mini) * 10; x += 1) {
            double result = EquationParser.EvaluateDerivative(BigDecimal.valueOf(mini + x * 0.1));
            if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY) {
                graph.getData().add(series);
                series = new XYChart.Series<>();
            } else
                plotPoint(mini + x * 0.1, EquationParser.EvaluateDerivative(BigDecimal.valueOf(mini + x * 0.1)), series);
        }
        graph.getData().add(series);
    }

    /***
     * Plots a point on the graph.
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param series the series to plot on.
     */
    private void plotPoint(final double x, final double y,
                           final XYChart.Series<Double, Double> series) {
        XYChart.Data<Double, Double> point = new XYChart.Data<>(x, y);
        //point.setNode(new ShowCoordinatesNode(x, y));
        series.getData().add(point);
    }


    /***
     * Clears any plotting in the graph
     */
    public void clear() {
        graph.getData().clear();
    }
}