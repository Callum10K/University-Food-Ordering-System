package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCategory;

public class Order {
    private String foodItemID;
    private String foodItemName;
    private double price;
    private String vendorUID;
    private foodCategory category;
    private int quantity;
    
    public Order(String foodItemID, String foodItemName, double price, String vendorUID, foodCategory category, int quantity) {
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.price = price;
        this.vendorUID = vendorUID;
        this.category = category;
        this.quantity = quantity;
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
}
