package com.example.infs3605_group2.Models;

public class User {
    private String password;
    private String username;
    private String userType;
    private int profileImage;
    private double balance;
    private String linkedAccount;
    private String savingsGoalPic;
    private String savingsGoal;
    private String savingsName;
    public User() {
    }

    public String getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(String linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSavingsGoalPic() {
        return savingsGoalPic;
    }

    public void setSavingsGoalPic(String savingsGoalPic) {
        this.savingsGoalPic = savingsGoalPic;
    }

    public String getSavingsGoal() {
        return savingsGoal;
    }

    public void setSavingsGoal(String savingsGoal) {
        this.savingsGoal = savingsGoal;
    }

    public String getSavingsName() {
        return savingsName;
    }

    public void setSavingsName(String savingsName) {
        this.savingsName = savingsName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
