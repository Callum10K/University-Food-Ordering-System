package UniversityFoodOrderingSystem.Page;
import UniversityFoodOrderingSystem.Enum.foodCategory;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import UniversityFoodOrderingSystem.mainClass;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Order;


public class orderModuleMainPage implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == backButton) {
                currentValue = 1;
                orderModuleMainPage.setVisible(false);
                if (viewCart) {
                    if (Edit) {
                        mainClass.orderViewCartPage.getOrderViewCartPage().setVisible(true);
                    } else {
                        mainClass.customerViewMenuPage.getviewMenuCustomerView().setVisible(true);
                    }
                } else {
                    mainClass.customerViewMenuPage.getviewMenuCustomer().setVisible(true);
                }
            } else if (e.getSource() == addToCartButton) {
                mainClass.customerViewMenuPage.getChosenVendor(vendorID);
                if (foodFound) {
                    for (Order o: allOrder) {
                        if (o.getFoodItemID().equals(foodID)) {
                            o.setQuantity(currentValue);
                        } 
                    }
                } else {
                    Customer.addOrder(foodID, foodName, priceOfItem, vendorID, cat, currentValue);
                }
                orderModuleMainPage.setVisible(false);
                viewCart = true;
                if (Edit) {
                    mainClass.orderViewCartPage.getOrderViewCartPage().setVisible(true);
                } else {
                     mainClass.customerViewMenuPage.getviewMenuCustomerView().setVisible(true);
                } 
            } 
        } catch (Exception x) {
            JOptionPane.showMessageDialog(orderModuleMainPage, "Invalid input! Try Again!");
        }
    }
    private JFrame orderModuleMainPage;

    public JFrame getOrderModuleMainPage() {
        update();
        return orderModuleMainPage;
    }
    
    public JFrame getEditOrderModuleMainPage(String ID) {
        for (Order o: allOrder) {
            if (o.getFoodItemID().equals(ID)) {
                foodID = o.getFoodItemID();
                foodName = o.getFoodItemName();
                priceOfItem = o.getPrice();
                cat = o.getCategory();
                vendorID = o.getVendorUID();
            } 
        }
        update();
        Edit = true;
        return orderModuleMainPage;
    }
    
    private Label foodTitle;
    private Label vendorTitle;
    private static String foodID, foodName, vendorID;
    private static foodCategory cat;
    private static double priceOfItem;
    private static Boolean viewCart = false;
    private static Boolean Edit = false;
    private static int currentValue = 1;
    private static Boolean foodFound = false;

    JButton backButton = new JButton("Back");
    JButton addToCartButton = new JButton("Add To Cart");

    public static void getFoodData(String foodItemID, String foodItemName, Double price, foodCategory category, String vendorUID) {
        foodID= foodItemID;
        foodName = foodItemName;
        priceOfItem = price;
        cat = category;
        vendorID = vendorUID;
    }

    public void update() {
        Edit = false;
        currentValue = 1;
        foodFound = false;
        for (Order o: allOrder) {
            if (o.getFoodItemID().equals(foodID)) {
                currentValue = o.getQuantity();
                foodFound = true;
            }
        }
        orderModuleMainPage = new JFrame("Order Page");
        orderModuleMainPage.setSize(500, 400);
        orderModuleMainPage.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1)); // 1 column, as many rows as needed
        orderModuleMainPage.add(mainPanel);
        
        mainPanel.removeAll();
        
        foodTitle = new Label(foodName, Label.CENTER);
        foodTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        mainPanel.add(foodTitle);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        mainPanel.add(buttonsPanel);

        JButton decrementButton = new JButton("-");
        decrementButton.setFocusable(false);
        buttonsPanel.add(decrementButton);

        JLabel valueLabel = new JLabel("Amount: " + currentValue);
        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        buttonsPanel.add(valueLabel);

        JButton incrementButton = new JButton("+");
        incrementButton.setFocusable(false);
        buttonsPanel.add(incrementButton);

        vendorTitle = new Label("Prepared by: " + vendorID, Label.CENTER);
        vendorTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        mainPanel.add(vendorTitle);
        
        /*JPanel reviewPanel = new JPanel(new FlowLayout());
        mainPanel.add(reviewPanel);
        
        JButton reviewButton = new JButton("View Reviews and Ratings");
        reviewButton.setFocusable(false);
        reviewPanel.add(reviewButton);*/
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        mainPanel.add(controlPanel);
        
        backButton.setFocusable(false);
        controlPanel.add(backButton);
        
        addToCartButton.setFocusable(false);
        controlPanel.add(addToCartButton);

        // ActionListener for the increment button
        incrementButton.addActionListener(e -> {
            currentValue++;
            valueLabel.setText("Amount: " + currentValue);
        });

        // ActionListener for the decrement button
        decrementButton.addActionListener(e -> {
            if (currentValue > 1){
                currentValue--;
            }
            valueLabel.setText("Amount: " + currentValue);
        });
       
        backButton.addActionListener(this);
        addToCartButton.addActionListener(this);
        
        //orderModuleMainPage.revalidate();
        //orderModuleMainPage.repaint();
    }
}