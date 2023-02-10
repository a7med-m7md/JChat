module SharedUtilities {
    requires java.sql;
    requires java.rmi;
    requires javafx.base;

//    exports com.java.iti;
    exports model;
    exports services;
    exports exceptions;
    exports model.user;
  
    exports model.group;
    exports utils;
}