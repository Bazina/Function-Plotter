module task1.functionplotter {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.jfoenix;

    opens task1.functionplotter to javafx.fxml;
    exports task1.functionplotter;

    opens task1.functionplotter.Plotter to javafx.fxml;
    exports task1.functionplotter.Plotter;

    opens task1.functionplotter.Parser to javafx.fxml;
    exports task1.functionplotter.Parser;
}