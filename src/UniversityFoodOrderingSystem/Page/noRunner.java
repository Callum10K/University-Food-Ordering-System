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
import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import UniversityFoodOrderingSystem.Model.OrderConfirm;
import UniversityFoodOrderingSystem.mainClass;


public class noRunner implements ActionListener{
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == dineInBtn) {
                for (OrderConfirm o: allOrderConfirm) {
                    o.setOption("Dine In");
                    noRunner.setVisible(false);
                    mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
                    break;
                }
            } else if (e.getSource() == TakeAwayBtn) {
                for (OrderConfirm o: allOrderConfirm) {
                    o.setOption("TakeAway");
                    noRunner.setVisible(false);
                    mainClass.customerDashboardPage.getCustomerDashboard().setVisible(true);
                    break;
                }
            } 
        } catch (Exception x) {
            JOptionPane.showMessageDialog(noRunner, "Invalid input! Try Again!");
        }
    }
    private JFrame noRunner;
    private Label title;
    private Label title2;
    private static String orderID;

    public JFrame getNoRunnerPage() {
        update();
        return noRunner;
    }
    
    public static void getOID(String OID) {
        orderID = OID;
    }

    JButton dineInBtn = new JButton("Dine In");
    JButton TakeAwayBtn = new JButton("Take Away");

    public void update() {
        noRunner = new JFrame("No Runner Available");
        noRunner.setSize(500, 400);
        noRunner.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1)); // 1 column, as many rows as needed
        noRunner.add(mainPanel);
        
        mainPanel.removeAll();
        
        title = new Label("There are currently no available runners for " + orderID + ".", Label.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(title);
        
        title2 = new Label("Change option to Dine In or Take Away.", Label.CENTER);
        title2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        mainPanel.add(title2);

        JPanel controlPanel = new JPanel(new FlowLayout());
        mainPanel.add(controlPanel);
        
        dineInBtn.setFocusable(false);
        controlPanel.add(dineInBtn);
        
        TakeAwayBtn.setFocusable(false);
        controlPanel.add(TakeAwayBtn);

        dineInBtn.addActionListener(this);
        TakeAwayBtn.addActionListener(this);
        
        //orderModuleMainPage.revalidate();
        //orderModuleMainPage.repaint();
    }
}