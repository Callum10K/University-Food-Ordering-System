package UniversityFoodOrderingSystem.Page;
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
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrder;
import UniversityFoodOrderingSystem.mainClass;


public class PlaceOrder implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == backButton) {
                PlaceOrder.setVisible(false);
                allOrder.clear();
                mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
            } 
        } catch (Exception x) {
            JOptionPane.showMessageDialog(PlaceOrder, "Invalid input! Try Again!");
        }
    }
    private JFrame PlaceOrder;
    private Label sentTitle;

    public JFrame getPlaceOrderPage() {
        update();
        return PlaceOrder;
    }

    JButton backButton = new JButton("Back");

    public void update() {
        PlaceOrder = new JFrame("Order Menu Page");
        PlaceOrder.setSize(500, 400);
        PlaceOrder.setLocation(700, 300);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1)); // 1 column, as many rows as needed
        PlaceOrder.add(mainPanel);
        
        mainPanel.removeAll();
        
        sentTitle = new Label("Your order has been sent to the Vendor.", Label.CENTER);
        sentTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(sentTitle);

        
        
        JPanel controlPanel = new JPanel(new FlowLayout());
        mainPanel.add(controlPanel);
        
        backButton.setFocusable(false);
        controlPanel.add(backButton);

        backButton.addActionListener(this);
        
        //orderModuleMainPage.revalidate();
        //orderModuleMainPage.repaint();
    }
}