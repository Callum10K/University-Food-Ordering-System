package UniversityFoodOrderingSystem.Page;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allNotification;

import UniversityFoodOrderingSystem.Abstract.userDashboardCommonMethods;
import UniversityFoodOrderingSystem.Interface.userDashboard;
import UniversityFoodOrderingSystem.mainClass;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class customerDashboard extends userDashboardCommonMethods implements userDashboard, ActionListener {
    private JFrame customerDashboardPage;
    private JButton viewMenuBtn, historyBtn, checkNotificationBtn, viewBalanceBtn, transactionHistoryBtn, readReviewBtn, logoutBtn;
    private Label userGreetingsLabel;
    private static Boolean notificationRead = true;

    public JFrame getCustomerDashboard() {
        // Initialize User Greetings and adding Label to NORTH POSITION
        //userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        //userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        //customerDashboardPage.add(userGreetingsLabel, BorderLayout.NORTH);

        // Initializing Image Icon
        //ImageIcon customerImage = new ImageIcon("icons/customer.png");
        //JLabel customerLabel = new JLabel(customerImage);

        // Add the image label to the CENTER position
        //customerDashboardPage.add(customerLabel, BorderLayout.CENTER);
        update();
        displayUserGreetings();
        displayUserIcon();
        return customerDashboardPage;
        
    }
    
    public static void getNotificationRead(Boolean nr) {
        notificationRead = nr;
    }


    private void update() {
        customerDashboardPage = new JFrame("Customer Dashboard");
        customerDashboardPage.setSize(950, 250);
        customerDashboardPage.setLocationRelativeTo(null);
 
        viewMenuBtn = new JButton("View Menu");
        historyBtn = new JButton("View Order History");
        if (notificationRead) {
            checkNotificationBtn = new JButton("Check Notifications");
        } else {
            checkNotificationBtn = new JButton("Check Notifications*");
        }
        viewBalanceBtn = new JButton("View Balance");
        transactionHistoryBtn = new JButton("View Transaction History");
        readReviewBtn = new JButton("Read Reviews");
        logoutBtn = new JButton("Log out");

        viewMenuBtn.setFocusable(false);
        historyBtn.setFocusable(false);
        checkNotificationBtn.setFocusable(false);
        viewBalanceBtn.setFocusable(false);
        transactionHistoryBtn.setFocusable(false);
        readReviewBtn.setFocusable(false);
        logoutBtn.setFocusable(false);

        viewMenuBtn.addActionListener(this);
        historyBtn.addActionListener(this);
        checkNotificationBtn.addActionListener(this);
        viewBalanceBtn.addActionListener(this);
        transactionHistoryBtn.addActionListener(this);
        readReviewBtn.addActionListener(this);
        logoutBtn.addActionListener(this);


        // Add a window listener to handle window closing event
        customerDashboardPage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(viewMenuBtn);
        buttonPanel.add(historyBtn);
        buttonPanel.add(checkNotificationBtn);
        buttonPanel.add(viewBalanceBtn);
        buttonPanel.add(transactionHistoryBtn);
        buttonPanel.add(readReviewBtn);
        buttonPanel.add(logoutBtn);
        customerDashboardPage.add(buttonPanel, BorderLayout.SOUTH);

        
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == viewMenuBtn) {
                customerDashboardPage.setVisible(false);
                if (!allOrder.isEmpty()) {
                    mainClass.customerViewMenuPage.getviewMenuCustomerView().setVisible(true);
                } else {
                    mainClass.customerViewMenuPage.getviewMenuCustomer().setVisible(true);
                }
            } else if (e.getSource() == logoutBtn) {
                allOrder.clear();
                customerDashboardPage.setVisible(false);
                logOut();
            } else if (e.getSource() == historyBtn) {
                customerDashboardPage.setVisible(false);
                mainClass.orderHistoryPage.getOrderHistory().setVisible(true);
            } else if (e.getSource() == viewBalanceBtn) {
                for (User u: allUsers) {
                    if (u.getUid().equals(mainClass.loginUser.getUid())) {
                        JOptionPane.showMessageDialog(customerDashboardPage, "Balance: RM" + u.getBalance());
                    }
                }
            } else if (e.getSource() == transactionHistoryBtn) {
                customerDashboardPage.setVisible(false);
                mainClass.customerTransactionHistoryPage.getCustomerTransactionHistory().setVisible(true); 
            } else if (e.getSource() == checkNotificationBtn) {
                customerDashboardPage.setVisible(false);
                mainClass.customerNotificationPage.getCustomerNotification().setVisible(true);
            } else if (e.getSource() == readReviewBtn) {
                customerDashboardPage.setVisible(false);
                mainClass.readCustomerReviewPage.getViewCustomerReview().setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(customerDashboardPage, "Invalid input! Try Again!");
        }
    }

    public void displayUserGreetings(){
        // Initialize User Greetings and adding Label to NORTH POSITION
        userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        customerDashboardPage.add(userGreetingsLabel, BorderLayout.NORTH);
    }

    public void displayUserIcon(){
        // Initializing Image Icon
        ImageIcon customerImage = new ImageIcon("icons/customer.png");
        JLabel customerLabel = new JLabel(customerImage);

        // Add the image label to the CENTER position
        customerDashboardPage.add(customerLabel, BorderLayout.CENTER);
    }
}
