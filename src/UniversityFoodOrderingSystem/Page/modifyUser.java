package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.FileIO.DataIO;
import UniversityFoodOrderingSystem.Model.Admin;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static UniversityFoodOrderingSystem.Enum.userRoles.USER;

public class modifyUser implements ActionListener {
    private JFrame modifyUserPage;
    private JButton saveBtn, cancelBtn;
    private JTextField usernameField, uidField, passwordField;
    private JComboBox<userRoles> roleComboBox;
    private JComboBox<foodCuisineType> cuisineComboBox;

    public JFrame getModifyUser() {
        // Load existing user information into the fields
        User existingUser = User.obtainUserInfoUsingUID(mainClass.uid);
        if (existingUser != null) {
            usernameField.setText(existingUser.getName());
            uidField.setText(existingUser.getUid());
            passwordField.setText(String.valueOf(existingUser.getPassword()));
            roleComboBox.setSelectedItem(existingUser.getRole());
            cuisineComboBox.setSelectedItem(existingUser.getFoodCuisine());
        }
        return modifyUserPage;
    }

    public modifyUser() {
        modifyUserPage = new JFrame("Modify User");
        modifyUserPage.setSize(400, 300);
        modifyUserPage.setLocationRelativeTo(null);

        usernameField = new JTextField(20);
        uidField = new JTextField(20);
        passwordField = new JTextField(20);
        roleComboBox = new JComboBox<>(userRoles.values()); // Use values() method to get enum values
        cuisineComboBox = new JComboBox<>(foodCuisineType.values()); // Use values() method to get enum values

        // Set the values for specified entry fields as non-editable
        uidField.setEditable(false);
        roleComboBox.setEditable(false);
        roleComboBox.setEnabled(false);
        cuisineComboBox.setEditable(false);
        cuisineComboBox.setEnabled(false);

        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Existing UID:"));
        panel.add(uidField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Role:"));
        panel.add(roleComboBox);
        panel.add(new JLabel("Cuisine Type:"));
        panel.add(cuisineComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        modifyUserPage.setLayout(new BorderLayout());
        modifyUserPage.add(panel, BorderLayout.CENTER);
        modifyUserPage.add(buttonPanel, BorderLayout.SOUTH);

        // Add a window listener to handle window closing event
        modifyUserPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                goBackToDashboard();
            }
        });

        saveBtn.addActionListener(this);
        cancelBtn.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == saveBtn) {
                saveModifiedUser();
            } else if (e.getSource() == cancelBtn) {
                goBackToDashboard();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(modifyUserPage, "Invalid input! Try Again!");
        }
    }

    private void saveModifiedUser() {
        // Implement logic to save the modified user information
        String newUsername = usernameField.getText();
        int newPassword = Integer.parseInt(passwordField.getText());
        userRoles role = (userRoles) roleComboBox.getSelectedItem();
        foodCuisineType cuisineType = (foodCuisineType) cuisineComboBox.getSelectedItem();

        // Check there are any empty inputs
        if (newUsername == null || newUsername.isEmpty() || newPassword == 0){
            JOptionPane.showMessageDialog(modifyUserPage, "Empty input detected! Please fill up all entry fields.");
            return;
        }

        // Update the user information
        User modifiedUser = User.obtainUserInfoUsingUID(mainClass.uid);
        modifiedUser.setName(newUsername);
        modifiedUser.setPassword(newPassword);
        modifiedUser.setRole(role);
        modifiedUser.setFoodCuisine(cuisineType);

        Admin.modifyUserCredentials(mainClass.uid, newUsername, newPassword);

        // Update Vendor Name in allFoodItem arrayList
        Admin.replaceOldUIDWithNewModifiedUID(mainClass.uid, newUsername);

        modifyUserPage.setVisible(false);
        mainClass.adminDashboardPage.getAdminDashboard().setVisible(true);
    }

    private void goBackToDashboard() {
        modifyUserPage.setVisible(false);
        mainClass.adminDashboardPage.getAdminDashboard().setVisible(true);
    }
}
