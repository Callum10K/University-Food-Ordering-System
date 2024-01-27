package UniversityFoodOrderingSystem.Abstract;

import UniversityFoodOrderingSystem.mainClass;

public abstract class userDashboardCommonMethods {
    public void logOut(){
        mainClass.loginModule.getUserLoginPage().setVisible(true);
    }

}


