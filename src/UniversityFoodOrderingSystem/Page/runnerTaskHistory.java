package UniversityFoodOrderingSystem.Page;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allDeliveries;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allFoodItems;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import UniversityFoodOrderingSystem.Model.Delivery;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.mainClass;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class runnerTaskHistory implements ActionListener {
    private JFrame runnerTaskHistory;
    private JButton backBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    
    public JFrame getRunnerTaskHistory() {
        update();
        return runnerTaskHistory;
    }
    
    private void update() {
        runnerTaskHistory = new JFrame("Task History");
        runnerTaskHistory.setSize(1200, 400);
        runnerTaskHistory.setLocationRelativeTo(null);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
     
        tableModel = new DefaultTableModel(new Object[]{"Delivery ID", "Order ID", "Vendor ID", "Customer ID", "Date & Time", "Delivery Status", "location", "Customer Review"}, 0);
        menuTable = new JTable(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);

        runnerTaskHistory.setLayout(new BorderLayout());
        runnerTaskHistory.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        runnerTaskHistory.add(buttonPanel, BorderLayout.SOUTH);
        
        // Load and display the menu items in the JTable
        displayHistory();
    }

    private void displayHistory() {
        String customerReview = "";
        // Clear the current table data
        tableModel.setRowCount(0);
        // Retrieve the menu items and add them to the tableModel
        for (Delivery d: allDeliveries) {
            for (OrderConfirm o: allOrderConfirm) {
                if (o.getOrderID().equals(d.getOrderID())) {
                    customerReview = o.getReviewRunner();
                    break;
                }
            }
            if (d.getRunnerID().equals(mainClass.loginUser.getUid())) {
                tableModel.addRow(new Object[]{d.getDeliveryID(), d.getOrderID(), d.getVendorUID(), d.getCustomerID(), d.getTime(), d.getDeliveryStatus(), d.getLocation(), customerReview});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            runnerTaskHistory.setVisible(false);
            mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
        } 
    }
}
