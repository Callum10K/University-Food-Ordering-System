package UniversityFoodOrderingSystem.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Review;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class orderHistory implements ActionListener {
    private JFrame orderHistory;
    private JButton backBtn;
    private JButton reorderBtn;
    private JButton viewDetailsBtn;
    private JButton cancelOrderBtn;

    private JButton reviewVendorBtn, reviewRunnerBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String customerID;
    
    public static void getCustomerID(String CID) {
        customerID = CID;
    }
    
    public JFrame getOrderHistory() {
        update();
        //Customer.displayMenuItems(tableModel);
        return orderHistory;
    }
    
    private void update() {
        orderHistory = new JFrame("Order History");
        orderHistory.setSize(1200, 400);
        orderHistory.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.addActionListener(this);
        
        reorderBtn = new JButton("Reorder");
        reorderBtn.addActionListener(this);
        
        cancelOrderBtn = new JButton("Cancel Order");
        cancelOrderBtn.addActionListener(this);

        reviewVendorBtn = new JButton("Review Vendor");
        reviewVendorBtn.addActionListener(this);

        reviewRunnerBtn = new JButton("Review Runner");
        reviewRunnerBtn.addActionListener(this);
     
        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Vendor UID", "Option", "Date & Time", "Order Status", "Delivery Status", "RunnerID", "Vendor Review", "Runner Review"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(viewDetailsBtn);
        buttonPanel.add(reorderBtn);
        buttonPanel.add(cancelOrderBtn);
        buttonPanel.add(reviewVendorBtn);
        buttonPanel.add(reviewRunnerBtn);

        orderHistory.setLayout(new BorderLayout());
        orderHistory.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        orderHistory.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load and display the menu items in the JTable
        displayHistory();
    }

    private void displayHistory() {
        // Clear the current table data
        Set<String> uniqueOrderIDs = new HashSet<>();
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (OrderConfirm item: allOrderConfirm) {
            if (item.getCustomerID().equals(customerID)) {
                String orderID = item.getOrderID();
                if (uniqueOrderIDs.add(orderID)) {
                    // If it's not in the set, add the row to the tableModel
                    tableModel.addRow(new Object[]{item.getOrderID(), item.getVendorUID(), item.getOption(), item.getTime(), item.getStatus(), item.getDeliveryStatus(),item.getRunnerID(), item.getReviewVendor(), item.getReviewRunner()});
                }
                //tableModel.addRow(new Object[]{item.getOrderID(), item.getVendorUID(), item.getOption(), item.getTime(), item.getStatus()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            orderHistory.setVisible(false);
            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
        } else if (e.getSource() == viewDetailsBtn) {
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
                JOptionPane.showMessageDialog(orderHistory, "Order Items:\n" + messageBuilder.toString() + "Total Price: RM" + TotalPrice);
            } else {
                JOptionPane.showMessageDialog(orderHistory, "Please select a menu item to order.");
            } 
        } else if (e.getSource() == reorderBtn) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                orderHistory.setVisible(false);
                mainClass.orderViewCartPage.getOrderViewCartReorderPage(OID).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(orderHistory, "Please select an order to reorder.");
            }
        } else if (e.getSource() == cancelOrderBtn) {
            boolean messageAlreadyShown = false;
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getOrderID().equals(OID) && o.getStatus().equals("Pending")) {
                        o.setStatus("Canceled");
                        displayHistory();
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Preparing") && o.getStatus().equals("Ready") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(orderHistory, "Order has already been accepted. \n You cannot cancel it.");
                        messageAlreadyShown = true;
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Canceled") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(orderHistory, "The order has already been canceled.");
                        messageAlreadyShown = true;
                    } else if (o.getOrderID().equals(OID) && o.getStatus().equals("Declined") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(orderHistory, "Order has already been declined.");
                        messageAlreadyShown = true;
                    }  else if (o.getOrderID().equals(OID) && o.getStatus().equals("Ready") && !messageAlreadyShown){
                        JOptionPane.showMessageDialog(orderHistory, "Order is already ready.");
                        messageAlreadyShown = true;
                    }
                }

            } else {
                JOptionPane.showMessageDialog(orderHistory, "Please select an order to cancel.");
            }
        } else if (e.getSource() == reviewVendorBtn) {
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String option = (String) tableModel.getValueAt(selectedRow, 2);
                String VendorUID = (String) tableModel.getValueAt(selectedRow, 1);
                String orderStatus = (String) tableModel.getValueAt(selectedRow, 4);
                String DeliveryStatus = (String) tableModel.getValueAt(selectedRow, 5);
                boolean found = Review.checkVendorReviewExists(OID, VendorUID);
                if (orderStatus.equals("Ready")) {
                    if ((option.equals("Delivery") && DeliveryStatus.equals("Delivered")) || !option.equals("Delivery")) {
                        if (!found) {
                            orderHistory.setVisible(false);
                            mainClass.customerReviewVendorPage.getGiveReviewCustomerForVendor(OID, VendorUID).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(orderHistory, "You have already reviewed the vendor based on the Order ID!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(orderHistory, "Order cannot be reviewed at the moment.");
                    }
                } else{
                    JOptionPane.showMessageDialog(orderHistory, "Order cannot be reviewed at the moment.");
                }
            } else {
                JOptionPane.showMessageDialog(orderHistory, "Please select a vendor to be reviewed.");
            }

        } else if (e.getSource() == reviewRunnerBtn) {
            // implement checking to ensure review for runner associated with  a specific order id doesn't exist
            int selectedRow = menuTable.getSelectedRow();
            if (selectedRow != -1) {
                String OID = (String) tableModel.getValueAt(selectedRow, 0);
                String option = (String) tableModel.getValueAt(selectedRow, 2);
                String orderStatus = (String) tableModel.getValueAt(selectedRow, 4);
                String DeliveryStatus = (String) tableModel.getValueAt(selectedRow, 5);
                String runnerUID = (String) tableModel.getValueAt(selectedRow, 6);
                String runnerReviewed = (String) tableModel.getValueAt(selectedRow, 8);
                boolean found = Review.checkRunnerReviewExists(OID, runnerReviewed, runnerUID);
                if (option.equals("Delivery")) {
                    if (orderStatus.equals("Ready") && DeliveryStatus.equals("Delivered")) {
                        if (!found) {
                            //if (!option.equals("Delivery")) {
                            //JOptionPane.showMessageDialog(orderHistory, "Invalid operation!");
                            //return;
                            //}
                            orderHistory.setVisible(false);
                            mainClass.customerReviewRunnerPage.getGiveReviewCustomerForRunner(OID, runnerUID).setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(orderHistory, "You have already reviewed the delivery runner based on the Order ID!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(orderHistory, "Order cannot be reviewed at the moment.");
                    }
                } else {
                    JOptionPane.showMessageDialog(orderHistory, "Review not available for non-deliveries!");
                }
            } else {
                JOptionPane.showMessageDialog(orderHistory, "Please select a runner to be reviewed.");
            }
        }
    }
}