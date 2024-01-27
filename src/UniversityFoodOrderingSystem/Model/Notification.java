package UniversityFoodOrderingSystem.Model;

public class Notification {
    private String NID;
    private String time;
    private String Notification;
    private String UID;

    public Notification(String NID, String time, String Notification, String UID) {
        this.NID = NID;
        this.time = time;
        this.Notification = Notification;
        this.UID = UID;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotification() {
        return Notification;
    }

    public void setNotification(String Notification) {
        this.Notification = Notification;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

   
    
}
