package UniversityFoodOrderingSystem.Page;

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
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VendorNotification implements ActionListener {
    private JFrame vendorNotification;
    private JButton backBtn, readBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String vendorID;
    
    public JFrame getVendorNotification() {
        update();
        //Vendor.displayCustomerOrderInTable(tableModel);
        return vendorNotification;
    }
    
    public static void getVendorID(String VID) {
        vendorID = VID;
    }
    
    private void update() {
        vendorNotification = new JFrame("Vendor Notification Page");
        vendorNotification.setSize(800, 400);
        vendorNotification.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        readBtn = new JButton("Read");
        readBtn.addActionListener(this);
        
        tableModel = new DefaultTableModel(new Object[]{"NID", "Date & Time", "Notification"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(readBtn);

        vendorNotification.setLayout(new BorderLayout());
        vendorNotification.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        vendorNotification.add(buttonPanel, BorderLayout.SOUTH);

        // Load and display the menu items in the JTable
        displayNotification();
    }

    private void displayNotification() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (Notification n: allNotification) {
            if (vendorID.equals(n.getUID())) {
                tableModel.addRow(new Object[]{n.getNID(), n.getTime(), n.getNotification()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            vendorNotification.setVisible(false);
            mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
        } else if (e.getSource() == readBtn) {
            vendorNotification.setVisible(false);
            mainClass.vendorDashboardPage.getNotificationRead(true);
            mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
        } 
    }
}