package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.userRoles;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allOrderConfirm;
import static UniversityFoodOrderingSystem.FileIO.DataIO.allReview;

public class Review {
    private String reviewerUID;
    private String revieweeUID;
    private int rating;
    private String feedback;
    private String OrderID;
    private userRoles roles;

    public Review (String reviewerUID, String revieweeUID, int rating, String feedback, String OrderID, userRoles roles){
        this.reviewerUID = reviewerUID;
        this.revieweeUID = revieweeUID;
        this.rating = rating;
        this.feedback = feedback;
        this.OrderID = OrderID;
        this.roles = roles;
    }

    public String getReviewerUID() {
        return reviewerUID;
    }

    public void setReviewerUID(String reviewerUID) {
        this.reviewerUID = reviewerUID;
    }

    public String getRevieweeUID() {
        return revieweeUID;
    }

    public void setRevieweeUID(String revieweeUID) {
        this.revieweeUID = revieweeUID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public userRoles getRoles() {
        return roles;
    }

    public void setRoles(userRoles roles) {
        this.roles = roles;
    }

    public static boolean checkVendorReviewExists(String OID, String VendorUID){
        for(Review r: allReview){
            if(r.getRevieweeUID().equals(VendorUID) && r.getOrderID().equals(OID)){
                return true;
            }
        }
        return false;
    }

    public static boolean checkRunnerReviewExists(String OID, String runnerReviewed, String runnerUID){
        for(OrderConfirm r: allOrderConfirm){
            if(r.getRunnerID().equals(runnerUID) && r.getOrderID().equals(OID) && runnerReviewed.equals("T")){
                return true;
            }
        }
        return false;
    }
}
