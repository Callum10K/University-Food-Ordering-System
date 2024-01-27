package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class viewCustomerReview implements ItemListener {
    private JFrame viewCustomerReview;
    private JButton backBtn;
    private JTable reviewTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> viewReviewTypeComboBox;

    public JFrame getViewCustomerReview() {
        viewReviewTypeComboBox.setSelectedIndex(0);
        Customer.displayReview(tableModel);
        return viewCustomerReview;
    }

    public viewCustomerReview() {
        viewCustomerReview = new JFrame("View Reviews");
        viewCustomerReview.setSize(800, 400);
        viewCustomerReview.setLocationRelativeTo(null);

        String[] viewMenuOptions = {"All Reviews","Vendor Reviews", "Delivery Runner Reviews"};
        viewReviewTypeComboBox = new JComboBox<>(viewMenuOptions);
        viewReviewTypeComboBox.addItemListener(this);

        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBackToDashboard());

        tableModel = new DefaultTableModel(new Object[]{"Customer ID", "Order ID","Reviewee ID", "Role Type", "Rating", "Feedback"}, 0);
        reviewTable = new JTable(tableModel);

        // Load and display all menu items in the JTable initially
        try {
            Customer.displayReview(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(viewCustomerReview, "Error loading menu items. Try Again!");
        }

        // Add a window listener to handle window closing event
        viewCustomerReview.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);

        viewCustomerReview.setLayout(new BorderLayout());
        viewCustomerReview.add(viewReviewTypeComboBox, BorderLayout.NORTH);
        viewCustomerReview.add(new JScrollPane(reviewTable), BorderLayout.CENTER);
        viewCustomerReview.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Handle the selected item change event
            try {
                String selectedItem = (String) viewReviewTypeComboBox.getSelectedItem();
                if (selectedItem.equals("All Reviews")) {
                    Customer.displayReview(tableModel);
                } else if (selectedItem.equals("Delivery Runner Reviews")) {
                    Customer.displaySelectedRoleReview(tableModel, userRoles.RUNNER);
                } else{
                    Customer.displaySelectedRoleReview(tableModel, userRoles.VENDOR);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewCustomerReview, "Error filtering menu items. Try Again!");
            }
        }
    }

    private void goBackToDashboard() {
        viewCustomerReview.setVisible(false);
        mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
    }
}
