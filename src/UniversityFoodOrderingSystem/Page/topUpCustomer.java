package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.FileIO.DataIO;
import UniversityFoodOrderingSystem.Model.Admin;
import UniversityFoodOrderingSystem.Model.User;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static UniversityFoodOrderingSystem.Enum.userRoles.USER;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allNotification;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allReceipt;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allTransaction;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.Model.Notification;
import UniversityFoodOrderingSystem.Model.Receipt;
import UniversityFoodOrderingSystem.Model.Transaction;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class topUpCustomer implements ActionListener, ItemListener {
    private JFrame topUpCustomerPage;
    private JButton confirmBtn, cancelBtn;
    private JComboBox<String> AmountComboBox;
    private Label title;
    private static int Amount = 10;
    private static String userID;
    private static String notificationID;
    private static int notificationIDNum = 0;
    private static String receiptID;
    private static int receiptIDNum = 0;
    private static String transactionID;
    private static int transactionIDNum = 0;

    public JFrame getTopUpCustomer() {
        update();
        AmountComboBox.setSelectedIndex(0);
        return topUpCustomerPage;
    }
    
    public static void getUserID(String UID) {
        userID = UID;
    }

    private void update() {
        topUpCustomerPage = new JFrame("Top-up Balance");
        topUpCustomerPage.setSize(400, 300);
        topUpCustomerPage.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1)); // 1 column, as many rows as needed
        topUpCustomerPage.add(mainPanel);

        title = new Label("Topup Amount for " + userID + ":", Label.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
        mainPanel.add(title);
        
        JPanel comboBoxPanel = new JPanel(new FlowLayout());
        String[] viewAmount = {"RM10", "RM20", "RM50", "RM100"};
        AmountComboBox = new JComboBox<>(viewAmount);
        AmountComboBox.addItemListener(this);
        comboBoxPanel.add(AmountComboBox);
        
        confirmBtn = new JButton("Confirm");
        confirmBtn.addActionListener(this);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmBtn);
        buttonPanel.add(cancelBtn);
    
        mainPanel.add(comboBoxPanel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
 
        // Add a window listener to handle window closing event
        topUpCustomerPage.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                goBackToDashboard();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == confirmBtn) {
                for (User u: allUsers) {
                    if (u.getUid().equals(userID)) {
                        u.setBalance(u.getBalance() + Amount);
                        JOptionPane.showMessageDialog(topUpCustomerPage, " + RM" + Amount + " added to " + userID + "'s Balance" + "\n Current Balance: " + u.getBalance());
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();  
                        String formattedDateTime = now.format(dtf);
                        notificationIDNum += 1;
                        notificationID = "NID"+ notificationIDNum;
                        receiptIDNum += 1;
                        receiptID = "RID"+ receiptIDNum;
                        transactionIDNum += 1;
                        transactionID = "TID"+ transactionIDNum;
                        if (!allNotification.isEmpty()) { 
                            for (Notification n: allNotification) {
                                if (n.getNID().equals(notificationID)) {
                                    notificationIDNum = Integer.parseInt(n.getNID().substring(3)) + 1;
                                    notificationID = "NID"+ notificationIDNum;
                                }
                            }
                        }
                        if (!allReceipt.isEmpty()) { 
                            for (Receipt r: allReceipt) {
                                if (r.getReceiptID().equals(receiptID)) {
                                    receiptIDNum = Integer.parseInt(r.getReceiptID().substring(3)) + 1;
                                    receiptID = "RID"+ receiptIDNum;
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
                        String role = String.valueOf(u.getRole());
                        if (role.equals("CUSTOMER")) {
                            Customer.getNotification(notificationID, formattedDateTime, "[" + receiptID + "]" + " An amount of RM " +  Amount + " has been added to your balance.", u.getUid());
                            String AmountString = String.valueOf(Amount);
                            Customer.getReceipt(receiptID, AmountString, formattedDateTime, userID);
                            Customer.getTransaction(transactionID, formattedDateTime, "[" + receiptID + "]" + " Admin TopUp", "+RM" + Amount, u.getUid());
                        }
                        //Amount = 0;
                        break;
                    }
                }
            } else if (e.getSource() == cancelBtn) {
                goBackToDashboard();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(topUpCustomerPage, "Invalid input! Try Again!");
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            try {
                String selectedItem = (String) AmountComboBox.getSelectedItem();
                if (selectedItem.equals("RM10")) {
                    Amount = 10;
                } else if (selectedItem.equals("RM20")) {
                    Amount = 20;
                } else if (selectedItem.equals("RM50")) {
                    Amount = 50;
                } else if (selectedItem.equals("RM100")) {
                    Amount = 100;
                } 
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(topUpCustomerPage, "Error filtering menu items. Try Again!");
            }
        }
    }
    
    private void goBackToDashboard() {
        topUpCustomerPage.setVisible(false);
        mainClass.adminDashboardPage.getAdminDashboard().setVisible(true);
    }
}