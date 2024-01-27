package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;

import javax.swing.table.DefaultTableModel;

import UniversityFoodOrderingSystem.mainClass;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static UniversityFoodOrderingSystem.FileIO.DataIO.*;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allReview;

public class Customer extends User {
    public Customer (String username, String uid, int password, userRoles userRole, foodCuisineType foodCuisine, double balance) {
        super(username, uid, password, userRoles.CUSTOMER, foodCuisineType.NONE, balance);
    }
    public static void displayMenuItems(DefaultTableModel tableModel) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (foodItem item : allFoodItems) {
            tableModel.addRow(new Object[]{item.getFoodItemID(), item.getFoodItemName(), item.getCuisineType() ,item.getPrice(), item.getVendorName(), item.getVendorUID(), item.getCategory()});
        }
    }
    public static void displaySelectedCuisineMenuItems(DefaultTableModel tableModel, foodCuisineType selectedCuisine) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and filter based on cuisine type
        for (foodItem item : allFoodItems) {
            if (item.getCuisineType().equals(selectedCuisine)) {
                tableModel.addRow(new Object[]{item.getFoodItemID(), item.getFoodItemName(), item.getCuisineType(), item.getPrice(), item.getVendorName(), item.getVendorUID(), item.getCategory()});
            }
        }
    }
    
    public static void displaySelectedOrderTime(DefaultTableModel tableModel, foodCuisineType selectedCuisine) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and filter based on cuisine type
        for (foodItem item : allFoodItems) {
            if (item.getCuisineType().equals(selectedCuisine)) {
                tableModel.addRow(new Object[]{item.getFoodItemID(), item.getFoodItemName(), item.getCuisineType(), item.getPrice(), item.getVendorName(), item.getVendorUID(), item.getCategory()});
            }
        }
    }
    
    public static void deleteOrder(String foodItemID) {
        Iterator<Order> iterator = allOrder.iterator();
        while (iterator.hasNext()) {
            Order item = iterator.next();
            if (item.getFoodItemID().equals(foodItemID)) {
                iterator.remove(); // Remove the matching order
                break; // Stop searching after the first match (foodItemID is unique)
            }
        }
    }
    
    public static void addOrder(String foodItemID, String foodItemName, double price, String vendorUID, foodCategory category, int quantity) {
        // Implement logic to add a new food item to the list
        allOrder.add((new Order(foodItemID, foodItemName, price, vendorUID, category, quantity)));
    }
    
    public static void confirmOrder(String customerID,String orderID, String foodItemID, String foodItemName, double price, String vendorUID, foodCategory category, int quantity, String option, String time, String status, String DeliveryStatus, String reviewVendor, String reviewRunner, String runnerID) {
        // Implement logic to add a new food item to the list
        allOrderConfirm.add((new OrderConfirm(customerID, orderID, foodItemID, foodItemName, price, vendorUID, category, quantity, option, time, status, DeliveryStatus, reviewVendor, reviewRunner, runnerID)));
    }
    
    public static void callForDelivery(String DID, String OID, String VID, String CID, String deliveryStatus, String time, String location, String RID, String Availability) {
        // Implement logic to add a new food item to the list
        allDeliveries.add(new Delivery(DID, OID, VID, CID, deliveryStatus, time, location, RID, Availability));
    }
    
    public static void getNotification(String NID, String time, String Notification, String CID) {
        // Implement logic to add a new food item to the list
        allNotification.add(new Notification(NID, time, Notification, CID));
        mainClass.customerDashboardPage.getNotificationRead(false);
    }
    
    public static void getReceipt(String RID, String Amount, String time, String CID) {
        // Implement logic to add a new food item to the list
        allReceipt.add(new Receipt(RID, Amount, time, CID));
    }
    
    public static void getTransaction(String TID, String time, String title, String transaction, String CID) {
        // Implement logic to add a new food item to the list
        allTransaction.add(new Transaction(TID, time, title, transaction, CID));
    }

    public static void addNewReview(String reviewerUID, String revieweeUID, int rating, String feedback, String OrderID, userRoles role){
        allReview.add(new Review(reviewerUID, revieweeUID, rating, feedback, OrderID, role));
    }

    public static void displayReview(DefaultTableModel tableModel) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve reviews and add them to the tableModel
        for (Review review : allReview) {
            tableModel.addRow(new Object[]{review.getReviewerUID(), review.getRevieweeUID(), review.getOrderID(), review.getRoles(),review.getRating(), review.getFeedback()});
        }

        // To sort review based on role type
        Collections.sort(allReview, Comparator.comparing(Review::getRoles));
    }

    public static void displaySelectedRoleReview(DefaultTableModel tableModel, userRoles selectedRole) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve reviews and add them to the tableModel
        for (Review review : allReview) {
            if(review.getRoles().equals(selectedRole)) {
                tableModel.addRow(new Object[]{review.getReviewerUID(), review.getOrderID(),review.getRevieweeUID(), review.getOrderID(), review.getRoles(), review.getRating(), review.getFeedback()});
            }
        }

    }

    public static void modifyReviewVendorStatus(String orderID, String vendorUID, String addedStatus){
        for (OrderConfirm oc : allOrderConfirm){
            if(oc.getOrderID().equals(orderID) && oc.getVendorUID().equals(vendorUID)){
                oc.setReviewVendor(addedStatus);
                break;
            }
        }
    }

    public static void modifyReviewRunnerStatus(String orderID, String runnerUID, String addedStatus){
        for (OrderConfirm oc : allOrderConfirm){
            if(oc.getOrderID().equals(orderID) && oc.getRunnerID().equals(runnerUID)){
                oc.setReviewRunner(addedStatus);
                break;
            }
        }
    }
}
