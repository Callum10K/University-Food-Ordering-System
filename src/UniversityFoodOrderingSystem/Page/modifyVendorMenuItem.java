package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.FileIO.DataIO;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.Model.foodItem;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class modifyVendorMenuItem implements ActionListener {
    private JFrame modifyVendorMenuItem;
    private JButton saveBtn, cancelBtn;
    private JTextField foodItemNameField, priceField, foodItemIDField;
    private JComboBox<foodCategory> categoryComboBox;
    private JComboBox<foodCuisineType> foodCuisineTypeJComboBox;

    public JFrame getModifyVendorMenuItem() {
        // Load existing user information into the fields
        foodItem existingFoodItem = foodItem.obtainFoodItemInfo(mainClass.foodItemID, mainClass.loginUser.getUid());
        if (existingFoodItem != null) {
            foodItemNameField.setText(existingFoodItem.getFoodItemName());
            priceField.setText(String.valueOf(existingFoodItem.getPrice()));
            foodItemIDField.setText(existingFoodItem.getFoodItemID());
            categoryComboBox.setSelectedItem(existingFoodItem.getCategory());
            foodCuisineTypeJComboBox.setSelectedItem(existingFoodItem.getCuisineType());
        }
        return modifyVendorMenuItem;
    }

    public modifyVendorMenuItem() {
        modifyVendorMenuItem = new JFrame("Modify Vendor Menu Item");
        modifyVendorMenuItem.setSize(400, 300);
        modifyVendorMenuItem.setLocationRelativeTo(null);

        foodItemIDField = new JTextField(20);
        foodItemNameField = new JTextField(20);
        priceField = new JTextField(20);
        categoryComboBox = new JComboBox<>(foodCategory.values());
        foodCuisineTypeJComboBox = new JComboBox<>(foodCuisineType.values());
        saveBtn = new JButton("Save");
        cancelBtn = new JButton("Cancel");

        saveBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        // Set the values for specified entry fields as non-editable
        foodItemIDField.setEditable(false);
        foodCuisineTypeJComboBox.setEditable(false);
        foodCuisineTypeJComboBox.setEnabled(false);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Food Item ID:"));
        panel.add(foodItemIDField);
        panel.add(new JLabel("Food Item Name:"));
        panel.add(foodItemNameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);
        panel.add(new JLabel("Cuisine Type:"));
        panel.add(foodCuisineTypeJComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);

        modifyVendorMenuItem.setLayout(new BorderLayout());
        modifyVendorMenuItem.add(panel, BorderLayout.CENTER);
        modifyVendorMenuItem.add(buttonPanel, BorderLayout.SOUTH);

        // Add a window listener to handle window closing event
        modifyVendorMenuItem.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                goBackToManageVendorMenu();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == saveBtn) {
                saveModifiedFoodItem();
            } else if (e.getSource() == cancelBtn) {
                goBackToManageVendorMenu();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(modifyVendorMenuItem, "Invalid price format! Please enter a valid number.");
        } catch (Exception x) {
            JOptionPane.showMessageDialog(modifyVendorMenuItem, "Invalid input! Try Again!");
        }
    }

    private void saveModifiedFoodItem() {
        // Implement logic to save the modified food item information
        String selectedFoodItemID = mainClass.foodItemID;
        String newFoodItemName = foodItemNameField.getText();
        double newPrice = Double.parseDouble(priceField.getText());
        foodCategory newCategory = (foodCategory) categoryComboBox.getSelectedItem();

        if (selectedFoodItemID == null || newFoodItemName == null || selectedFoodItemID.isEmpty() || newFoodItemName.isEmpty() || newPrice == 0) {
            JOptionPane.showMessageDialog(modifyVendorMenuItem, "Empty input detected! Please fill up all entry fields.");
            return;
        }

        // Update the food item information
        Vendor.modifyFoodItemAttributes(selectedFoodItemID, newFoodItemName, newPrice, mainClass.loginUser.getUid(), newCategory);

        modifyVendorMenuItem.setVisible(false);
        mainClass.manageVendorMenuPage.getManageVendorMenu().setVisible(true);
    }

    private void goBackToManageVendorMenu() {
        modifyVendorMenuItem.setVisible(false);
        mainClass.manageVendorMenuPage.getManageVendorMenu().setVisible(true);
    }
}
