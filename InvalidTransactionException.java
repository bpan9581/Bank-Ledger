// Brian Pan ID: 112856241 Recitation 2

/**
 * Checks to see if the Transaction object follows the correct format
 */
public class InvalidTransactionException extends IllegalArgumentException {
    public  InvalidTransactionException(){
        super("Transaction is invalid");
    }
}
