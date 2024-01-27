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

public class runnerNotification implements ActionListener {
    private JFrame runnerNotification;
    private JButton backBtn, readBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    
    public JFrame getRunnerNotification() {
        update();
        return runnerNotification;
    }
    
    private void update() {
        runnerNotification = new JFrame("Runner Notification Page");
        runnerNotification.setSize(800, 400);
        runnerNotification.setLocationRelativeTo(null);

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

        runnerNotification.setLayout(new BorderLayout());
        runnerNotification.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        runnerNotification.add(buttonPanel, BorderLayout.SOUTH);

        // Load and display the menu items in the JTable
        displayNotification();
    }

    private void displayNotification() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        for (Notification n: allNotification) {
            if (mainClass.loginUser.getUid().equals(n.getUID())) {
                tableModel.addRow(new Object[]{n.getNID(), n.getTime(), n.getNotification()});
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            runnerNotification.setVisible(false);
            mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
        } else if (e.getSource() == readBtn) {
            runnerNotification.setVisible(false);
            mainClass.runnerDashboardPage.getNotificationRead(true);
            mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
        } 
    }
}