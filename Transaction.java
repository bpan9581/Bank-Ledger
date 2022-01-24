// Brian Pan ID: 112856241 Recitation 2
public class Transaction {
    private String date;
    private double amount;
    private String description;

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Transaction(){}

    public Transaction(String date, double amount, String description){
        this.date = date;
        this.amount = amount;
        this.description = description;
    }

    public Object clone(){
        Transaction clone = new Transaction(this.date,  this.amount, this.description);
        return clone;
    }

    public boolean equals(Object obj){
        if(obj instanceof Transaction){
            Transaction t = (Transaction) obj;
            if(t.description.equals(this.description) && t.amount == this.amount && t.date.equals(this.date))
                return true;
        }
        return false;
    }
}
