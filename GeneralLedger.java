// Brian Pan ID: 112856241 Recitation 2


import java.util.InputMismatchException;
////////////////////////////////////////////////////////////////////////////////
/**
 * This class represents a General Ledger with an array of Transactions, and
 * values for Credit and Debit
 */
public class GeneralLedger {
    private static final int MAX_TRANSACTIONS = 50;

    private Transaction[] ledger = new Transaction[MAX_TRANSACTIONS];
    private double totalDebitAmount = 0;
    private double totalCreditAmount = 0;
    private int totalTransactions = 0;

    /**
     *
     * @return
     * An empty array of Transaction objects
     */
    public Transaction[] getLedger() {
        return ledger;
    }

    /**
     *
     * @param ledger
     * An array of Transaction objects
     */
    public void setLedger(Transaction[] ledger) {
        this.ledger = ledger;
    }

    /**
     *
     * @return
     * double values of assets
     */
    public double getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(double totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    /**
     *
     * @return
     * double value of liabilities
     */
    public double getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void setTotalCreditAmount(double totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    /**
     *
     * @return
     * The total amount of transaction possible in this array
     */
    public static int getMaxTransactions() {
        return MAX_TRANSACTIONS;
    }

    /**
     *
     * @return
     * The number of Transaction objects in the ledger
     */
    public int getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(int totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public GeneralLedger() {
        this.totalTransactions = 0;
    }

    /**
     *
     * @param newTransaction
     * A Transaction object with parameters of Date, Amount, and Description
     */
    public void addTransaction(Transaction newTransaction) {
        if (totalTransactions == MAX_TRANSACTIONS)
            throw new FullGeneralLedgerException();

        if (newTransaction.getAmount() == 0 ||
                !(newTransaction.getDate().substring(4,5).compareTo("/") == 0 &&
                        newTransaction.getDate().substring(7,8).compareTo("/") == 0 &&
                        Integer.parseInt(newTransaction.getDate().substring(0, 4)) <= 2050 &&
                        Integer.parseInt(newTransaction.getDate().substring(0, 4)) >= 1900 &&
                        Integer.parseInt(newTransaction.getDate().substring(5, 7)) <= 12 &&
                        Integer.parseInt(newTransaction.getDate().substring(5,7)) >= 1 &&
                        Integer.parseInt(newTransaction.getDate().substring(8, 10)) <= 30 &&
                        Integer.parseInt(newTransaction.getDate().substring(8, 10)) >= 1 &&
                        newTransaction.getDate().length() == 10))
            throw new InvalidTransactionException();

        for (int i = 0; i < this.getTotalTransactions(); i++)
            if (newTransaction.equals(ledger[i]))
                throw new TransactionAlreadyExistException();
        if(totalTransactions == 0){
            ledger[0] = newTransaction;
            totalTransactions++;
            if(newTransaction.getAmount() < 0)
                totalCreditAmount = totalCreditAmount + newTransaction.getAmount();
            else
                totalDebitAmount = totalDebitAmount + newTransaction.getAmount();

        }
        else try {
            ledger[totalTransactions] = newTransaction;
            Transaction temp;
            totalTransactions++;
            if(newTransaction.getAmount() < 0)
                totalCreditAmount = totalCreditAmount + newTransaction.getAmount();
            else
                totalDebitAmount = totalDebitAmount + newTransaction.getAmount();

            for (int i = 0; i < totalTransactions; i++) {
                for (int j = i + 1; j < totalTransactions; j++) {
                    if (ledger[j].getDate().compareTo(ledger[i].getDate()) < 0) {
                        temp = ledger[i];
                        ledger[i] = ledger[j];
                        ledger[j] = temp;
                    }
                }
            }
        } catch (FullGeneralLedgerException ex) {
            System.out.print("General Ledger is already full");
        } catch (InvalidTransactionException ex) {
            System.out.print("Transaction does not follow the correct format");
        } catch (TransactionAlreadyExistException ex) {
            System.out.print("Transaction already exist");
        } catch (InputMismatchException ex) {
            System.out.print("Invalid Input");
        }
    }

    /**
     *
     * @param position
     * Any integer value possible within the array of Transaction objects
     */
    public void removeTransaction(int position) {
        if (position > this.totalTransactions)
            throw new InvalidLedgerPositionException();
        if (this.totalTransactions - position == 1) {
            if (ledger[position].getAmount() < 0)
                totalCreditAmount = totalCreditAmount - ledger[position].getAmount();
            else
                totalDebitAmount = totalDebitAmount - ledger[position].getAmount();
            ledger[position] = new Transaction();
        }

        else {
            try {
                if (ledger[position].getAmount() < 0)
                    totalCreditAmount = totalCreditAmount - ledger[position].getAmount();
                else
                    totalDebitAmount = totalDebitAmount - ledger[position].getAmount();
                ledger[position] = new Transaction();
                for (int i = position; i < this.getTotalTransactions() - 1; i++) {
                    ledger[i] = ledger[i + 1];
                }
                ledger[totalTransactions - 1] = new Transaction();
                totalTransactions--;
                System.out.println("Transaction removed" + '\n');
            } catch (InvalidLedgerPositionException ex) {
                System.out.print("The position entered is out of bounds");
            }
        }
    }

    /**
     *
     * @param position
     * Any integer value possible within the array of Transaction objects
     * @return
     * The Transaction object in a certain position in the array of objects
     */
    public Transaction getTransaction(int position){
        if (position > totalTransactions || position < 0) {
            throw new InvalidLedgerPositionException();
        }
        return ledger[position];
    }

    /**
     *
     * @param generalLedger
     * A object with parameters of an array of Transactions and debit and credit amounts
     * @param date
     * A string in the format yyyy/mm/dd
     */

    public static void filter(GeneralLedger generalLedger, String date) {
        int counter = 1;
        System.out.printf("%-8s%-15s%-10s%-21s", "No.", "Date", "Debit", "Credit",
                "Description" );
        System.out.println();
        System.out.println("------------------------------------------" +
                "---------------------------" +
                "------------------------------");
        for (int i = 0; i < generalLedger.getTotalTransactions(); i++) {
            if (date.compareTo(generalLedger.getTransaction(i).getDate()) == 0) {
                if (generalLedger.getTransaction(i).getAmount() > 0) {
                    System.out.printf("%-8d%-15s%-31.2f", counter,
                            generalLedger.getTransaction(i).getDate(),
                            generalLedger.getTransaction(i).getAmount(),
                            generalLedger.getTransaction(i).getDescription());
                    System.out.println();
                    counter++;
                }
                if (generalLedger.getTransaction(i).getAmount() < 0) {
                    System.out.printf("%-8d%-25s%-21.2f", counter,
                            generalLedger.getTransaction(i).getDate(),
                            Math.abs(generalLedger.getTransaction(i).getAmount()),
                            generalLedger.getTransaction(i).getDescription());
                    System.out.println();
                    counter++;
                }
            }
        }
    }

    /**
     *
     * @return
     * A copy of a GeneralLedger object
     */
    public Object clone() {
        Transaction[] copy = new Transaction[MAX_TRANSACTIONS];
        for (int i = 0; i < totalTransactions; i++)
            copy[i] = (Transaction) ledger[i].clone();
        return copy;
    }

    /**
     *
     * @param transaction
     * A object with parameters of Date, Amount, and Description
     * @return
     * Either true or false, if the  Transaction Object exist
     * within the GeneralLedger object
     */
    public boolean exists(Transaction transaction) {
        try {
            for (int i = 0; i < totalTransactions; i++) {
                if (transaction.equals(ledger[i]))
                    return true;
            }
        } catch (IllegalArgumentException ex) {
            System.out.print("Transaction is not valid");
        }
        return false;
    }

    /**
     *
     * @return
     * An integer value referencing the number of Transaction objects
     */
    public int size() {
        return totalTransactions;
    }

    /**
     * Prints a neatly formatted table of Transactions
     */
    public void printAllTransactions() {
        System.out.println(toString());
    }

    /**
     *
     * @return
     * A string representation of GeneralLedger
     */
    public String toString(){
        int counter = 1;
        String printAll = ""
        + String.format("%-8s%-15s%-10s%-11s%s", "No.", "Date", "Debit",
                "Credit", "Description" + '\n')
                + "------------------------------------------------------" +
                "---------------" +
                "------------------------------\n";
        for (int i = 0; i < totalTransactions; i++) {
            if (this.ledger[i].getAmount() > 0) {
                printAll = printAll + String.format("%-8d%-15s%-21.2f%s",
                        counter, this.ledger[i].getDate(),
                        this.ledger[i].getAmount(),
                        this.ledger[i].getDescription() +'\n');

                counter++;
            }
            if (this.ledger[i].getAmount() < 0) {
                printAll = printAll + String.format("%-8d%-25s%-11.2f%s",
                        counter, this.ledger[i].getDate(),
                        Math.abs(this.ledger[i].getAmount()),
                        this.ledger[i].getDescription() + '\n');
                counter++;
            }
        }
        return  printAll;
    }
}
