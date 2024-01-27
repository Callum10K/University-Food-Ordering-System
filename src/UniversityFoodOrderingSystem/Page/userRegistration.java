package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.Model.Admin;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static UniversityFoodOrderingSystem.Enum.userRoles.USER;

public class userRegistration implements ActionListener {
    private JFrame userRegistrationPage;
    private JButton registerBtn, resetBtn, returnBtn;
    private JTextField usernameField, uidField, passwordField;
    private JRadioButton vendorRadioBtn, customerRadioBtn, runnerRadioBtn, adminRadioBtn;
    private ButtonGroup roleButtonGroup;
    private JComboBox<foodCuisineType> foodCuisineTypeJComboBox;

    public JFrame getUserRegistrationPage() {
        return userRegistrationPage;
    }

    public userRegistration() {
        userRegistrationPage = new JFrame("User Registration");
        userRegistrationPage.setSize(400, 300);
        userRegistrationPage.setLocationRelativeTo(null);

        usernameField = new JTextField(20);
        uidField = new JTextField(20);
        passwordField = new JTextField(20);

        foodCuisineTypeJComboBox = new JComboBox<>(foodCuisineType.values());
        foodCuisineTypeJComboBox.setEnabled(false); // Disable the JComboBox by default

        vendorRadioBtn = new JRadioButton("Vendor");
        customerRadioBtn = new JRadioButton("Customer");
        runnerRadioBtn = new JRadioButton("Runner");
        adminRadioBtn = new JRadioButton("Admin");

        roleButtonGroup = new ButtonGroup();
        roleButtonGroup.add(vendorRadioBtn);
        roleButtonGroup.add(customerRadioBtn);
        roleButtonGroup.add(runnerRadioBtn);
        roleButtonGroup.add(adminRadioBtn);


        // Enable the JComboBox only if the Vendor radio button is selected
        vendorRadioBtn.addActionListener(e -> {
            foodCuisineTypeJComboBox.setEnabled(vendorRadioBtn.isSelected());
        });

        // Disable the JComboBox only if the other radio buttons (other than Vendor radio button) are selected
        customerRadioBtn.addActionListener(e -> {
            // Reset the JComboBox to NONE
            foodCuisineTypeJComboBox.setSelectedIndex(0);
            // Disable the JComboBox when the customer radio button is selected
            foodCuisineTypeJComboBox.setEnabled(false);
        });

        runnerRadioBtn.addActionListener(e -> {
            // Reset the JComboBox to NONE
            foodCuisineTypeJComboBox.setSelectedIndex(0);
            // Disable the JComboBox when the runner radio button is selected
            foodCuisineTypeJComboBox.setEnabled(false);
        });

        adminRadioBtn.addActionListener(e -> {
            // Reset the JComboBox to NONE
            foodCuisineTypeJComboBox.setSelectedIndex(0);
            // Disable the JComboBox when the admin radio button is selected
            foodCuisineTypeJComboBox.setEnabled(false);
        });

        registerBtn = new JButton("Register");
        resetBtn = new JButton("Reset");
        returnBtn = new JButton("Cancel");

        registerBtn.setFocusable(false);
        resetBtn.setFocusable(false);
        returnBtn.setFocusable(false);

        registerBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        returnBtn.addActionListener(this);

        // Add a window listener to handle window closing event
        userRegistrationPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                goBackToAdminDashboard();
            }
        });

        JPanel panel = new JPanel(new GridLayout(8, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("UID:"));
        panel.add(uidField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(vendorRadioBtn);
        panel.add(new JLabel(""));
        panel.add(customerRadioBtn);
        panel.add(new JLabel(""));
        panel.add(runnerRadioBtn);
        panel.add(new JLabel(""));
        panel.add(adminRadioBtn);
        panel.add(new JLabel("Cuisine Type: "));
        panel.add(foodCuisineTypeJComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(registerBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(returnBtn);

        userRegistrationPage.setLayout(new BorderLayout());
        userRegistrationPage.add(panel, BorderLayout.CENTER);
        userRegistrationPage.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == registerBtn) {
                // Perform registration logic here
                String username = usernameField.getText();
                String uid = uidField.getText();
                int password = Integer.parseInt(passwordField.getText());

                // Determine the selected role
                userRoles selectedRole = getUserRole();

                // Determine the selected Cuisine Type
                foodCuisineType selectedCuisineType = (foodCuisineType) foodCuisineTypeJComboBox.getSelectedItem();

                // Check there are any empty inputs
                if (username == null || uid == null || username.isEmpty() || uid.isEmpty() || password == 0 || selectedRole == USER) {
                    JOptionPane.showMessageDialog(userRegistrationPage, "Empty input detected! Please fill up all entry fields.");
                    return;
                }
                // Check if FoodItemID already exists
                if (Admin.isUserExists(uid)) {
                    JOptionPane.showMessageDialog(userRegistrationPage, "UID already exists! Please choose a different UID.");
                    return;
                }

                // Check for valid Cuisine Type assigned to Vendor
                if (selectedRole == userRoles.VENDOR && selectedCuisineType == foodCuisineType.NONE){
                    JOptionPane.showMessageDialog(userRegistrationPage, "Invalid cuisine type assigned! Please choose a valid cuisine type.");
                    return;
                }
                Admin.addNewUser(username, uid, password, selectedRole, selectedCuisineType, 0);

                // Reset the fields after registration
                resetFields();

                // Display a summary of the new user information
                JOptionPane.showMessageDialog(userRegistrationPage, "New user created successfully!\n" +
                        "Username: " + username + "\n" +
                        "UID: " + uid + "\n" +
                        "Role: " + selectedRole + "\n" +
                        "Cuisine Type: " + selectedCuisineType);
            } else if (e.getSource() == resetBtn) {
                resetFields();
            } else if (e.getSource() == returnBtn) {
                goBackToAdminDashboard();
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(userRegistrationPage, "Invalid input! Please check your entries and try again.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userRegistrationPage, "Invalid password format! Please enter password in integers.");
        }
    }

    private userRoles getUserRole() {
        if (vendorRadioBtn.isSelected()) {
            return userRoles.VENDOR;
        } else if (customerRadioBtn.isSelected()) {
            return userRoles.CUSTOMER;
        } else if (runnerRadioBtn.isSelected()) {
            return userRoles.RUNNER;
        } else if (adminRadioBtn.isSelected()) {
            return userRoles.ADMIN;
        } else {
            return USER; // Default role if none selected
        }
    }

    private void resetFields() {
        usernameField.setText("");
        uidField.setText("");
        passwordField.setText("");
        roleButtonGroup.clearSelection();
        foodCuisineTypeJComboBox.setSelectedIndex(0);
        foodCuisineTypeJComboBox.setEnabled(false); // Disable after reset
    }

    private void goBackToAdminDashboard() {
        userRegistrationPage.setVisible(false);
        mainClass.adminDashboardPage.getAdminDashboard().setVisible(true);
    }
}
