package UniversityFoodOrderingSystem.Page;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allDeliveries;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;

import UniversityFoodOrderingSystem.Abstract.userDashboardCommonMethods;
import UniversityFoodOrderingSystem.Interface.userDashboard;
import UniversityFoodOrderingSystem.Model.Delivery;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class runnerDashboard extends userDashboardCommonMethods implements userDashboard, ActionListener {
    private JFrame runnerDashboardPage;
    private JButton viewTaskBtn, viewNotificationsBtn, viewHistoryBtn, viewBalanceBtn, revenueDashboardBtn, readReviewBtn, logoutBtn;
    private Label userGreetingsLabel;
    private static Boolean notificationRead = true;

    public JFrame getRunnerDashboard() {
        update();
        displayUserGreetings();
        displayUserIcon();
        return runnerDashboardPage;
    }
    
    public static void getNotificationRead(Boolean nr) {
        notificationRead = nr;
    }

    private void update() {
        runnerDashboardPage = new JFrame("Runner Dashboard");
        runnerDashboardPage.setSize(1100, 250);
        runnerDashboardPage.setLocationRelativeTo(null);

        viewTaskBtn = new JButton("View Task");
        if (notificationRead) {
            viewNotificationsBtn = new JButton("Check Notifications");
        } else {
            viewNotificationsBtn = new JButton("Check Notifications*");
        }
        viewHistoryBtn = new JButton("View Task History");
        viewBalanceBtn = new JButton("View Balance");
        revenueDashboardBtn = new JButton("View Revenue");
        readReviewBtn = new JButton("View Reviews");
        logoutBtn = new JButton("Log out");

        viewTaskBtn.setFocusable(false);
        viewNotificationsBtn.setFocusable(false);
        viewHistoryBtn.setFocusable(false);
        viewBalanceBtn.setFocusable(false);
        revenueDashboardBtn.setFocusable(false);
        readReviewBtn.setFocusable(false);
        logoutBtn.setFocusable(false);

        logoutBtn.addActionListener(this);
        viewTaskBtn.addActionListener(this);
        viewNotificationsBtn.addActionListener(this);
        viewBalanceBtn.addActionListener(this);
        viewHistoryBtn.addActionListener(this);
        readReviewBtn.addActionListener(this);
        revenueDashboardBtn.addActionListener(this);

        runnerDashboardPage.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(viewTaskBtn);
        buttonPanel.add(viewNotificationsBtn);
        buttonPanel.add(viewHistoryBtn);
        buttonPanel.add(viewBalanceBtn);
        buttonPanel.add(revenueDashboardBtn);
        buttonPanel.add(readReviewBtn);
        buttonPanel.add(logoutBtn);
        runnerDashboardPage.add(buttonPanel, BorderLayout.SOUTH);

        // Add a window listener to handle window closing event
        runnerDashboardPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                logOut();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == viewTaskBtn) {
                runnerDashboardPage.setVisible(false);
                mainClass.runnerViewTaskPage.getRunnerViewTask().setVisible(true);
                
            } else if (e.getSource() == viewNotificationsBtn) {
                runnerDashboardPage.setVisible(false);
                mainClass.runnerNotificationPage.getRunnerNotification().setVisible(true);
            } else if (e.getSource() == viewHistoryBtn) {
                runnerDashboardPage.setVisible(false);
                mainClass.runnerTaskHistoryPage.getRunnerTaskHistory().setVisible(true);
            } else if (e.getSource() == viewBalanceBtn) {
                for (User u: allUsers) {
                    if (u.getUid().equals(mainClass.loginUser.getUid())) {
                        JOptionPane.showMessageDialog(runnerDashboardPage, "Balance: RM" + u.getBalance());
                    }
                }
            } else if (e.getSource() == revenueDashboardBtn) {
                Double DailyEarning = 0.0;
                Double MonthlyEarning = 0.0;
                Double YearlyEarning = 0.0;
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                for (Delivery d: allDeliveries) {
                    if (d.getRunnerID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = d.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.toLocalDate().isEqual(now.toLocalDate()) && d.getDeliveryStatus().equals("Delivered")) {
                            DailyEarning += 7;
                        }
                    }
                }
                for (Delivery d: allDeliveries) {
                    if (d.getRunnerID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = d.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.getMonth().equals(now.getMonth()) && d.getDeliveryStatus().equals("Delivered")) {
                            MonthlyEarning += 7;
                        }
                    }
                }
                for (Delivery d: allDeliveries) {
                    if (d.getRunnerID().equals(mainClass.loginUser.getUid())) {
                        String orderTimeString = d.getTime();
                        LocalDateTime orderDateTime = LocalDateTime.parse(orderTimeString, dtf);
                        if (orderDateTime.getYear() == now.getYear() && d.getDeliveryStatus().equals("Delivered")) {
                            YearlyEarning += 7;
                        }
                    }
                }                
                JOptionPane.showMessageDialog(runnerDashboardPage, "Daily Earning: RM" + DailyEarning + "\n Monthly Earning: RM" + MonthlyEarning + "\n Yearly Earning: RM" + YearlyEarning);
            } else if (e.getSource() == readReviewBtn) {
                runnerDashboardPage.setVisible(false);
                mainClass.runnerViewOwnReviewPage.getRunnerViewOwnReview().setVisible(true);
            } else if (e.getSource() == logoutBtn) {
                runnerDashboardPage.setVisible(false);
                logOut();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(runnerDashboardPage, "Invalid input! Try Again!");
        }
    }

    public void displayUserGreetings(){
        // Initialize User Greetings and adding Label to NORTH POSITION
        userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        runnerDashboardPage.add(userGreetingsLabel, BorderLayout.NORTH);
    }

    public void displayUserIcon(){
        // Initializing Image Icon
        ImageIcon runnerImage = new ImageIcon("icons/runner.png");
        JLabel runnerLabel = new JLabel(runnerImage);

        // Add the image label to the CENTER position
        runnerDashboardPage.add(runnerLabel, BorderLayout.CENTER);
    }
}
