package UniversityFoodOrderingSystem.Model;

public class Receipt {
    String ReceiptID;
    String Amount;
    String time;
    String userID;

    public Receipt(String ReceiptID, String Amount, String time, String userID) {
        this.ReceiptID = ReceiptID;
        this.Amount = Amount;
        this.time = time;
        this.userID = userID;
    }

    public String getReceiptID() {
        return ReceiptID;
    }

    public void setReceiptID(String ReceiptID) {
        this.ReceiptID = ReceiptID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    
    
}
