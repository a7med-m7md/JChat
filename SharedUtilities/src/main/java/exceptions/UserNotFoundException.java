package exceptions;

import java.rmi.RemoteException;

public class UserNotFoundException extends RemoteException {
    public UserNotFoundException(){
        super("User not found exception");
    }

}
