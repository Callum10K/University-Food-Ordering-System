package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;

import javax.swing.table.DefaultTableModel;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static UniversityFoodOrderingSystem.FileIO.DataIO.*;


public class Vendor extends User {

    public Vendor (String username, String uid, int password, userRoles userRole, foodCuisineType foodCuisine, double balance) {
        super(username, uid, password, userRoles.VENDOR, foodCuisineType.NONE, balance);
    }
    public static void displayVendorMenuInTable(DefaultTableModel tableModel, String vendorUID) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Populate the table with the vendor's menu items
        for (foodItem f : allFoodItems) {
            if (f.getVendorUID().equals(vendorUID)) {
                Object[] rowData = {f.getFoodItemID(), f.getFoodItemName(), f.getCuisineType(), f.getPrice(), f.getCategory()};
                tableModel.addRow(rowData);
            }
        }

        //to sort vendor's menu items based on Vendor ID
        Collections.sort(allFoodItems, Comparator.comparing(foodItem::getVendorUID));
    }
    
    public static void displayCustomerOrderInTable(DefaultTableModel tableModel) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Populate the table with the vendor's menu items
        for (OrderConfirm o : allOrderConfirm) {
            tableModel.addRow(new Object[]{o.getCustomerID(), o.getTime(), o.getStatus()});
        }
        //to sort vendor's menu items based on Vendor ID
        //Collections.sort(allFoodItems, Comparator.comparing(foodItem::getVendorUID));
    }
    
    public static void displayOrderTime(DefaultTableModel tableModel, String VID, String OID) {
        Set<String> uniqueOrderIDs = new HashSet<>();

        // Retrieve the menu items and add them to the tableModel
        for (OrderConfirm item: allOrderConfirm) {
            if (item.getVendorUID().equals(VID) && !item.getStatus().equals("Canceled") && item.getOrderID().equals(OID)) {
                String orderID = item.getOrderID();
                if (uniqueOrderIDs.add(orderID)) {
                    // If it's not in the set, add the row to the tableModel
                    tableModel.addRow(new Object[]{item.getOrderID(), item.getCustomerID(), item.getOption(), item.getTime(), item.getStatus(), item.getReviewVendor()});
                }
                //tableModel.addRow(new Object[]{item.getOrderID(), item.getVendorUID(), item.getOption(), item.getTime(), item.getStatus()});
            }
        }
    }
    
    public static void getNotification(String NID, String time, String Notification, String VID) {
        // Implement logic to add a new food item to the list
        allNotification.add(new Notification(NID, time, Notification, VID));
        mainClass.vendorDashboardPage.getNotificationRead(false);
    }

    public static void addFoodItem(String foodItemID, String foodItemName, foodCuisineType cuisineType, double price, String vendorName, String vendorUID, foodCategory category) {
        // Implement logic to add a new food item to the list
        allFoodItems.add((new foodItem(foodItemID, foodItemName, cuisineType, price, vendorName, vendorUID, category)));
    }

    public static void deleteFoodItem(String vendorUID, String foodItemID) {
        Iterator<foodItem> iterator = allFoodItems.iterator();
        while (iterator.hasNext()) {
            foodItem item = iterator.next();
            if (item.getVendorUID().equals(vendorUID) && item.getFoodItemID().equals(foodItemID)) {
                iterator.remove(); // Remove the matching food item
                break; // Stop searching after the first match (foodItemID is unique)
            }
        }
    }

    public static void modifyFoodItemAttributes(String foodItemID, String foodItemName, double price, String vendorUID, foodCategory category) {
        for (foodItem item : allFoodItems) {
            if (item.getFoodItemID().equals(foodItemID) && item.getVendorUID().equals(vendorUID)) {
                item.setFoodItemName(foodItemName);
                item.setPrice(price);
                item.setCategory(category);
                break;
            }
        }
    }

    public static boolean isFoodItemExists(String foodItemID) {
        for (foodItem item : allFoodItems) {
            if (item.getFoodItemID().equals(foodItemID)) {
                return true; // Food item with the given ID exists
            }
        }
        return false; // Food item with the given ID does not exist
    }

    public static void displayVendorReviewInTable(DefaultTableModel tableModel, String uid) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Populate the table with the vendor's menu items
        for (Review r : allReview) {
            if(r.getRevieweeUID().equals(uid)) {
                tableModel.addRow(new Object[]{r.getReviewerUID(), r.getOrderID(), r.getRating(), r.getFeedback()});
            }
        }
        //to sort vendor's menu items based on Vendor ID
        //Collections.sort(allFoodItems, Comparator.comparing(foodItem::getVendorUID));
    }
}
