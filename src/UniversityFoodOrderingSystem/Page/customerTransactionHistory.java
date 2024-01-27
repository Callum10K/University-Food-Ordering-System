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
import static UniversityFoodOrderingSystem.FileIO.DataIO.allTransaction;
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Transaction;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.table.TableColumn;

public class customerTransactionHistory implements ActionListener {
    private JFrame customerTransactionHistory;
    private JButton backBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String customerID;
    
    public JFrame getCustomerTransactionHistory() {
        update();
        return customerTransactionHistory;
    }
    
    private void update() {
        customerTransactionHistory = new JFrame("Transaction History");
        customerTransactionHistory.setSize(800, 400);
        customerTransactionHistory.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        
        tableModel = new DefaultTableModel(new Object[]{"TID", "Date & Time", "Title", "Transaction"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);

        customerTransactionHistory.setLayout(new BorderLayout());
        customerTransactionHistory.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        customerTransactionHistory.add(buttonPanel, BorderLayout.SOUTH);

        // Load and display the menu items in the JTable
        displayTransaction();
    }

    private void displayTransaction() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (Transaction t: allTransaction) {
            if (mainClass.loginUser.getUid().equals(t.getUid())) {
                tableModel.addRow(new Object[]{t.getTransactionID(), t.getTime(), t.getTitle(), t.getTransaction()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            customerTransactionHistory.setVisible(false);
            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
        } 
    }
}