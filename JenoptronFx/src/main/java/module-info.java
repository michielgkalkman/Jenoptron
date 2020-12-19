module JenoptronFx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    requires org.taHjaj.wo.jenoptron.model;
    requires commons.lang;
    requires reactfx;
    requires de.saxsys.mvvmfx;

    opens dt.reactfx.dtview to javafx.fxml;
    exports dt.reactfx.dtview;
}