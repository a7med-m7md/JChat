package exceptions;

public class DuplicationUserException extends Exception {
    public DuplicationUserException(){
        super("User not found exception");
    }
}
