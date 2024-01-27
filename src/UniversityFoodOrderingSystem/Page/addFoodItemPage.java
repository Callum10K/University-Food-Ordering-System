package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class addFoodItemPage implements ActionListener {
    private JFrame addFoodItemFrame;
    private JButton addBtn, resetBtn, returnBtn;
    private JTextField foodItemIDField, foodItemNameField, priceField;
    private JComboBox<foodCategory> categoryComboBox;

    public JFrame getAddFoodItemFrame() {
        return addFoodItemFrame;
    }

    public addFoodItemPage() {
        addFoodItemFrame = new JFrame("Add Food Item");
        addFoodItemFrame.setSize(400, 300);
        addFoodItemFrame.setLocationRelativeTo(null);

        foodItemIDField = new JTextField(20);
        foodItemNameField = new JTextField(20);
        priceField = new JTextField(20);

        categoryComboBox = new JComboBox<>(foodCategory.values());

        addBtn = new JButton("Add");
        resetBtn = new JButton("Reset");
        returnBtn = new JButton("Back");

        addBtn.addActionListener(this);
        resetBtn.addActionListener(this);
        returnBtn.addActionListener(this);

        // Add a window listener to handle window closing event
        addFoodItemFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                returnToPreviousPage();
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Food Item ID:"));
        panel.add(foodItemIDField);
        panel.add(new JLabel("Food Item Name:"));
        panel.add(foodItemNameField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);
        panel.add(new JLabel("Category:"));
        panel.add(categoryComboBox);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addBtn);
        buttonPanel.add(resetBtn);
        buttonPanel.add(returnBtn);

        addFoodItemFrame.setLayout(new BorderLayout());
        addFoodItemFrame.add(panel, BorderLayout.CENTER);
        addFoodItemFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == addBtn) {
                // Perform logic to add food item
                String foodItemID = foodItemIDField.getText();
                String foodItemName = foodItemNameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String vendorUID = mainClass.loginUser.getUid();
                foodCategory category = (foodCategory) categoryComboBox.getSelectedItem();

                // Check for Empty Input for All Input Fields
                if (foodItemID == null || foodItemName == null || foodItemID.isEmpty() || foodItemName.isEmpty() || vendorUID == null || vendorUID.isEmpty() || price == 0) {
                    JOptionPane.showMessageDialog(addFoodItemFrame, "Empty input detected! Please fill up all entry fields.");
                    return;
                }

                // Check if FoodItemID already exists
                if (Vendor.isFoodItemExists(foodItemID)) {
                    JOptionPane.showMessageDialog(addFoodItemFrame, "Food Item ID already exists! Please choose a different Food Item ID.");
                    return;
                }
                // Add the new food item to the menu
                Vendor.addFoodItem(foodItemID, foodItemName, mainClass.loginUser.getFoodCuisine(), price, mainClass.loginUser.getName(),vendorUID, category);

                // Reset the fields after adding
                resetFields();

                // Display a summary of the new food item information
                JOptionPane.showMessageDialog(addFoodItemFrame, "New food item added successfully!\n" +
                        "Food Item ID: " + foodItemID + "\n" +
                        "Food Item Name: " + foodItemName + "\n" +
                        "Cuisine Type: " + mainClass.loginUser.getFoodCuisine() + "\n" +
                        "Price: " + price + "\n" +
                        "Vendor Name: " + mainClass.loginUser.getName() + "\n" +
                        "Vendor UID: " + vendorUID + "\n" +
                        "Category: " + category);
            } else if (e.getSource() == resetBtn) {
                resetFields();
            } else if (e.getSource() == returnBtn) {
                returnToPreviousPage();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(addFoodItemFrame, "Invalid price format! Please enter a valid number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(addFoodItemFrame, "An error occurred. Please try again.");
        }
    }

    private void resetFields() {
        foodItemIDField.setText("");
        foodItemNameField.setText("");
        priceField.setText("");
        categoryComboBox.setSelectedIndex(0);
    }

    private void returnToPreviousPage() {
        addFoodItemFrame.setVisible(false);
        mainClass.manageVendorMenuPage.getManageVendorMenu().setVisible(true);
    }
}
