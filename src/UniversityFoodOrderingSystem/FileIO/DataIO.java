package UniversityFoodOrderingSystem.FileIO;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.Model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static UniversityFoodOrderingSystem.Enum.foodCuisineType.NONE;
import static UniversityFoodOrderingSystem.Enum.foodCuisineType.JAPANESE;
import static UniversityFoodOrderingSystem.Enum.userRoles.*;

public class DataIO{
    public static ArrayList<User> allUsers = new ArrayList<User>();
    public static ArrayList<foodItem> allFoodItems = new ArrayList<foodItem>();
    public static ArrayList<Order> allOrder = new ArrayList<Order>();
    public static ArrayList<OrderConfirm> allOrderConfirm = new ArrayList<OrderConfirm>();
    public static ArrayList<Notification> allNotification = new ArrayList<Notification>();
    public static ArrayList<Receipt> allReceipt = new ArrayList<Receipt>();
    public static ArrayList<Transaction> allTransaction = new ArrayList<Transaction>();
    public static ArrayList<Delivery> allDeliveries = new ArrayList<Delivery>();

    public static ArrayList<Review> allReview = new ArrayList<Review>();


    public static void initialiseUserFile() {
        try {
            File userFile = new File("user.txt");

            // Check if the file doesn't exist
            if (!userFile.exists()) {
                userFile.createNewFile();
                // Write default user information to the arrayList
                try (PrintWriter p1 = new PrintWriter(userFile)) {
                    p1.println();
                    allUsers.add(new User("Sean", "AD1", 2023, ADMIN, NONE, 0.0));
                    allUsers.add(new User("Sean", "RN1", 2023, RUNNER, NONE, 0.0));
                    allUsers.add(new User("Sean", "VD1", 2023, VENDOR, JAPANESE, 0.0));
                    allUsers.add(new User("Sean", "CT1", 2023, CUSTOMER, NONE, 0.0));
                }

                System.out.println("user.txt created with default user information.");
            } else {
                System.out.println("user.txt already exists. No action needed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void readFile() {
        try (Scanner s1 = new Scanner(new File("user.txt"))) {
            while (s1.hasNext()) {
                String name = s1.nextLine();
                String Uid = s1.nextLine();
                int password = Integer.parseInt(s1.nextLine());
                userRoles role  = userRoles.valueOf(s1.nextLine().toUpperCase());
                foodCuisineType foodCuisine = foodCuisineType.valueOf(s1.nextLine().toUpperCase());
                double balance = Double.parseDouble(s1.nextLine());
                s1.nextLine();
                allUsers.add(new User(name, Uid, password, role, foodCuisine, balance));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x) {
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s2 = new Scanner(new File("foodItem.txt"))) {
            while (s2.hasNext()) {
                String foodItemID = s2.nextLine();
                String foodItemName = s2.nextLine();
                foodCuisineType foodCuisine = foodCuisineType.valueOf(s2.nextLine().toUpperCase());
                double price = Double.parseDouble(s2.nextLine());
                String vendorName = s2.nextLine();
                String vendorUID = s2.nextLine();
                foodCategory category = foodCategory.valueOf(s2.nextLine().toUpperCase());
                s2.nextLine();
                allFoodItems.add(new foodItem(foodItemID, foodItemName, foodCuisine, price, vendorName,vendorUID, category));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x) {
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s3 = new Scanner(new File("customerReview.txt"))){
            while (s3.hasNext()) {
                String reviewerUID = s3.nextLine();
                String revieweeUID = s3.nextLine();
                int rating = Integer.parseInt(s3.nextLine());
                String feedback = s3.nextLine();
                String orderID = s3.nextLine();
                userRoles role  = userRoles.valueOf(s3.nextLine().toUpperCase());
                s3.nextLine();
                allReview.add(new Review(reviewerUID,revieweeUID, rating, feedback, orderID, role));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s4 = new Scanner(new File("order.txt"))){
            while (s4.hasNext()) {
                String customerID = s4.nextLine();
                String orderID = s4.nextLine();
                String foodItemID = s4.nextLine();
                String foodItemName = s4.nextLine();
                double price = Double.parseDouble(s4.nextLine());
                String vendorUID = s4.nextLine();
                foodCategory category  = foodCategory.valueOf(s4.nextLine().toUpperCase());
                int quantity = Integer.parseInt(s4.nextLine());
                String option = s4.nextLine();
                String time = s4.nextLine();
                String status = s4.nextLine();
                String deliveryStatus = s4.nextLine();
                String reviewVendor = s4.nextLine();
                String reviewRunner = s4.nextLine();
                String runnerID = s4.nextLine();
                s4.nextLine();
                allOrderConfirm.add(new OrderConfirm(customerID, orderID, foodItemID, foodItemName, price, vendorUID, category, quantity, option, time, status, deliveryStatus, reviewVendor, reviewRunner, runnerID));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s5 = new Scanner(new File("notification.txt"))){
            while (s5.hasNext()) {
                String NID = s5.nextLine();
                String time = s5.nextLine();
                String notification = s5.nextLine();
                String UID = s5.nextLine();
                s5.nextLine();
                allNotification.add(new Notification(NID, time, notification, UID));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s6 = new Scanner(new File("receipt.txt"))){
            while (s6.hasNext()) {
                String RID = s6.nextLine();
                String Amount = s6.nextLine();
                String time = s6.nextLine();
                String uid = s6.nextLine();
                s6.nextLine();
                allReceipt.add(new Receipt(RID, Amount, time, uid));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s7 = new Scanner(new File("transaction.txt"))){
            while (s7.hasNext()) {
                String TID = s7.nextLine();
                String time = s7.nextLine();
                String title = s7.nextLine();
                String transaction = s7.nextLine();
                String uid = s7.nextLine();
                s7.nextLine();
                allTransaction.add(new Transaction(TID, time, title, transaction, uid));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
        try (Scanner s8 = new Scanner(new File("delivery.txt"))){
            while (s8.hasNext()) {
                String DID = s8.nextLine();
                String OID = s8.nextLine();
                String vendorID = s8.nextLine();
                String customerID = s8.nextLine();
                String deliveryStatus = s8.nextLine();
                String time = s8.nextLine();
                String location = s8.nextLine();
                String runnerID = s8.nextLine();
                String availability = s8.nextLine();
                s8.nextLine();
                allDeliveries.add(new Delivery(DID, OID, vendorID, customerID, deliveryStatus, time, location, runnerID, availability));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + e.getMessage());
        } catch (Exception x){
            System.out.println("Error in read: " + x.getMessage());
        }
    }

    public static void writeFile(){
        try{
            PrintWriter p1 = new PrintWriter("user.txt");
            for(User c : allUsers){
                p1.println(c.getName());
                p1.println(c.getUid());
                p1.println(c.getPassword());
                p1.println(c.getRole());
                p1.println(c.getFoodCuisine());
                p1.println(c.getBalance());
                p1.println();
            }
            p1.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p2 = new PrintWriter("foodItem.txt");
            for(foodItem f : allFoodItems){
                p2.println(f.getFoodItemID());
                p2.println(f.getFoodItemName());
                p2.println(f.getCuisineType());
                p2.println(f.getPrice());
                p2.println(f.getVendorName());
                p2.println(f.getVendorUID());
                p2.println(f.getCategory());
                p2.println();
            }
            p2.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p4 = new PrintWriter("order.txt");
            for(OrderConfirm r : allOrderConfirm){
                p4.println(r.getCustomerID());
                p4.println(r.getOrderID());
                p4.println(r.getFoodItemID());
                p4.println(r.getFoodItemName());
                p4.println(r.getPrice());
                p4.println(r.getVendorUID());
                p4.println(r.getCategory());
                p4.println(r.getQuantity());
                p4.println(r.getOption());
                p4.println(r.getTime());
                p4.println(r.getStatus());
                p4.println(r.getDeliveryStatus());
                p4.println(r.getReviewVendor());
                p4.println(r.getReviewRunner());
                p4.println(r.getRunnerID());
                p4.println();
            }
            p4.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p3 = new PrintWriter("customerReview.txt");
            for(Review r : allReview){
                p3.println(r.getReviewerUID());
                p3.println(r.getRevieweeUID());
                p3.println(r.getRating());
                p3.println(r.getFeedback());
                p3.println(r.getOrderID());
                p3.println(r.getRoles());
                p3.println();
            }
            p3.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p5 = new PrintWriter("notification.txt");
            for (Notification n : allNotification){
                p5.println(n.getNID());
                p5.println(n.getTime());
                p5.println(n.getNotification());
                p5.println(n.getUID());
                p5.println();
            }
            p5.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p6 = new PrintWriter("receipt.txt");
            for (Receipt r : allReceipt){
                p6.println(r.getReceiptID());
                p6.println(r.getAmount());
                p6.println(r.getTime());
                p6.println(r.getUserID());
                p6.println();
            }
            p6.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p7 = new PrintWriter("transaction.txt");
            for (Transaction t : allTransaction){
                p7.println(t.getTransactionID());
                p7.println(t.getTime());
                p7.println(t.getTitle());
                p7.println(t.getTransaction());
                p7.println(t.getUid());
                p7.println();
            }
            p7.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
        try{
            PrintWriter p8 = new PrintWriter("delivery.txt");
            for (Delivery d: allDeliveries){
                p8.println(d.getDeliveryID());
                p8.println(d.getOrderID());
                p8.println(d.getVendorUID());
                p8.println(d.getCustomerID());
                p8.println(d.getDeliveryStatus());
                p8.println(d.getTime());
                p8.println(d.getLocation());
                p8.println(d.getRunnerID());
                p8.println(d.getAvailability());
                p8.println();
            }
            p8.close();
        }catch(Exception x){
            System.out.println("Error in write: " + x.getMessage());
        }
    }
}