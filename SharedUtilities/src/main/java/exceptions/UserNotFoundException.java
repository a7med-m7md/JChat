package exceptions;

import java.rmi.RemoteException;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super("User not found exception");
    }
}
