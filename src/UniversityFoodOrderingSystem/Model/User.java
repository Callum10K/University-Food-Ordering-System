package UniversityFoodOrderingSystem.Model;

import UniversityFoodOrderingSystem.Enum.foodCuisineType;
import UniversityFoodOrderingSystem.Enum.userRoles;

import static UniversityFoodOrderingSystem.FileIO.DataIO.allUsers;

public class User {
    private String name;
    private String uid;
    private int password;
    private userRoles role;
    private foodCuisineType foodCuisine;
    private double balance;
    
    public User(String name, String uid, int password, userRoles role, foodCuisineType foodCuisine, double balance) {
        this.name = name;
        this.uid = uid;
        this.password = password;
        this.role = role;
        this.foodCuisine = foodCuisine;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public userRoles getRole() {
        return role;
    }

    public void setRole(userRoles role) {
        this.role = role;
    }

    public foodCuisineType getFoodCuisine() {
        return foodCuisine;
    }

    public void setFoodCuisine(foodCuisineType foodCuisine) {
        this.foodCuisine = foodCuisine;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public static User obtainUserInfoUsingUID(String uid) {
        for (User user : allUsers) {
            if (uid.equals(user.getUid())) {
                return new User(user.getName(), user.getUid(), user.getPassword(), user.getRole(), user.getFoodCuisine(), user.getBalance());
            }
        }
        return null; // Return null if no user with matching credentials is found
    }

    public static User checkUID(String UID){
        for(User u: allUsers) {
            if (UID.equals(u.getUid())) {
                return u;
            }
        }
        return null;
    }

    public static User checkCredentialsMatch(String uid, int password) {
        for (User user : allUsers) {
            if (uid.equals(user.getUid()) && password == user.getPassword()) {
                return user;
            }
        }
        return null; // Return null if no user with matching credentials is found
    }
    public static User obtainUserInfo(String uid, int password) {
        for (User user : allUsers) {
            if (uid.equals(user.getUid()) && password == user.getPassword()) {
                return new User(user.getName(), user.getUid(), user.getPassword(), user.getRole(), user.getFoodCuisine(), user.getBalance());
            }
        }
        return null; // Return null if no user with matching credentials is found
    }
}
