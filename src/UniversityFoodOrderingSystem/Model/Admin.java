package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;

import javax.swing.table.DefaultTableModel;

import java.util.Iterator;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;

public class Admin extends User {
    public Admin (String name, String uid, int password, userRoles userRole, foodCuisineType foodCuisine, double balance) {
        super(name, uid, password, userRoles.ADMIN, foodCuisineType.NONE, balance);
    }
    public static void addNewUser(String name, String uid, int password, userRoles userRole, foodCuisineType foodCuisine, double balance){
        allUsers.add(new User(name, uid, password, userRole, foodCuisine, balance));
    }
    public static void displayUserInformationInTable(DefaultTableModel tableModel) {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Populate the table with user information
        for (User user : allUsers) {
            Object[] rowData = {user.getName(), user.getUid(), user.getPassword(), user.getRole(), user.getFoodCuisine()};
            tableModel.addRow(rowData);
        }
    }
    public static void deleteUser(String uid) {
        Iterator<User> iterator = allUsers.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUid().equals(uid)) {
                iterator.remove();
                break;
            }
        }
    }
    public static boolean isUserExists(String uid) {
        for (User user : allUsers) {
            if (user.getUid().equals(uid)) {
                return true; // User with the given UID exists
            }
        }
        return false; // User with the given UID does not exist
    }

    public static void modifyUserCredentials(String uid, String name, int password) {
        for (User user : allUsers) {
            if (user.getUid().equals(uid)) {
                user.setName(name);
                user.setPassword(password);
                break;
            }
        }
    }

    public static void replaceOldUIDWithNewModifiedUID(String uid, String name){
        for (foodItem foodItem : allFoodItems){
            if (foodItem.getVendorUID().equals(uid)){
                foodItem.setVendorName(name);
            }
        }
    }
    
    public static void addBalance(String uid, int amount) {
        for (User user : allUsers) {
            if (user.getUid().equals(uid)) {
                user.setBalance(user.getBalance() + amount);
                break;
            }
        }
    }
}
