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
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.TableColumn;

public class customerNotification implements ActionListener {
    private JFrame customerNotification;
    private JButton backBtn, readBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String customerID;
    
    public JFrame getCustomerNotification() {
        update();
        return customerNotification;
    }
    
    public static void getCustomerID(String CID) {
        customerID = CID;
    }
    
    private void update() {
        customerNotification = new JFrame("Customer Notification Page");
        customerNotification.setSize(800, 400);
        customerNotification.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        readBtn = new JButton("Read");
        readBtn.addActionListener(this);
        
        tableModel = new DefaultTableModel(new Object[]{"NID", "Date & Time", "Notification"}, 0);
        menuTable = new JTable(tableModel);
        int[] columnWidths = {40, 110, 200}; 
        
         for (int i = 0; i < menuTable.getColumnCount(); i++) {
            TableColumn column = menuTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(readBtn);

        customerNotification.setLayout(new BorderLayout());
        customerNotification.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        customerNotification.add(buttonPanel, BorderLayout.SOUTH);

        // Load and display the menu items in the JTable
        displayNotification();
    }

    private void displayNotification() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (Notification n: allNotification) {
            if (customerID.equals(n.getUID())) {
                tableModel.addRow(new Object[]{n.getNID(), n.getTime(), n.getNotification()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            customerNotification.setVisible(false);
            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
        } else if (e.getSource() == readBtn) {
            customerNotification.setVisible(false);
            mainClass.customerDashboardPage.getNotificationRead(true);
            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
        } 
    }
}