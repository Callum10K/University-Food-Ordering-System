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

public class runnerViewTask implements ActionListener {
    private JFrame runnerViewTask;
    private JButton backBtn;
    private JButton viewBtn;
    private JButton acceptBtn;
    private JButton declineBtn;
    private JButton deliveredBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String vendorID;
    private static String notificationID;
    private static int notificationIDNum = 0;
    private static String transactionID;
    private static int transactionIDNum = 0;
    private static Double totalPrice = 0.0;
    
    public static void getVendorID(String VID) {
        vendorID = VID;
    }
    
    public JFrame getRunnerViewTask() {
        update();
        //Vendor.displayCustomerOrderInTable(tableModel);
        return runnerViewTask;
    }
    
    private void update() {
        runnerViewTask = new JFrame("View Task");
        runnerViewTask.setSize(600, 400);
        runnerViewTask.setLocation(700, 300);
        runnerViewTask.setLocationRelativeTo(null);
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
        
        deliveredBtn = new JButton("Delivered");
        deliveredBtn.setFocusable(false);
        deliveredBtn.addActionListener(this);
        
        tableModel = new DefaultTableModel(new Object[]{"OrderID", "VendorUID", "CustomerID", "Status", "Date & Time", "Location"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(acceptBtn);
        buttonPanel.add(declineBtn);
        buttonPanel.add(deliveredBtn);

        runnerViewTask.setLayout(new BorderLayout());
        runnerViewTask.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        runnerViewTask.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load and display the menu items in the JTable
        displayRunnerTask();
    }

    private void displayRunnerTask() {
        // Clear the current table data
        tableModel.setRowCount(0);
        
        // Retrieve the menu items and add them to the tableModel
        for (Delivery d: allDeliveries) {
            if (d.getRunnerID().equals(mainClass.loginUser.getUid()) && !d.getDeliveryStatus().equals("Declined") && !d.getDeliveryStatus().equals("Delivered")) {
                tableModel.addRow(new Object[]{d.getOrderID(), d.getVendorUID(), d.getCustomerID(), d.getDeliveryStatus(), d.getTime(), d.getLocation()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            runnerViewTask.setVisible(false);
            mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
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
                JOptionPane.showMessageDialog(runnerViewTask, "Order Items:\n" + messageBuilder.toString() + "Total Price: RM" + TotalPrice);
                } else {
                    JOptionPane.showMessageDialog(runnerViewTask, "Please select a task to view.");
                }
        } else if (e.getSource() == acceptBtn) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                for (Delivery d: allDeliveries) {
                    if (d.getRunnerID().equals(mainClass.loginUser.getUid()) && d.getOrderID().equals(OID) && !d.getDeliveryStatus().equals("Accepted") ) {
                        d.setAvailability("Not Available");
                        d.setDeliveryStatus("Accepted");
                        displayRunnerTask();
                        for (OrderConfirm o: allOrderConfirm) {
                            if (o.getOrderID().equals(OID)) {
                                o.setDeliveryStatus("Delivering");
                                o.setRunnerID(mainClass.loginUser.getUid());
                            }
                        }
                    } else if (d.getOrderID().equals(OID) && d.getDeliveryStatus().equals("Accepted")) {
                        JOptionPane.showMessageDialog(runnerViewTask, "Order is already accepted.");
                    }
                }
            } else {
                    JOptionPane.showMessageDialog(runnerViewTask, "Please select a task to accept.");
            }
        } else if (e.getSource() == declineBtn) {
            Boolean onlyOnce = false;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();  
            String formattedDateTime = now.format(dtf);
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String CID = (String) tableModel.getValueAt(selectedRow, 1);
                String VID = (String) tableModel.getValueAt(selectedRow, 2);
                String RID = mainClass.loginUser.getUid();
                for (Delivery d: allDeliveries) {
                    if (d.getOrderID().equals(OID) && !d.getDeliveryStatus().equals("Accepted")) {
                        d.setAvailability("Not Available");
                        d.setDeliveryStatus("Declined");
                        displayRunnerTask();
                        for (OrderConfirm o: allOrderConfirm) {
                            if (o.getOrderID().equals(OID)) {
                                o.setDeliveryStatus("Declined");
                            }
                        }
                        onlyOnce = true;
                    } else if (d.getOrderID().equals(OID) && !d.getDeliveryStatus().equals("Accepted")) {
                        JOptionPane.showMessageDialog(runnerViewTask, "Order has already been accept.\n Cannot decline it.");
                    }
                }
                if (onlyOnce) {
                    mainClass.vendorManageOrderPage.getRunner(OID, CID, formattedDateTime, VID, RID);
                }
            } else {
                    JOptionPane.showMessageDialog(runnerViewTask, "Please select a task to accept.");
            }
        } else if (e.getSource() == deliveredBtn) {
            Boolean onlyOnce = false;
            for (User u: allUsers) {
                if (u.getUid().equals(mainClass.loginUser.getUid())) {
                    u.setBalance(u.getBalance() + 7);
                }
            }
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String RID = mainClass.loginUser.getUid();
                for (Delivery d: allDeliveries) {
                    if (d.getOrderID().equals(OID) && !d.getDeliveryStatus().equals("Pending")) {
                        for (OrderConfirm o: allOrderConfirm) {
                            if (o.getOrderID().equals(OID) && o.getStatus().equals("Ready")) {
                                d.setAvailability("Available");
                                d.setDeliveryStatus("Delivered");
                                displayRunnerTask();
                                o.setDeliveryStatus("Delivered");
                            } else if (o.getOrderID().equals(OID) && !o.getStatus().equals("Ready")) {
                                onlyOnce = true;
                            }
                        }
                    } else if (d.getOrderID().equals(OID) && d.getDeliveryStatus().equals("Pending")) {
                        JOptionPane.showMessageDialog(runnerViewTask, "Order has not been accepted");
                    }
                }
                if (onlyOnce) {
                    JOptionPane.showMessageDialog(runnerViewTask, "Order is not ready.");
                }
            }
        }
    }
}