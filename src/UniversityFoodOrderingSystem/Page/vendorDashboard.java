package UniversityFoodOrderingSystem.Page;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;

import UniversityFoodOrderingSystem.Abstract.userDashboardCommonMethods;
import UniversityFoodOrderingSystem.Interface.userDashboard;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.Model.Vendor;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class vendorDashboard extends userDashboardCommonMethods implements userDashboard, ActionListener {
    private JFrame vendorDashboardPage;
    private JButton checkOrderHistoryBtn, manageFoodOrderBtn, manageMenuBtn, checkNotificationBtn, viewBalanceBtn, revenueDashboardBtn, readReviewBtn, logoutBtn;
    private Label userGreetingsLabel;
    private static Boolean notificationRead = true;

    private Double DailyEarning = 0.0;
    private Double MonthlyEarning = 0.0;
    private Double YearlyEarning = 0.0;

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == checkOrderHistoryBtn) {
                vendorDashboardPage.setVisible(false);
                mainClass.vendorOrderHistoryPage.getVendorOrderHistory().setVisible(true);
            } else if (e.getSource() == manageFoodOrderBtn) {
                vendorDashboardPage.setVisible(false);
                mainClass.vendorManageOrderPage.getVendorManageOrder().setVisible(true);
            } else if (e.getSource() == manageMenuBtn) {
                vendorDashboardPage.setVisible(false);
                mainClass.manageVendorMenuPage.getManageVendorMenu().setVisible(true);
            } else if (e.getSource() == checkNotificationBtn) {
                vendorDashboardPage.setVisible(false);
                mainClass.VendorNotificationPage.getVendorNotification().setVisible(true); 
            } else if (e.getSource() == viewBalanceBtn) {
                for (User u: allUsers) {
                    if (u.getUid().equals(mainClass.loginUser.getUid())) {
                        JOptionPane.showMessageDialog(vendorDashboardPage, "Balance: RM" + u.getBalance());
                    }
                }
            } else if (e.getSource() == revenueDashboardBtn) {
                //Double DailyEarning = 0.0;
                //Double MonthlyEarning = 0.0;
                //Double YearlyEarning = 0.0;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                for (OrderConfirm o: allOrderConfirm) {
                    if (o.getVendorUID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.toLocalDate().isEqual(now.toLocalDate())) {
                            DailyEarning += o.getPrice() * o.getQuantity();
                        }
                    }
                }
                for (OrderConfirm o: allOrderConfirm){
                    if (o.getVendorUID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.getMonth().equals(now.getMonth())) {
                            MonthlyEarning += o.getPrice() * o.getQuantity();
                    }
                    }
                }
                for (OrderConfirm o : allOrderConfirm) {
                    if (o.getVendorUID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = o.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.getYear() == now.getYear()) {
                            YearlyEarning += o.getPrice() * o.getQuantity();
                        }
                    }
                }
                
                JOptionPane.showMessageDialog(vendorDashboardPage, "Daily Earning: RM" + DailyEarning + "\n Monthly Earning: RM" + MonthlyEarning + "\n Yearly Earning: RM" + YearlyEarning);
            } else if (e.getSource() == readReviewBtn) {
                vendorDashboardPage.setVisible(false);
                mainClass.vendorViewOwnReviewPage.getViewCustomerReview().setVisible(true);
            } else if (e.getSource() == logoutBtn) {
                vendorDashboardPage.setVisible(false);
                logOut();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(vendorDashboardPage, "Invalid input! Try Again!");
        }
    }
    
    public static void getNotificationRead(Boolean nr) {
        notificationRead = nr;
    }

    public void displayUserGreetings(){
        // Initialize User Greetings and add Label to NORTH POSITION
        userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        vendorDashboardPage.add(userGreetingsLabel, BorderLayout.NORTH);
    }

    public void displayUserIcon(){
        // Initializing Image Icon
        ImageIcon vendorImage = new ImageIcon("icons/vendor.png");
        JLabel vendorLabel = new JLabel(vendorImage);

        // Add the image label to the CENTER position
        vendorDashboardPage.add(vendorLabel, BorderLayout.CENTER);
    }

    public JFrame getVendorDashboard() {
        update();
        displayUserGreetings();
        displayUserIcon();
        return vendorDashboardPage;
    }

    private void update() {
        vendorDashboardPage = new JFrame("Vendor Dashboard");
        vendorDashboardPage.setSize(1100, 250);
        vendorDashboardPage.setLocationRelativeTo(null);

        checkOrderHistoryBtn = new JButton("Check Order History");
        manageFoodOrderBtn = new JButton("Manage Food Order");
        manageMenuBtn = new JButton("Manage Vendor Menu");
        if (notificationRead) {
            checkNotificationBtn = new JButton("Check Notifications");
        } else {
            checkNotificationBtn = new JButton("Check Notifications*");
        }
        viewBalanceBtn = new JButton("View Balance");
        revenueDashboardBtn = new JButton("View Revenue");
        readReviewBtn = new JButton("View Reviews");
        logoutBtn = new JButton("Log out");

        checkOrderHistoryBtn.setFocusable(false);
        manageFoodOrderBtn.setFocusable(false);
        manageMenuBtn.setFocusable(false);
        checkNotificationBtn.setFocusable(false);
        viewBalanceBtn.setFocusable(false);
        revenueDashboardBtn.setFocusable(false);
        readReviewBtn.setFocusable(false);
        logoutBtn.setFocusable(false);

        logoutBtn.addActionListener(this);
        checkOrderHistoryBtn.addActionListener(this);
        manageFoodOrderBtn.addActionListener(this);
        checkNotificationBtn.addActionListener(this);
        manageMenuBtn.addActionListener(this);
        viewBalanceBtn.addActionListener(this);
        readReviewBtn.addActionListener(this);
        revenueDashboardBtn.addActionListener(this);

        vendorDashboardPage.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(checkOrderHistoryBtn);
        buttonPanel.add(manageFoodOrderBtn);
        buttonPanel.add(manageMenuBtn);
        buttonPanel.add(checkNotificationBtn);
        buttonPanel.add(viewBalanceBtn);
        buttonPanel.add(revenueDashboardBtn);
        buttonPanel.add(readReviewBtn);
        buttonPanel.add(logoutBtn);
        vendorDashboardPage.add(buttonPanel, BorderLayout.SOUTH);

        // Add a window listener to handle window closing event
        vendorDashboardPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                vendorDashboardPage.setVisible(false);
                logOut();
            }
        });
    }
}
