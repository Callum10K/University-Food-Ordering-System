package UniversityFoodOrderingSystem.Page;

import UniversityFoodOrderingSystem.Enum.userRoles;
import UniversityFoodOrderingSystem.Model.Customer;
import UniversityFoodOrderingSystem.mainClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class customerReviewVendor implements ActionListener {
    private JFrame giveReviewCustomer;
    private JButton backBtn, submitBtn;

    private JRadioButton oneStarBtn, twoStarsBtn, threeStarsBtn, fourStarsBtn, fiveStarsBtn;
    private ButtonGroup ratingBtnGroup;
    private JTextArea feedbackField;
    private int Rating;
    private String OrderID, revieweeUID;

    public JFrame getGiveReviewCustomerForVendor(String OID, String vendorUID) {
        OrderID = OID;
        revieweeUID = vendorUID;
        return giveReviewCustomer;
    }


    public customerReviewVendor() { //settings for Jframe
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        giveReviewCustomer = new JFrame("Give Vendor Review");
        giveReviewCustomer.setSize(500, 300);
        giveReviewCustomer.setLocationRelativeTo(null);
        giveReviewCustomer.setLayout(layout);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(backBtn);
        buttonPanel.add(submitBtn);
        gbc.gridx = 0;
        gbc.gridy = 4;
        giveReviewCustomer.add(buttonPanel,gbc);


        //review title panel
        JLabel titleLabel = new JLabel("Rate Your Experience");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));


        JPanel titlePanel = new JPanel();
        titlePanel.add(titleLabel);

        //grid layout for title
        gbc.gridx = 0;
        gbc.gridy = 0;
        giveReviewCustomer.add(titlePanel, gbc);

        //review vendor title panel
        JLabel vendorReviewLabel = new JLabel("Rate Your Vendor");
        vendorReviewLabel.setFont(new Font("Arial", Font.BOLD, 14));


        JPanel vendorReviewPanel = new JPanel();
        vendorReviewPanel.add(vendorReviewLabel);

        //grid layout for title
        gbc.gridx = 0;
        gbc.gridy = 1;
        giveReviewCustomer.add(vendorReviewPanel,gbc);


        //rating radio buttons
        oneStarBtn = new JRadioButton("1");
        twoStarsBtn = new JRadioButton("2");
        threeStarsBtn = new JRadioButton("3");
        fourStarsBtn = new JRadioButton("4");
        fiveStarsBtn = new JRadioButton("5");

        oneStarBtn.addActionListener(this);
        twoStarsBtn.addActionListener(this);
        threeStarsBtn.addActionListener(this);
        fourStarsBtn.addActionListener(this);
        fiveStarsBtn.addActionListener(this);

        ratingBtnGroup = new ButtonGroup();
        ratingBtnGroup.add(oneStarBtn);
        ratingBtnGroup.add(twoStarsBtn);
        ratingBtnGroup.add(threeStarsBtn);
        ratingBtnGroup.add(fourStarsBtn);
        ratingBtnGroup.add(fiveStarsBtn);

        //review rating panel
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));

        ratingPanel.add(oneStarBtn);
        ratingPanel.add(twoStarsBtn);
        ratingPanel.add(threeStarsBtn);
        ratingPanel.add(fourStarsBtn);
        ratingPanel.add(fiveStarsBtn);

        //setting GridBagLayout for button panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        giveReviewCustomer.add(ratingPanel,gbc);


        //input box for feedback
        feedbackField = new JTextArea(5,20);
        feedbackField.setLineWrap(true);
        feedbackField.setWrapStyleWord(true);
        feedbackField.setPreferredSize(new Dimension(250,150));
        feedbackField.setEditable(true);

        JScrollPane scrollPane = new JScrollPane(feedbackField);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.add(scrollPane);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        giveReviewCustomer.add(feedbackPanel,gbc);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            giveReviewCustomer.setVisible(false);
            mainClass.orderHistoryPage.getOrderHistory().setVisible(true);
        }

        if (e.getSource() == submitBtn){
            int boolRadio = 0;
            int boolFeedback = 0;
            String feedback = feedbackField.getText();
            // Error handling for user not inputting any feedback
            if (feedback.isEmpty()) {
                JOptionPane.showOptionDialog(
                        giveReviewCustomer,
                        "Please provide feedback before submitting the review.",
                        "Feedback Missing",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, // Use the default icon
                        new Object[]{"OK"}, // Options for the user
                        "OK");}
            else {
                boolFeedback = 1;
            }

            if (oneStarBtn.isSelected()){
                Rating = 1;
                boolRadio = 1;
            }
            else if (twoStarsBtn.isSelected()){
                Rating = 2;
                boolRadio = 1;
            }
            else if (threeStarsBtn.isSelected()){
                Rating = 3;
                boolRadio = 1;
            }
            else if (fourStarsBtn.isSelected()){
                Rating = 4;
                boolRadio = 1;
            }
            else if (fiveStarsBtn.isSelected()){
                Rating = 5;
                boolRadio = 1;
            }
            else {
                JOptionPane.showOptionDialog(
                        giveReviewCustomer,
                        "Please select a rating before submitting the review.",
                        "Rating Missing",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null, // Use the default icon
                        new Object[]{"OK"}, // Options for the user
                        "OK");
            }

            if (boolRadio == 1 && boolFeedback == 1) {
                // add vendor review into review arraylist
                Customer.addNewReview(mainClass.loginUser.getUid(), revieweeUID, Rating, feedback, OrderID, userRoles.VENDOR);
                // update review vendor status of Order ID
                Customer.modifyReviewVendorStatus(OrderID, revieweeUID, "T");
                JOptionPane.showOptionDialog(
                        giveReviewCustomer,
                        "Your review has been submitted.",
                        "Review Submitted",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, // Use the default icon
                        new Object[]{"OK"}, // Options for the user
                        "OK");
                // reset the fields after leaving review
                ratingBtnGroup.clearSelection();
                feedbackField.setText("");
                giveReviewCustomer.setVisible(false);
                mainClass.orderHistoryPage.getOrderHistory().setVisible(true);
            }
        }
    }
}
// notes
// back button has to be configured where customer are allowed to go back to the previous page
// if possible add image on top of the ratings

