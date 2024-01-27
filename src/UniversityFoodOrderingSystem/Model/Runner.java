package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.table.DefaultTableModel;

import static UniversityFoodOrderingSystem.FileIO.DataIO.*;

public class Runner extends User {
    public Runner (String username, String uid, int password, userRoles userRole, foodCuisineType foodCuisine, double balance) {
        super(username, uid, password, userRoles.RUNNER, foodCuisineType.NONE, balance);
    }
    
    public static void getNotification(String NID, String time, String Notification, String RID) {
        // Implement logic to add a new food item to the list
        allNotification.add(new Notification(NID, time, Notification, RID));
        mainClass.runnerDashboardPage.getNotificationRead(false);
    }

    public static void displayCustomerReviewInTable(DefaultTableModel tableModel, String uid) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Populate the table with the vendor's menu items
        for (Review r : allReview) {
            if (r.getRevieweeUID().equals(uid)) {
                tableModel.addRow(new Object[]{r.getReviewerUID(), r.getOrderID(), r.getRating(), r.getFeedback()});
            }
        }
        //to sort vendor's menu items based on Vendor ID
        //Collections.sort(allFoodItems, Comparator.comparing(foodItem::getVendorUID));
    }
}
