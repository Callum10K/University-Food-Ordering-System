package UniversityFoodOrderingSystem.Model;

public class Transaction {
    private String TransactionID;
    private String time;
    private String title;
    private String transaction;
    private String uid;
    
    public Transaction(String TransactionID, String time, String title, String transaction, String uid) {
        this.TransactionID = TransactionID;
        this.time = time;
        this.title = title;
        this.transaction = transaction;
        this.uid = uid;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    } 
}
