package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Runner;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class runnerViewOwnReview {
    private JFrame runnerViewOwnReview;
    private JButton backBtn;
    private JTable reviewTable;
    private DefaultTableModel tableModel;


    public JFrame getRunnerViewOwnReview() {
        // Load and display all menu items in the JTable initially
        try {
            Runner.displayCustomerReviewInTable(tableModel, mainClass.loginUser.getUid());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(runnerViewOwnReview, "Error loading menu items. Try Again!");
        }
        return runnerViewOwnReview;
    }

    public runnerViewOwnReview() {
        runnerViewOwnReview = new JFrame("View Reviews");
        runnerViewOwnReview.setSize(800, 400);
        runnerViewOwnReview.setLocationRelativeTo(null);


        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBackToDashboard());

        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Delivery ID", "Rating", "Feedback"}, 0);
        reviewTable = new JTable(tableModel);

        // Add a window listener to handle window closing event
        runnerViewOwnReview.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);

        runnerViewOwnReview.setLayout(new BorderLayout());
        runnerViewOwnReview.add(new JScrollPane(reviewTable), BorderLayout.CENTER);
        runnerViewOwnReview.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void goBackToDashboard() {
        runnerViewOwnReview.setVisible(false);
        mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
    }
}

