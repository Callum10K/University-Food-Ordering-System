package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;

public class foodItem {
    private String foodItemID;
    private String foodItemName;
    private foodCuisineType cuisineType;
    private double price;
    private String vendorName;
    private String vendorUID;
    private foodCategory category;

    public foodItem(String foodItemID, String foodItemName, foodCuisineType cuisineType, double price, String vendorName, String vendorUID, foodCategory category) {
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.cuisineType = cuisineType;
        this.price = price;
        this.vendorName = vendorName;
        this.vendorUID = vendorUID;
        this.category = category;
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

    public foodCuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(foodCuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
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

    public static foodItem obtainFoodItemInfo(String foodItemID, String uid){
        for (foodItem foodItem : allFoodItems) {
            if (foodItemID.equals(foodItem.getFoodItemID()) && uid.equals(foodItem.getVendorUID())) {
                return new foodItem(foodItem.getFoodItemID(), foodItem.getFoodItemName(), foodItem.getCuisineType(),foodItem.getPrice(), foodItem.getVendorName(), foodItem.getVendorUID(), foodItem.getCategory());
            }
        }
        return null; // Return null if no user with matching credentials is found
    }
}