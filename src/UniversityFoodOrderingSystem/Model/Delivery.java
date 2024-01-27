package UniversityFoodOrderingSystem.Model;

public class Delivery {
    private String deliveryID;
    private String OrderID;
    private String vendorUID;
    private String customerID;
    private String deliveryStatus;
    private String time;
    private String location;
    private String runnerID;
    private String availability;

    public Delivery(String deliveryID, String OrderID, String vendorUID, String customerID, String deliveryStatus, String time, String location, String runnerID, String availability) {
        this.deliveryID = deliveryID;
        this.OrderID = OrderID;
        this.vendorUID = vendorUID;
        this.customerID = customerID;
        this.deliveryStatus = deliveryStatus;
        this.time = time;
        this.location = location;
        this.runnerID = runnerID;
        this.availability = availability;
    }

    public String getDeliveryID() {
        return deliveryID;
    }

    public void setDeliveryID(String deliveryID) {
        this.deliveryID = deliveryID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }

    public String getVendorUID() {
        return vendorUID;
    }

    public void setVendorUID(String vendorUID) {
        this.vendorUID = vendorUID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }    
}
