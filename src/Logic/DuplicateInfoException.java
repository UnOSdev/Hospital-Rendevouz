package Logic;
public class DuplicateInfoException extends RuntimeException {
    public DuplicateInfoException(String msg) {
        super("DuplicateInfoException: "+msg);
}
}
