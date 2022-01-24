// Brian Pan ID: 112856241 Recitation 2
import java.util.*;

/**
 * This class represents a General Ledger Menu where you can manage
 * transactions through a menu
 */
public class GeneralLedgerManager {
    public static void main(String[] args) {
        Scanner stdin = new Scanner(System.in);
        GeneralLedger ledger = new GeneralLedger();
        GeneralLedger copyLedger = new GeneralLedger();
        boolean flag = false;
        do {
            System.out.println("(A) Add Transaction");
            System.out.println("(G) Get Transaction");
            System.out.println("(R) Remove Transaction");
            System.out.println("(P) Print Transaction");
            System.out.println("(F) Filter by Transaction Date");
            System.out.println("(L) Look for Transaction");
            System.out.println("(S) Size");
            System.out.println("(B) Backup");
            System.out.println("(PB) Print Transactions in Backup");
            System.out.println("(RB) Revert to Backup");
            System.out.println("(CB) Compare Backup with Current");
            System.out.println("(PF) Print Financial Information");
            System.out.println("(Q) Quit");
            System.out.println('\n' + "Enter a selection: ");
            String selection = stdin.nextLine();
            selection= selection.toUpperCase();
            System.out.println();
            switch (selection) {
                case "A":
                    try{
                    System.out.println("Enter the date: ");
                    String date = stdin.nextLine();
                    System.out.println("Enter the amount: ");
                    double amount = stdin.nextDouble();
                    System.out.println("Enter the description: ");
                    stdin.nextLine();
                    String description = stdin.nextLine();
                    Transaction add = new Transaction(date, amount, description);
                    ledger.addTransaction(add);
                    System.out.println("Transaction added" + '\n');
                    }
                    catch (FullGeneralLedgerException ex) {
                        System.out.println("General Ledger is already full" + '\n');
                    }
                    catch (InvalidTransactionException ex) {
                        System.out.println("Transaction does not follow the " +
                                "correct format" + '\n');
                    }
                    catch (TransactionAlreadyExistException ex) {
                        System.out.println("Transaction already exist" + '\n');
                    }
                    catch (InputMismatchException ex){
                        System.out.println("Invalid input" + '\n');
                    }
                    catch (IndexOutOfBoundsException ex){
                        System.out.println("Invalid input" + '\n');
                    }

                    break;
                case "G":
                    System.out.println("Enter the position");
                    int position = stdin.nextInt();
                    position--;
                    stdin.nextLine();
                    try{
                        Transaction temp = ledger.getTransaction(position);
                        System.out.println("Date: " + temp.getDate());
                        System.out.println("Amount: " + temp.getAmount());
                        System.out.println("Description: " +
                                temp.getDescription() + '\n');
                    }
                    catch(InvalidLedgerPositionException ex){
                        System.out.println("Position is invalid");
                }
                    break;
                case "R":
                    System.out.println("Enter the position of the transaction: ");
                    int removePosition = stdin.nextInt();
                    removePosition--;
                    stdin.nextLine();
                    ledger.removeTransaction(removePosition);
                    System.out.println();
                    break;
                case "P" :
                    ledger.printAllTransactions();
                    System.out.println();
                    break;
                case "F":
                    System.out.println("Enter a date: ");
                    String filterDate = stdin.nextLine();
                    GeneralLedger.filter(ledger, filterDate);
                    System.out.println();
                    break;
                case "L" :
                    System.out.println("Enter the date: ");
                    String findDate = stdin.nextLine();
                    System.out.println("Enter the amount: ");
                    double findAmount = stdin.nextDouble();
                    stdin.nextLine();
                    System.out.println("Enter the description: ");
                    String findDescription = stdin.nextLine();
                    Transaction findTransaction = new Transaction(findDate,
                            findAmount, findDescription);
                    if(ledger.exists(findTransaction))
                        System.out.println("Transaction is in current ledger"
                                + '\n');
                    else
                        System.out.println("Transaction is NOT in current ledger"
                                + '\n');
                    break;
                case "S":
                    System.out.println("The size is " + ledger.size() + '\n');
                    break;
                case "B":
                    copyLedger = new GeneralLedger();
                    Transaction[] clone = (Transaction[]) ledger.clone();

                    for(int i = 0; i < ledger.getTotalTransactions(); i++){
                        copyLedger.addTransaction(clone[i]);
                    }
                    System.out.println("Ledger is backed up" + '\n');

                    break;
                case "PB":
                    copyLedger.printAllTransactions();
                    System.out.println();
                    break;
                case "RB":
                    for(int i= 0; i < ledger.getTotalTransactions(); i++){
                        ledger.removeTransaction(i);
                    }
                    ledger.setTotalTransactions(0);
                    for(int i = 0; i < copyLedger.getTotalTransactions(); i++){
                        ledger.addTransaction(copyLedger.getTransaction(i));
                    }
                    System.out.println("Ledger reverted to backup" + '\n');
                    break;
                case "CB":
                    boolean equals = true;
                    if(copyLedger.getTotalTransactions() !=
                            ledger.getTotalTransactions())
                        equals = false;
                    else for(int i = 0; i < ledger.getTotalTransactions(); i++){
                        if (!ledger.getTransaction(i).equals(copyLedger.getTransaction(i)))
                            equals = false;
                    }
                    if (equals)
                        System.out.println("The backup is the same as current ledger"
                                + '\n');
                    else
                        System.out.println("The backup ledger and current ledger " +
                                "are different" + '\n');
                    break;
                case "PF" :
                    System.out.printf("%-12s %.2f" + '\n', "Assets: ", ledger.getTotalDebitAmount());
                    System.out.printf("%-12s %.2f" + '\n', "Liabilities: " , Math.abs(ledger.getTotalCreditAmount()));
                    System.out.printf("%-12s %.2f" + '\n' + '\n', "Net Worth: ", ledger.getTotalDebitAmount()
                            - Math.abs(ledger.getTotalCreditAmount()));

                    break;
                case "Q":
                    flag = true;
                    System.out.println("Program Terminating Successfully..." );
                    break;

                default:
                    System.out.println("Invalid input" + '\n');
                    break;
            }
        }
        while(!flag);
        stdin.close();
    }
}
