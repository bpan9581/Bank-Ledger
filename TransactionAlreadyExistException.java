// Brian Pan ID: 112856241 Recitation 2

/**
 * Checks to see if there is a duplicate object
 */
public class TransactionAlreadyExistException extends IllegalArgumentException {
    public TransactionAlreadyExistException(){
        super("Transaction already exist");
    }
}
