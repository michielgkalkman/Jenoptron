module org.taHjaj.wo.jenoptron.model {
    requires commons.collections;
    requires log4j;
    requires rxjava;
    requires xstream;
    requires commons.lang;
    requires commons.io;
    requires java.desktop;
    exports org.taHjaj.wo.jenoptron.model.core;
    exports org.taHjaj.wo.jenoptron.model.core.binary;
    exports org.taHjaj.wo.jenoptron.model.icore;
}