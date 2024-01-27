package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.FileIO.DataIO;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static UniversityFoodOrderingSystem.mainClass.vendorDashboardPage;

public class userLogin implements ActionListener {
    private JFrame userLoginPage;
    private JButton loginBtn, exitBtn;
    private static Boolean popUp = false;

    public JFrame getUserLoginPage() {
        return userLoginPage;
    }
    
    public static void setPopUp(Boolean value) {
        popUp = value;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == loginBtn) {
                String uid = JOptionPane.showInputDialog("Enter UID: ");
                if (uid == null) {
                    // User closed the JOptionPane or clicked cancel
                    return;
                }
                User found = User.checkUID(uid);
                if (found == null) {
                    JOptionPane.showMessageDialog(userLoginPage, "Invalid UID! Please try Again!"); //added this (11/12 during wpcs)
                    return;
                }
                String passwordStr = JOptionPane.showInputDialog("Password: ");
                if (passwordStr == null) {
                    // User closed the JOptionPane or clicked cancel
                    return;
                }
                int password = Integer.parseInt(passwordStr);
                User match = User.checkCredentialsMatch(uid, password);
                if (match == null) {
                    JOptionPane.showMessageDialog(userLoginPage, "Password does not match! Please try Again!"); //added this (11/12 during wpcs)
                    return;
                }
                User basicInfo = User.obtainUserInfo(uid, password);
                mainClass.loginUser = basicInfo;
                switch (match.getRole()) {
                    case CUSTOMER:
                        mainClass.orderViewCartPage.getCustomerID(uid);
                        mainClass.orderHistoryPage.getCustomerID(uid);
                        mainClass.customerNotificationPage.getCustomerID(uid);
                        userLoginPage.setVisible(false);
                        mainClass.customerDashboardPage = new customerDashboard();
                        if (popUp) {
                            mainClass.noRunnerPage.getNoRunnerPage().setVisible(true);
                        } else {
                            mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
                        }
                        setPopUp(false);
                        break;
                    case VENDOR:
                        mainClass.vendorManageOrderPage.getVendorID(uid);
                        mainClass.vendorOrderHistoryPage.getVendorID(uid);
                        mainClass.VendorNotificationPage.getVendorID(uid);
                        userLoginPage.setVisible(false);
                        mainClass.vendorDashboardPage = new vendorDashboard();
                        vendorDashboardPage.getVendorDashboard().setVisible(true);
                        break;
                    case RUNNER:
                        userLoginPage.setVisible(false);
                        mainClass.runnerDashboardPage = new runnerDashboard();
                        mainClass.runnerDashboardPage.getRunnerDashboard().setVisible(true);
                        break;
                    case ADMIN:
                        userLoginPage.setVisible(false);
                        mainClass.adminDashboardPage = new adminDashboard();
                        mainClass.adminDashboardPage.getAdminDashboard().setVisible(true);
                        break;
                }
            } else if (e.getSource() == exitBtn) {
                String input = JOptionPane.showInputDialog("Enter password:");
                if (input == null) {
                    // User closed the JOptionPane or clicked cancel
                    return;
                }
                if (!input.equals("12345")){
                    // when Exit Password is incorrect
                    throw new Exception();
                }
                DataIO.writeFile();
                System.exit(0);
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(userLoginPage, "Invalid input! Try Again!");
        }
    }

    public userLogin() {
        userLoginPage = new JFrame("User Login");
        userLoginPage.setSize(450, 100);
        userLoginPage.setLocationRelativeTo(null);

        loginBtn = new JButton("Login");
        exitBtn = new JButton("Exit");

        loginBtn.setFocusable(false);
        exitBtn.setFocusable(false);

        loginBtn.addActionListener(this);
        exitBtn.addActionListener(this);

        // Add a window listener to handle window closing event
        userLoginPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                DataIO.writeFile();
                System.exit(0);
            }
        });

        userLoginPage.setLayout(new FlowLayout());
        userLoginPage.add(loginBtn);
        userLoginPage.add(exitBtn);
        userLoginPage.setVisible(true);
    }
}