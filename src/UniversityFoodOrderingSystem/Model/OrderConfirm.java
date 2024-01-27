package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCategory;

public class OrderConfirm {
    private String customerID;
    private String orderID;
    private String foodItemID;
    private String foodItemName;
    private double price;
    private String vendorUID;
    private foodCategory category;
    private int quantity;
    private String option;
    private String time;
    private String status;
    private String DeliveryStatus;
    private String reviewVendor;
    private String reviewRunner;
    private String runnerID;

    public OrderConfirm(String customerID, String orderID, String foodItemID, String foodItemName, double price, String vendorUID, foodCategory category, int quantity, String option, String time, String status, String DeliveryStatus, String reviewVendor, String reviewRunner, String runnerID) {
        this.customerID = customerID;
        this.orderID = orderID;
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.price = price;
        this.vendorUID = vendorUID;
        this.category = category;
        this.quantity = quantity;
        this.option = option;
        this.time = time;
        this.status = status;
        this.DeliveryStatus = DeliveryStatus;
        this.reviewVendor = reviewVendor;
        this.reviewRunner = reviewRunner;
        this.runnerID = runnerID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(String foodItemID) {
        this.foodItemID = foodItemID;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendorUID() {
        return vendorUID;
    }

    public void setVendorUID(String vendorUID) {
        this.vendorUID = vendorUID;
    }

    public foodCategory getCategory() {
        return category;
    }

    public void setCategory(foodCategory category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryStatus() {
        return DeliveryStatus;
    }

    public void setDeliveryStatus(String DeliveryStatus) {
        this.DeliveryStatus = DeliveryStatus;
    }

    public String getReviewVendor() {
        return reviewVendor;
    }

    public void setReviewVendor(String reviewVendor) {
        this.reviewVendor = reviewVendor;
    }

    public String getReviewRunner() {
        return reviewRunner;
    }

    public void setReviewRunner(String reviewRunner) {
        this.reviewRunner = reviewRunner;
    }

    public String getRunnerID() {
        return runnerID;
    }

    public void setRunnerID(String runnerID) {
        this.runnerID = runnerID;
    }

}
