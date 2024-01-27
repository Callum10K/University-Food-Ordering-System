package UniversityFoodOrderingSystem.Page;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allDeliveries;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allNotification;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allTransaction;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Delivery;
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Runner;
import UniversityFoodOrderingSystem.Model.Transaction;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class vendorManageOrder implements ActionListener {
    private JFrame vendorManageOrder;
    private JButton backBtn;
    private JButton viewBtn;
    private JButton acceptBtn;
    private JButton declineBtn;
    private JButton readyBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String vendorID;
    private static String notificationID;
    private static int notificationIDNum = 0;
    private static String transactionID;
    private static int transactionIDNum = 0;
    private static Double totalPrice = 0.0;
    private static String runnerID = "";
    private static String rid = "";
    private static String deliveryID;
    private static String location;
    private static int deliveryIDNum = 0;
    private static Boolean deliverySent = false;
    private static Boolean AlreadyExist = false;
    private static Boolean skip = false;
    
    public static void getVendorID(String VID) {
        vendorID = VID;
    }
    
    public static void getLocation(String customerLocation) {
        location = customerLocation;
    }
    
    public static void getRunner(String OID, String CID, String formattedDateTime, String VID, String RID) {
        runnerID = "";
        for (Delivery d: allDeliveries) {
            if (OID.equals(d.getOrderID()) && d.getAvailability().equals("Available")) {
                runnerID = d.getRunnerID();
            }
        }
        if (runnerID.equals("")) {
            for (User u: allUsers) {
                AlreadyExist = false;
                String role = String.valueOf(u.getRole());
                if (role.equals("RUNNER")) {
                    rid = u.getUid();
                    for (Delivery d: allDeliveries) {
                        if (d.getRunnerID().equals(u.getUid())) {
                            AlreadyExist = true;
                        }
                    }
                    if (!AlreadyExist) {
                        runnerID = rid;
                        break;
                    } else {
                        continue;
                    }
                }
            }
        }
        if (runnerID.equals("")) {
            mainClass.noRunnerPage.getOID(OID);
            mainClass.loginModule.setPopUp(true);
            notificationIDNum += 1;
            notificationID = "NID"+ notificationIDNum;
            if (!allNotification.isEmpty()) { 
                for (Notification n: allNotification) {
                    if (n.getNID().equals(notificationID)) {
                        notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                        notificationID = "NID"+ notificationIDNum;
                    }
                }
            }
            transactionIDNum += 1;
            transactionID = "TID"+ transactionIDNum;
            if (!allTransaction.isEmpty()) { 
                for (Transaction t: allTransaction) {
                    if (t.getTransactionID().equals(transactionID)) {
                        transactionIDNum = Integer.parseInt(t.getTransactionID().substring(3)) + 1;
                        transactionID = "TID"+ transactionIDNum;
                    }
                }
            }
            Customer.getNotification(notificationID, formattedDateTime, "[" + OID + "]" + " There are currently no available runners.", CID);
            for (User u: allUsers) {
                if (u.getUid().equals(CID)) {
                    u.setBalance(u.getBalance() + 7);
                }
            }
            Customer.getTransaction(transactionID, formattedDateTime, "[" + OID + "]" + " Refund Delivery Fee" , "+RM7", CID);
        }
        if (!runnerID.equals("")) {
            deliveryIDNum += 1;
            deliveryID = "DID"+ deliveryIDNum;
            if (!allDeliveries.isEmpty()) { 
                for (Delivery d: allDeliveries) {
                    if (d.getDeliveryID().equals(deliveryID)) {
                        deliveryIDNum = Integer.parseInt(d.getDeliveryID().substring(3)) + 1;
                        deliveryID = "DID"+ deliveryIDNum;
                    }
                }
            }
            Customer.callForDelivery(deliveryID, OID, VID, CID, "Pending", formattedDateTime, location, runnerID, "Available");
        
        }
    }
  
    public static void runnerGetOrderNotification(String OID) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();  
        String formattedDateTime = now.format(dtf);
        notificationIDNum += 1;
        notificationID = "NID"+ notificationIDNum;
        if (!allNotification.isEmpty()) { 
            for (Notification n: allNotification) {
                if (n.getNID().equals(notificationID)) {
                    notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                    notificationID = "NID"+ notificationIDNum;
                }
            }
        }
        for (Delivery d: allDeliveries) {
            if(d.getOrderID().equals(OID) && d.getDeliveryStatus().equals("Accepted")) {
                Runner.getNotification(notificationID, formattedDateTime, "[" + OID + "]" + " Order is ready to be collected from " + vendorID, runnerID);
                runnerID = d.getRunnerID();
                break;
            }
        }
    }
    
    public JFrame getVendorManageOrder() {
        update();
        //Vendor.displayCustomerOrderInTable(tableModel);
        return vendorManageOrder;
    }
    
    private void update() {
        vendorManageOrder = new JFrame("Manage Order Page");
        vendorManageOrder.setSize(600, 400);
        vendorManageOrder.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        
        viewBtn = new JButton("View Details");
        viewBtn.setFocusable(false);
        viewBtn.addActionListener(this);
        
        acceptBtn = new JButton("Accept");
        acceptBtn.setFocusable(false);
        acceptBtn.addActionListener(this);
        
        declineBtn = new JButton("Decline");
        declineBtn.setFocusable(false);
        declineBtn.addActionListener(this);
        
        readyBtn = new JButton("Ready");
        readyBtn.setFocusable(false);
        readyBtn.addActionListener(this);
        
        tableModel = new DefaultTableModel(new Object[]{"OrderID", "CustomerID", "Option", "Time", "Status"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(acceptBtn);
        buttonPanel.add(declineBtn);
        buttonPanel.add(readyBtn);

        vendorManageOrder.setLayout(new BorderLayout());
        vendorManageOrder.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        vendorManageOrder.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load and display the menu items in the JTable
        displayCustomerOrder();
    }

    private void displayCustomerOrder() {
        // Clear the current table data
        Set<String> uniqueOrderIDs = new HashSet<>();
        tableModel.setRowCount(0);
        
        // Retrieve the menu items and add them to the tableModel
        for (OrderConfirm o: allOrderConfirm) {
            if (o.getVendorUID().equals(vendorID) && !o.getStatus().equals("Ready") && !o.getStatus().equals("Declined") && !o.getStatus().equals("Canceled")) {
                String orderID = o.getOrderID();
                if (uniqueOrderIDs.add(orderID)) {
                    // If it's not in the set, add the row to the tableModel
                    tableModel.addRow(new Object[]{o.getOrderID(), o.getCustomerID(), o.getOption(), o.getTime(), o.getStatus()});
                }
                //tableModel.addRow(new Object[]{item.getOrderID(), item.getVendorUID(), item.getOption(), item.getTime(), item.getStatus()});
            }
            //tableModel.addRow(new Object[]{o.getOrderID(), o.getCustomerID(), o.getOption(), o.getTime(), o.getStatus()});
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            vendorManageOrder.setVisible(false);
            mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
        } else if (e.getSource() == viewBtn) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                StringBuilder messageBuilder = new StringBuilder();
                int TotalPrice = 0;
                int n = 0;
                for (OrderConfirm item : allOrderConfirm) {
                    if (item.getOrderID().equals(OID)) {
                        TotalPrice += item.getPrice() * item.getQuantity();
                        n += 1;
                        String orderInformation = n + ") " + item.getFoodItemName() + " x" + item.getQuantity() + " RM" + item.getPrice() * item.getQuantity();

                        // Append the foodName to the StringBuilder
                        messageBuilder.append(orderInformation).append("\n");
                    }
                }

                // Display the message using JOptionPane
                JOptionPane.showMessageDialog(vendorManageOrder, "Order Items:\n" + messageBuilder.toString() + "Total Price: RM" + TotalPrice);
                } else {
                    JOptionPane.showMessageDialog(vendorManageOrder, "Please select a menu item to order.");
                }
        } else if (e.getSource() == acceptBtn) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  
            String formattedDateTime = now.format(dtf);
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                boolean messageAlreadyShown = false;
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String CID = (String) tableModel.getValueAt(selectedRow, 1);
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID) && !o.getStatus().equals("Preparing")) {
                        o.setStatus("Preparing");
                        displayCustomerOrder();
                        notificationIDNum += 1;
                        notificationID = "NID"+ notificationIDNum;
                        if (!allNotification.isEmpty()) { 
                            for (Notification n: allNotification) {
                                if (n.getNID().equals(notificationID)) {
                                    notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                                    notificationID = "NID"+ notificationIDNum;
                                }
                            }
                        }
                Customer.getNotification(notificationID, formattedDateTime, "[" + OID + "]" + " Order has been accepted by " + vendorID, CID);
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Preparing") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(vendorManageOrder, "Order has already been accepted.");
                        messageAlreadyShown = true;
                    }
                }
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID) && o.getOption().equals("Delivery")) {
                        getRunner(OID, CID, formattedDateTime, mainClass.loginUser.getUid(), "");
                    }
                }
            } else {
                    JOptionPane.showMessageDialog(vendorManageOrder, "Please select an order to accept.");
            }
        } else if (e.getSource() == declineBtn) {
            totalPrice = 0.0;
            boolean messageAlreadyShown = false;
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String CID = (String) tableModel.getValueAt(selectedRow, 1);
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID)) {
                        totalPrice += o.getPrice() * o.getQuantity();
                    }
                    if (o.getOrderID().equals(OID) && !o.getStatus().equals("Preparing")) {
                        o.setStatus("Declined");
                        displayCustomerOrder();
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Preparing") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(vendorManageOrder, "Cannot decline an order that has already been accepted.");
                        messageAlreadyShown = true;
                    }
                }
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();  
                String formattedDateTime = now.format(dtf);
                notificationIDNum += 1;
                notificationID = "NID"+ notificationIDNum;
                transactionIDNum += 1;
                transactionID = "TID"+ transactionIDNum;
                if (!allNotification.isEmpty()) { 
                        for (Notification n: allNotification) {
                            if (n.getNID().equals(notificationID)) {
                                notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                                notificationID = "NID"+ notificationIDNum;
                            }
                        }
                    }
                if (!allTransaction.isEmpty()) { 
                    for (Transaction t: allTransaction) {
                        if (t.getTransactionID().equals(transactionID)) {
                            transactionIDNum = Integer.parseInt(t.getTransactionID().substring(3)) + 1;
                            transactionID = "TID"+ transactionIDNum;
                        }
                    }
                }
                for (User u: allUsers) {
                    if (u.getUid().equals(CID)) {
                        u.setBalance(u.getBalance() + totalPrice);
                    }
                    if (u.getUid().equals(mainClass.loginUser.getUid())) {
                        u.setBalance(u.getBalance() - totalPrice);
                    }
                }
                Customer.getNotification(notificationID, formattedDateTime, "[" + OID + "]" + " Order has been declined by " + vendorID, CID);
                Customer.getTransaction(transactionID, formattedDateTime, "[" + OID + "]" + " Refund from " + vendorID + " Reason: Order Declined." , "+RM" + totalPrice, CID);
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID) && o.getOption().equals("Delivery")) {
                        for (User u: allUsers) {
                            if (u.getUid().equals(CID)) {
                                u.setBalance(u.getBalance() + 7);
                            }
                        }
                    }
                }
                transactionIDNum += 1;
                transactionID = "TID"+ transactionIDNum;
                Customer.getTransaction(transactionID, formattedDateTime, "[" + OID + "]" + " Refund Delivery Fee" , "+RM7", CID);
            }
            else {
                JOptionPane.showMessageDialog(vendorManageOrder, "Please select an order to decline.");
            } 
        } else if (e.getSource() == readyBtn) {
            boolean messageAlreadyShown = false;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  
            String formattedDateTime = now.format(dtf);
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String CID = (String) tableModel.getValueAt(selectedRow, 1);
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID) && !o.getStatus().equals("Pending")) {
                        o.setStatus("Ready");
                        displayCustomerOrder();
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Pending") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(vendorManageOrder, "Order has to be accepted first.");
                        messageAlreadyShown = true;
                    }
                }
                notificationIDNum += 1;
                notificationID = "NID"+ notificationIDNum;
                if (!allNotification.isEmpty()) { 
                    for (Notification n: allNotification) {
                        if (n.getNID().equals(notificationID)) {
                            notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                            notificationID = "NID"+ notificationIDNum;
                        }
                    }
                }
                Customer.getNotification(notificationID, formattedDateTime, "[" + OID + "]" + " Order is ready to be collected from " + vendorID, CID);
                runnerGetOrderNotification(OID);
            }
            else {
                JOptionPane.showMessageDialog(vendorManageOrder, "Please select an order to inform that it is ready.");
            } 
        }
    }
}