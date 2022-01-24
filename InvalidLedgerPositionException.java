// Brian Pan ID: 112856241 Recitation 2

/**
 * Checks to see if position is invalid
 */
public class InvalidLedgerPositionException extends IllegalArgumentException {
    public InvalidLedgerPositionException(){
        super("The position entered is invalid");
    }
}
