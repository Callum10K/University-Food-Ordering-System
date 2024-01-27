package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Abstract.userDashboardCommonMethods;
import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.Interface.userDashboard;
import UniversityFoodOrderingSystem.Model.Admin;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class adminDashboard extends userDashboardCommonMethods implements userDashboard, ActionListener {
    private JFrame adminDashboardPage;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton userRegistrationBtn, modifyBtn, deleteBtn, topUpBtn, logoutBtn;
    private Label userGreetingsLabel;

    public JFrame getAdminDashboard() {
        // Initialize User Greetings and adding Label to NORTH POSITION
        //userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        //userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        // Initializing Image Icon
        //ImageIcon adminImage = new ImageIcon("icons/admin.png");
        //JLabel adminLabel = new JLabel(adminImage);

        // Create a panel for the user greetings label and admin image with BorderLayout
        //JPanel greetingsPanel = new JPanel(new BorderLayout());
        //greetingsPanel.add(userGreetingsLabel, BorderLayout.EAST);
        //greetingsPanel.add(adminLabel, BorderLayout.WEST);
        displayUserIcon();

        // Add the panel to the CENTER position
        //adminDashboardPage.add(greetingsPanel, BorderLayout.NORTH);

        Admin.displayUserInformationInTable(tableModel);
        return adminDashboardPage;
    }

    public adminDashboard() {
        adminDashboardPage = new JFrame("Admin Dashboard");
        adminDashboardPage.setSize(700, 400);
        adminDashboardPage.setLocationRelativeTo(null);

        userRegistrationBtn = new JButton("User Registration");
        modifyBtn = new JButton("Modify User Credentials");
        deleteBtn = new JButton("Delete User");
        topUpBtn = new JButton("Top Up Balance");
        logoutBtn = new JButton("Logout");

        userRegistrationBtn.setFocusable(false);
        topUpBtn.setFocusable(false);
        logoutBtn.setFocusable(false);
        modifyBtn.setFocusable(false);
        deleteBtn.setFocusable(false);

        logoutBtn.addActionListener(this);
        userRegistrationBtn.addActionListener(this);
        topUpBtn.addActionListener(this);
        modifyBtn.addActionListener(this);
        deleteBtn.addActionListener(this);

        tableModel = new DefaultTableModel(new Object[]{"Username", "UID", "Password", "Role", "Cuisine Type"}, 0);
        userTable = new JTable(tableModel);

        adminDashboardPage.setLayout(new BorderLayout());

        // Add a window listener to handle window closing event
        adminDashboardPage.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut();
            }
        });

        // Create a panel for the buttons with FlowLayout
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1));
        buttonPanel.add(userRegistrationBtn);
        buttonPanel.add(modifyBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(topUpBtn);
        buttonPanel.add(logoutBtn);

        adminDashboardPage.add(buttonPanel, BorderLayout.EAST);  // Adjusted alignment

        // Create a panel for the table with BorderLayout
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        adminDashboardPage.add(tablePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == userRegistrationBtn) {
                adminDashboardPage.setVisible(false);
                mainClass.userRegistrationPage.getUserRegistrationPage().setVisible(true);
            } else if (e.getSource() == modifyBtn) {
                // Implement logic to modify the selected user account
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the UID of the selected user
                    mainClass.uid = (String) userTable.getValueAt(selectedRow, 1);
                    // Perform modifications based on the selected UID
                    modifyUser modifyUserPage = new modifyUser();
                    adminDashboardPage.setVisible(false);
                    modifyUserPage.getModifyUser().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(adminDashboardPage, "Please select a user to modify.");
                }
            } else if (e.getSource() == deleteBtn) {
                // Implement logic to delete the selected user account
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the UID of the selected user
                    String selectedUID = (String) userTable.getValueAt(selectedRow, 1);

                    // Confirm deletion with a dialog
                    int dialogResult = JOptionPane.showConfirmDialog(adminDashboardPage,
                            "Are you sure you want to delete the selected user?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION);
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        // Delete the user
                        Admin.deleteUser(selectedUID);
                        Admin.displayUserInformationInTable(tableModel);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(adminDashboardPage, "Please select a user to delete.");
                }
            } else if (e.getSource() == topUpBtn) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the UID of the selected user
                    String UID = (String) userTable.getValueAt(selectedRow, 1);
                    userRoles role = (userRoles) userTable.getValueAt(selectedRow, 3);
                    if (role.equals(userRoles.CUSTOMER)) {
                        adminDashboardPage.setVisible(false);
                        mainClass.topUpCustomerPage.getUserID(UID);
                        mainClass.topUpCustomerPage.getTopUpCustomer().setVisible(true);
                    } else{
                        JOptionPane.showMessageDialog(adminDashboardPage, "Only Customers are allowed to be topped up.");
                    }
                } else {
                    JOptionPane.showMessageDialog(adminDashboardPage, "Please select a user to top up their balance.");
                }
            } else if (e.getSource() == logoutBtn) {
                adminDashboardPage.setVisible(false);
                logOut();
            }
        } catch (Exception x) {
            JOptionPane.showMessageDialog(adminDashboardPage, "Invalid input! Try Again!");
        }
    }

    public void displayUserGreetings(){
        // Initialize User Greetings and adding Label to NORTH POSITION
        userGreetingsLabel = new Label("Hello " + mainClass.loginUser.getName() + " (" + mainClass.loginUser.getUid() + ")!", Label.CENTER);
        userGreetingsLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

    }

    public void displayUserIcon(){
        // Initialize User Greetings and adding Label to NORTH POSITION
        displayUserGreetings();
        // Initializing Image Icon
        ImageIcon adminImage = new ImageIcon("icons/admin.png");
        JLabel adminLabel = new JLabel(adminImage);
        // Create a panel for the user greetings label and admin image with BorderLayout
        JPanel greetingsPanel = new JPanel(new BorderLayout());
        greetingsPanel.add(userGreetingsLabel, BorderLayout.EAST);
        greetingsPanel.add(adminLabel, BorderLayout.WEST);
        // Add the panel to the CENTER position
        adminDashboardPage.add(greetingsPanel, BorderLayout.NORTH);

    }
}
