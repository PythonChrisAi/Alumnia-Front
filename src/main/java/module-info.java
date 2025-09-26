module com.csb.unit1.javafxalumnia {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    // Jackson
    requires com.fasterxml.jackson.databind;

    // Apache HttpClient5 (automatic modules por artifactId)
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    // Abrir tus modelos a Jackson
    opens com.csb.unit1.javafxalumnia.model to com.fasterxml.jackson.databind;

    // Abrir controladores a JavaFX
    opens com.csb.unit1.javafxalumnia.controller to javafx.fxml;

    // Exportar tus paquetes
    exports com.csb.unit1.javafxalumnia;
    exports com.csb.unit1.javafxalumnia.controller;
    exports com.csb.unit1.javafxalumnia.model;
}
