package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class vendorViewOwnReview {
    private JFrame vendorViewOwnReview;
    private JButton backBtn;
    private JTable reviewTable;
    private DefaultTableModel tableModel;


    public JFrame getViewCustomerReview() {
        // Load and display all menu items in the JTable initially
        try {
            Vendor.displayVendorReviewInTable(tableModel, mainClass.loginUser.getUid());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vendorViewOwnReview, "Error loading menu items. Try Again!");
        }
        return vendorViewOwnReview;
    }

    public vendorViewOwnReview() {
        vendorViewOwnReview = new JFrame("View Reviews");
        vendorViewOwnReview.setSize(800, 400);
        vendorViewOwnReview.setLocation(700, 300);
        vendorViewOwnReview.setLocationRelativeTo(null);


        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBackToDashboard());

        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Order ID", "Rating", "Feedback"}, 0);
        reviewTable = new JTable(tableModel);

        // Add a window listener to handle window closing event
        vendorViewOwnReview.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);

        vendorViewOwnReview.setLayout(new BorderLayout());
        vendorViewOwnReview.add(new JScrollPane(reviewTable), BorderLayout.CENTER);
        vendorViewOwnReview.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void goBackToDashboard() {
        vendorViewOwnReview.setVisible(false);
        mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
    }
}
