package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class vendorOrderHistory implements ItemListener, ActionListener {
    private JFrame vendorOrderHistory;
    private JButton backBtn;
    private JButton viewDetailsBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> viewOrderTimeComboBox;
    private static String vendorID;
    private static String orderTime = "All";
    
    public static void getVendorID(String VID) {
        vendorID = VID;
    }
    
    public JFrame getVendorOrderHistory() {
        update();
        viewOrderTimeComboBox.setSelectedIndex(0);
        //Customer.displayMenuItems(tableModel);
        return vendorOrderHistory;
    }
    
    private static boolean isSameQuarter(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        int quarter1 = (dateTime1.getMonthValue() - 1) / 3 + 1;
        int quarter2 = (dateTime2.getMonthValue() - 1) / 3 + 1;
        return quarter1 == quarter2;
    }
    
    private void update() {
        vendorOrderHistory = new JFrame("Order History");
        vendorOrderHistory.setSize(900, 400);
        vendorOrderHistory.setLocationRelativeTo(null);
        
        String[] viewOrderTime = {"All Orders", "Daily", "Monthly", "Quarterly"};
        viewOrderTimeComboBox = new JComboBox<>(viewOrderTime);
        viewOrderTimeComboBox.addItemListener(this);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        viewDetailsBtn = new JButton("View Details");
        viewDetailsBtn.addActionListener(this);
     
        tableModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID", "Runner ID", "Option", "Date & Time", "Order Status", "Customer Review"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(viewDetailsBtn);

        vendorOrderHistory.setLayout(new BorderLayout());
        vendorOrderHistory.add(viewOrderTimeComboBox, BorderLayout.NORTH);
        vendorOrderHistory.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        vendorOrderHistory.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load and display the menu items in the JTable
        displayHistory();
    }

    private void displayHistory() {
        // Clear the current table data
        Set<String> uniqueOrderIDs = new HashSet<>();
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (OrderConfirm item: allOrderConfirm) {
            if (item.getVendorUID().equals(vendorID) && !item.getStatus().equals("Canceled")) {
                String orderID = item.getOrderID();
                if (uniqueOrderIDs.add(orderID)) {
                    // If it's not in the set, add the row to the tableModel
                    tableModel.addRow(new Object[]{item.getOrderID(), item.getCustomerID(), item.getRunnerID(), item.getOption(), item.getTime(), item.getStatus(), item.getReviewVendor()});
                }
                //tableModel.addRow(new Object[]{item.getOrderID(), item.getVendorUID(), item.getOption(), item.getTime(), item.getStatus()});
            }
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Handle the selected item change event
            try {
                String selectedItem = (String) viewOrderTimeComboBox.getSelectedItem();
                if (selectedItem.equals("Daily")) {
                    tableModel.setRowCount(0);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    for (OrderConfirm o: allOrderConfirm){
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.toLocalDate().isEqual(now.toLocalDate())) {
                            Vendor.displayOrderTime(tableModel, vendorID, o.getOrderID());
                        }
                    }
                } else if (selectedItem.equals("Monthly")) {
                    tableModel.setRowCount(0);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    for (OrderConfirm o: allOrderConfirm){
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.getMonth().equals(now.getMonth())) {
                            Vendor.displayOrderTime(tableModel, vendorID, o.getOrderID());
                        }
                    }
                } else if (selectedItem.equals("Quarterly")) {
                    tableModel.setRowCount(0);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    for (OrderConfirm o: allOrderConfirm){
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (isSameQuarter(orderDateTime, now)) {
                            Vendor.displayOrderTime(tableModel, vendorID, o.getOrderID());
                        }
                    }
                } else if (selectedItem.equals("All Orders")) {
                    displayHistory();
                } else {
                    displayHistory();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vendorOrderHistory, "Error filtering menu items. Try Again!");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            vendorOrderHistory.setVisible(false);
            mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
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
                JOptionPane.showMessageDialog(vendorOrderHistory, "Order Items:\n" + messageBuilder.toString() + "Total Price: RM" + TotalPrice);
            } else {
                JOptionPane.showMessageDialog(vendorOrderHistory, "Please select a menu item to order.");
            } 
        }
    }
}