package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCategory;
import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Order;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class customerViewMenu implements ItemListener, ActionListener {
    private JFrame viewMenuCustomer;
    private JButton backBtn;
    private JButton orderBtn;
    private JButton viewCartBtn;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> viewMenuTypeComboBox;
    private static String chosenVendor;
    private static int n = 0;
    
    public JFrame getviewMenuCustomer() {
        update();
        viewMenuTypeComboBox.setSelectedIndex(0);
        Customer.displayMenuItems(tableModel);
        viewCartBtn.setVisible(false);
        return viewMenuCustomer;
    }
    
    public JFrame getviewMenuCustomerView() {
        update();
        viewMenuTypeComboBox.setSelectedIndex(0);
        Customer.displayMenuItems(tableModel);
        if (!allOrder.isEmpty()) {
            viewCartBtn.setVisible(true);
        } else {
            viewCartBtn.setVisible(false);
        }
        return viewMenuCustomer;
    }
    
    public static void getChosenVendor(String cVendor) {
        chosenVendor = cVendor;
    }

    private void update() {
        n = 0;
        viewMenuCustomer = new JFrame("View Menu");
        viewMenuCustomer.setSize(800, 400);
        viewMenuCustomer.setLocationRelativeTo(null);

        String[] viewMenuOptions = {"All Menu Items", "Chinese", "Western", "Japanese", "Other"};
        viewMenuTypeComboBox = new JComboBox<>(viewMenuOptions);
        viewMenuTypeComboBox.addItemListener(this);

        backBtn = new JButton("Back");
        backBtn.addActionListener(e -> goBackToDashboard());
        
        orderBtn = new JButton("Order");
        orderBtn.addActionListener(this);
        
        for (Order o: allOrder) {
            n += 1;
        }
        viewCartBtn = new JButton("+ " + n + " View Your Cart");
        viewCartBtn.addActionListener(this);

        tableModel = new DefaultTableModel(new Object[]{"Food Item ID", "Food Item Name", "Cuisine Type", "Price", "Vendor Name", "Vendor UID", "Category"}, 0);
        menuTable = new JTable(tableModel);

        // Load and display all menu items in the JTable initially
        try {
            Customer.displayMenuItems(tableModel);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(viewMenuCustomer, "Error loading menu items. Try Again!");
        }

        // Add a window listener to handle window closing event
        viewMenuCustomer.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                goBackToDashboard();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(orderBtn);
        buttonPanel.add(viewCartBtn);

        viewMenuCustomer.setLayout(new BorderLayout());
        viewMenuCustomer.add(viewMenuTypeComboBox, BorderLayout.NORTH);
        viewMenuCustomer.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        viewMenuCustomer.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Handle the selected item change event
            try {
                String selectedItem = (String) viewMenuTypeComboBox.getSelectedItem();
                if (selectedItem.equals("Chinese")) {
                    Customer.displaySelectedCuisineMenuItems(tableModel, foodCuisineType.CHINESE);
                } else if (selectedItem.equals("Western")) {
                    Customer.displaySelectedCuisineMenuItems(tableModel, foodCuisineType.WESTERN);
                } else if (selectedItem.equals("Japanese")) {
                    Customer.displaySelectedCuisineMenuItems(tableModel, foodCuisineType.JAPANESE);
                } else if (selectedItem.equals("Other")) {
                    Customer.displaySelectedCuisineMenuItems(tableModel, foodCuisineType.OTHER);
                } else {
                    Customer.displayMenuItems(tableModel);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(viewMenuCustomer, "Error filtering menu items. Try Again!");
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            viewMenuCustomer.setVisible(false);
            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
        } else if (e.getSource() == orderBtn) {
            //mainClass.orderModuleMenu.getOrderModuleMainPage().setVisible(true);
            int selectedRow = menuTable.getSelectedRow();
                if (selectedRow != -1) {
                    String vendorUID = (String) tableModel.getValueAt(selectedRow, 5);
                    if (vendorUID.equals(chosenVendor) || allOrder.isEmpty()) {
                        viewMenuCustomer.setVisible(false);
                        // Get the foodItemID from the selected row
                        String foodItemID = (String) tableModel.getValueAt(selectedRow, 0);
                        String foodItemName = (String) tableModel.getValueAt(selectedRow, 1);
                        Double price = (Double) tableModel.getValueAt(selectedRow, 3);
                        foodCategory category = (foodCategory) tableModel.getValueAt(selectedRow, 6);
                        mainClass.orderModuleMenu.getFoodData(foodItemID, foodItemName, price, category, vendorUID);
                        mainClass.orderModuleMenu = new orderModuleMainPage();
                        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                        //LocalDateTime now = LocalDateTime.now();  
                        mainClass.orderModuleMenu.getOrderModuleMainPage().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(viewMenuCustomer, "You can only order from one vendor at a time. \n" + "Current Vendor: " + chosenVendor);
                    }
                    //Customer.addToChart(foodItemID, "Not ready", now, 0, "Status");
                    //Vendor.addFoodItem(foodItemID, foodItemName, price, vendorUID, category);
                    // Remove the food item from the table model and data model
                    //tableModel.removeRow(selectedRow);
                    //Vendor.deleteFoodItem(mainClass.loginUser.getUid(), foodItemID);
                } else {
                    JOptionPane.showMessageDialog(viewMenuCustomer, "Please select a menu item to order.");
            }
        } else if (e.getSource() == viewCartBtn) {
            viewMenuCustomer.setVisible(false);
            mainClass.orderViewCartPage.getOrderViewCartPage().setVisible(true);
        }
    }

    private void goBackToDashboard() {
        viewMenuCustomer.setVisible(false);
        mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
    }
}
