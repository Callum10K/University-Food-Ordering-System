package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class manageVendorMenu implements ActionListener {
    private JFrame manageVendorMenuPage;
    private JButton backBtn, addMenuItemBtn, deleteMenuItemBtn, modifyMenuItemBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;

    public JFrame getManageVendorMenu() {
        try {
            Vendor.displayVendorMenuInTable(tableModel, mainClass.loginUser.getUid());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(manageVendorMenuPage, "Error loading menu items. Try Again!");
        }
        return manageVendorMenuPage;
    }

    public manageVendorMenu() {
        manageVendorMenuPage = new JFrame("Vendor Menu Management");
        manageVendorMenuPage.setSize(750, 400);
        manageVendorMenuPage.setLocationRelativeTo(null);

        addMenuItemBtn = new JButton("Add Menu Item");
        modifyMenuItemBtn = new JButton("Modify Menu Item");
        deleteMenuItemBtn = new JButton("Delete Menu Item");
        backBtn = new JButton("Back");

        addMenuItemBtn.setFocusable(false);
        modifyMenuItemBtn.setFocusable(false);
        deleteMenuItemBtn.setFocusable(false);
        backBtn.setFocusable(false);

        addMenuItemBtn.addActionListener(this);
        modifyMenuItemBtn.addActionListener(this);
        deleteMenuItemBtn.addActionListener(this);
        backBtn.addActionListener(this);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Item Name", "Cuisine Type", "Price", "Category"}, 0);
        menuTable = new JTable(tableModel);

        // Add a window listener to handle window closing event
        manageVendorMenuPage.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                goBackToDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        buttonPanel.add(addMenuItemBtn);
        buttonPanel.add(modifyMenuItemBtn);
        buttonPanel.add(deleteMenuItemBtn);
        buttonPanel.add(backBtn);

        manageVendorMenuPage.setLayout(new BorderLayout());
        manageVendorMenuPage.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        manageVendorMenuPage.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == backBtn) {
                goBackToDashboard();
            } else if (e.getSource() == modifyMenuItemBtn) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow != -1) {
                    mainClass.foodItemID = (String) tableModel.getValueAt(selectedRow, 0);

                    manageVendorMenuPage.setVisible(false);
                    modifyVendorMenuItem modifyVendorMenuItemPage = new modifyVendorMenuItem();
                    modifyVendorMenuItemPage.getModifyVendorMenuItem().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(manageVendorMenuPage, "Please select a menu item to modify.");
                }
            } else if (e.getSource() == addMenuItemBtn) {
                manageVendorMenuPage.setVisible(false);
                mainClass.addFoodItemMenuPage.getAddFoodItemFrame().setVisible(true);
            } else if (e.getSource() == deleteMenuItemBtn) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow != -1) {
                    String foodItemID = (String) tableModel.getValueAt(selectedRow, 0);

                    tableModel.removeRow(selectedRow);
                    Vendor.deleteFoodItem(mainClass.loginUser.getUid(), foodItemID);
                } else {
                    JOptionPane.showMessageDialog(manageVendorMenuPage, "Please select a menu item to delete.");
                }
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(manageVendorMenuPage, "Invalid input! Try Again!");
        }
    }

    private void goBackToDashboard() {
        manageVendorMenuPage.setVisible(false);
        mainClass.vendorDashboardPage.getVendorDashboard().setVisible(true);
    }
}
