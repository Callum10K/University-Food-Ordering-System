package UniversityFoodOrderingSystem.Page;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allDeliveries;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allNotification;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allTransaction;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Delivery;
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.Order;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.Transaction;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.Model.Vendor;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import UniversityFoodOrderingSystem.mainClass;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JTextField;


public class orderViewCart implements ItemListener, ActionListener {
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            // Handle the selected item change event
            try {
                if (radioButton3.isSelected()) {
                    locationPanel.setVisible(true);
                    deliveryFeeTitle.setVisible(true);
                    totalPriceLabel.setVisible(false);
                    newTotalPriceLabel.setVisible(true);
                }
                else if (radioButton1.isSelected()) {
                    locationPanel.setVisible(false);
                    deliveryFeeTitle.setVisible(false);
                    totalPriceLabel.setVisible(true);
                    newTotalPriceLabel.setVisible(false);
                } 
                else if (radioButton2.isSelected()) {
                    locationPanel.setVisible(false);
                    deliveryFeeTitle.setVisible(false);
                    totalPriceLabel.setVisible(true);
                    newTotalPriceLabel.setVisible(false);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(orderViewCartPage, "Error filtering menu items. Try Again!");
            }
        }
    }
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == backButton) {
                if (reorder) {
                    orderViewCartPage.setVisible(false);
                    mainClass.orderHistoryPage.getOrderHistory().setVisible(true);
                } else {
                    orderViewCartPage.setVisible(false);
                    mainClass.customerViewMenuPage.getviewMenuCustomerView().setVisible(true);
                }
            } else if (e.getSource() == editButton) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow != -1) {
                    orderViewCartPage.setVisible(false);
                    String foodItemID = (String) tableModel.getValueAt(selectedRow, 0);
                    mainClass.orderModuleMenu = new orderModuleMainPage();
                    mainClass.orderModuleMenu.getEditOrderModuleMainPage(foodItemID).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(orderViewCartPage, "Please select a menu item to edit.");
                } 
            } else if (e.getSource() == deleteButton) {
                int selectedRow = menuTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the foodItemID from the selected row
                    String foodItemID = (String) tableModel.getValueAt(selectedRow, 0);

                    // Remove the food item from the table model and data model
                    //tableModel.removeRow(selectedRow);
                    orderViewCartPage.setVisible(false);
                    Customer.deleteOrder(foodItemID);
                    displayCart();
                    getOrderViewCartPage();
                    orderViewCartPage.setVisible(true);
                }
            } else if (e.getSource() == placeOrderButton) {
                if (radioButton1.isSelected() || radioButton2.isSelected() || radioButton3.isSelected()) {
                    if (radioButton1.isSelected()) {
                        option = "Dine In";
                    } else if (radioButton2.isSelected()) {
                        option = "TakeAway";
                    } else if (radioButton3.isSelected()) {
                        option = "Delivery";
                    }
                    if (option.equals("Delivery") && locationField.getText().equals("")) {
                        JOptionPane.showMessageDialog(orderViewCartPage, "Location cannot be empty.");
                    } else {
                        if (option.equals("Delivery")) {
                            totalPrice += 7;
                        }
                        for (User u: allUsers) {
                            if (u.getUid().equals(customerID)) {
                                if (u.getBalance() >= totalPrice) {
                                    u.setBalance(u.getBalance() - totalPrice);
                                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();  
                                    String formattedDateTime = now.format(dtf);
                                    orderIDNum += 1;
                                    orderID = "OID"+ orderIDNum;
                                    notificationIDNum += 1;
                                    notificationID = "NID"+ notificationIDNum;
                                    transactionIDNum += 1;
                                    transactionID = "TID"+ transactionIDNum;
                                    if (!allOrderConfirm.isEmpty()) { 
                                        for (OrderConfirm o: allOrderConfirm) {
                                            if (o.getOrderID().equals(orderID)) {
                                                orderIDNum = Integer.parseInt(o.getOrderID().substring(3)) + 1;
                                                orderID = "OID"+ orderIDNum;
                                            }
                                        }
                                    }
                                    if (!allNotification.isEmpty()) { 
                                        for (Notification n: allNotification) {
                                            if (n.getNID().equals(notificationID)) {
                                                notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                                                notificationID = "NID"+ notificationIDNum;
                                            }
                                        }
                                    }
                                    if (!allTransaction.isEmpty()) { 
                                        for (Transaction t: allTransaction) {
                                            if (t.getTransactionID().equals(transactionID)) {
                                                transactionIDNum = Integer.parseInt(t.getTransactionID().substring(3)) + 1;
                                                transactionID = "TID"+ transactionIDNum;
                                            }
                                        }
                                    }
                                    if (reorder) {
                                        for (OrderConfirm o: allOrderConfirm) {
                                            if (o.getOrderID().equals(reorderID)) {
                                                Customer.addOrder(o.getFoodItemID(), o.getFoodItemName(), o.getPrice(), o.getVendorUID(), o.getCategory(), o.getQuantity());
                                            }
                                        }
                                    }
                                    notificationSent = false;
                                    for (Order o: allOrder) {
                                        for (User r: allUsers) {
                                            if (r.getUid().equals(o.getVendorUID())) {
                                                r.setBalance(r.getBalance() + totalPrice - 7);
                                            }
                                        }
                                        Customer.confirmOrder(customerID, orderID, o.getFoodItemID(), o.getFoodItemName(), o.getPrice(), o.getVendorUID(), o.getCategory(), o.getQuantity(), option, formattedDateTime, "Pending", "-", "F", "F", "-");  
                                        if (!notificationSent) {
                                            Vendor.getNotification(notificationID, formattedDateTime, "[" + orderID + "]" + " Order has been submitted by " + customerID, o.getVendorUID());
                                            int vendorPrice = totalPrice - 7;
                                            Customer.getTransaction(transactionID, formattedDateTime, "[" + orderID + "] Transfer to " + o.getVendorUID(), "-RM" + vendorPrice, mainClass.loginUser.getUid());
                                            transactionIDNum += 1;
                                            transactionID = "TID"+ transactionIDNum;
                                            Customer.getTransaction(transactionID, formattedDateTime, "[" + orderID + "] Delivery Fee", "-RM7", mainClass.loginUser.getUid());
                                        }
                                        notificationSent = true;
                                    }
                                    allOrder.clear();
                                    mainClass.vendorManageOrderPage.getLocation(locationField.getText());
                                    orderViewCartPage.setVisible(false);
                                    mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
                                    //mainClass.PlaceOrderPage.getPlaceOrderPage().setVisible(true);
                                } else {
                                    JOptionPane.showMessageDialog(orderViewCartPage, "Your balance is not sufficient for placing this order.");
                                }
                            }
                        }
                    }
                } else {
                    // Show a message or take appropriate action since no radio button is selected
                    JOptionPane.showMessageDialog(orderViewCartPage, "Please select a radio button before performing the action.");
                }
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(orderViewCartPage, "Invalid input! Try Again!");
        }
    }
   
    private JFrame orderViewCartPage;
    private Label cartTitle;
    private Label totalPriceLabel;
    private Label deliveryFeeTitle;
    private Label newTotalPriceLabel;
    private JButton backButton;
    private JButton editButton;
    private JButton placeOrderButton;
    private JButton deleteButton;
    private JTextField locationField;
    private JPanel locationPanel;
    private static int totalPrice = 0;
    private JTable menuTable;
    private DefaultTableModel tableModel;
    private static String customerID;
    private static String notificationID;
    private static int notificationIDNum = 0;
    private static Boolean notificationSent = false;
    private static String transactionID;
    private static int transactionIDNum = 0;
    private static String option;
    private static String orderID;
    private static String reorderID;
    private static int orderIDNum = 0;
    private static Boolean reorder = false;
    private static String runnerID = "";
    JRadioButton radioButton1;
    JRadioButton radioButton2;
    JRadioButton radioButton3;
    
    public static void getCustomerID(String CID) {
        customerID = CID;
    }

    public JFrame getOrderViewCartPage() {
        reorder = false;
        updateLabels();
        return orderViewCartPage;
    }
    
    public JFrame getOrderViewCartReorderPage(String OID) {
        reorder = true;
        reorderID = OID;
        updateLabels();
        editButton.setVisible(false);
        deleteButton.setVisible(false);
        return orderViewCartPage;
    }

    private void updateLabels() {
        totalPrice = 0;
        option = "";
        orderViewCartPage = new JFrame("View Cart");
        orderViewCartPage.setSize(600, 600);
        orderViewCartPage.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1)); // 1 column, as many rows as needed
        orderViewCartPage.add(mainPanel);

        // Clear existing labels
        mainPanel.removeAll();

        cartTitle = new Label("Your cart", Label.CENTER);
        cartTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        mainPanel.add(cartTitle);
        
        tableModel = new DefaultTableModel(new Object[]{"Food Item ID", "Food Item Name", "Category", "Quantity", "Price (RM)"}, 0);
        menuTable = new JTable(tableModel);
        mainPanel.add(new JScrollPane(menuTable), BorderLayout.CENTER);
        
        displayCart();
        
        JPanel RadioButtons = new JPanel(new FlowLayout());
        
        radioButton1 = new JRadioButton("Dine In");
        radioButton2 = new JRadioButton("TakeAway");
        radioButton3 = new JRadioButton("Delivery");
        
        // Create a ButtonGroup to group the radio buttonsq
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        radioButton1.setFocusable(false);
        radioButton2.setFocusable(false);
        radioButton3.setFocusable(false);
        RadioButtons.add(radioButton1);
        RadioButtons.add(radioButton2);
        RadioButtons.add(radioButton3);
        mainPanel.add(RadioButtons);
        
        locationPanel = new JPanel(new FlowLayout());
        mainPanel.add(locationPanel);
        
        JLabel locationLabel = new JLabel("Location: ");
        locationField = new JTextField(20);
        locationPanel.add(locationLabel);
        locationPanel.add(locationField);
        locationPanel.setVisible(false);
        
        deliveryFeeTitle = new Label("Delivery Fee: RM7", Label.CENTER);
        deliveryFeeTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(deliveryFeeTitle);
        deliveryFeeTitle.setVisible(false);
        
        totalPriceLabel = new Label("Total: RM" + totalPrice, Label.CENTER);
        totalPriceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(totalPriceLabel);
        
        int newPrice = totalPrice + 7;
        newTotalPriceLabel = new Label("Total: RM" + totalPrice + " + RM7 = RM" + newPrice, Label.CENTER);
        newTotalPriceLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(newTotalPriceLabel);
        newTotalPriceLabel.setVisible(false);
            
        JPanel buttonPanel = new JPanel(new FlowLayout());
        mainPanel.add(buttonPanel);
        
        backButton = new JButton("Back");
        backButton.setFocusable(false);
        buttonPanel.add(backButton);
        
        placeOrderButton = new JButton("Place Order");
        placeOrderButton.setFocusable(false);
        buttonPanel.add(placeOrderButton);
        
        editButton = new JButton("Edit order");
        placeOrderButton.setFocusable(false);
        buttonPanel.add(editButton);
        
        deleteButton = new JButton("Delete order");
        deleteButton.setFocusable(false);
        buttonPanel.add(deleteButton);
        
        backButton.addActionListener(this);
        placeOrderButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        radioButton1.addItemListener(this);
        radioButton2.addItemListener(this);
        radioButton3.addItemListener(this);

        // Update the layout
        //orderViewCartPage.revalidate();
        //orderViewCartPage.repaint();
        
    }
    
    private void displayCart() {
        // Clear the current table data
        tableModel.setRowCount(0);

        // Retrieve the menu items and add them to the tableModel
        if (reorder) {
            for (OrderConfirm o: allOrderConfirm) {
                if (o.getOrderID().equals(reorderID)) {
                    tableModel.addRow(new Object[]{o.getFoodItemID(), o.getFoodItemName(), o.getCategory(), o.getQuantity(), o.getPrice() * o.getQuantity()});
                    totalPrice += o.getPrice() * o.getQuantity();
                }
            }
        } else {
            for (Order o: allOrder) {
                tableModel.addRow(new Object[]{o.getFoodItemID(), o.getFoodItemName(), o.getCategory(), o.getQuantity(), o.getPrice() * o.getQuantity()});
                totalPrice += o.getPrice() * o.getQuantity();
            }
        }
    }
}
