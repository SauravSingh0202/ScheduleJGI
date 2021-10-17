package com.example.schedulejgi;

class UserHelper {
    String name,email,mobile,password,address,userType;

    public UserHelper() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public UserHelper(String name, String email, String mobile, String password, String address, String userType) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.address = address;
        this.userType = userType;

    }
}
