// Brian Pan ID: 112856241 Recitation 2

/**
 * Creates and argument to check if the General Ledger object is full
 */
public class FullGeneralLedgerException extends IllegalArgumentException {
    public FullGeneralLedgerException(){
        super("The General Ledger is Full");
    }
}
