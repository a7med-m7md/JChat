package exceptions;

public class DuplicateUserException extends Exception {
    public DuplicateUserException(){
        super("User with the Same number exists");
    }
}
