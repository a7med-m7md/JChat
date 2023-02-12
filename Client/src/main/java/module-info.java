//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;
    requires SharedUtilities;
    requires java.desktop;
    requires javafx.swing;
//    requires ab;
//    requires bot;

    exports Client;
    exports Client.ui.controllers;
    exports Client.ui.controllerutils;
    opens Client to
            javafx.fxml;
    opens Client.ui.controllers to
            javafx.fxml;
}
