module SharedUtilities {
    requires java.sql;
    requires java.rmi;
    requires javafx.base;
    requires javafx.graphics;

//    exports com.java.iti;
    exports model;
    exports services;
    exports exceptions;
    exports model.user;

    exports utils;
}