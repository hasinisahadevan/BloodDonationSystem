
package com.bloodbank;

public class Donor {

    private int donorId;
    private String donorName;
    private int age;
    private String gender;
    private String phone;
    private String bloodGroup;
    private String city;

    public Donor(int donorId, String donorName, int age,
                 String gender, String phone,
                 String bloodGroup, String city) {

        this.donorId = donorId;
        this.donorName = donorName;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.bloodGroup = bloodGroup;
        this.city = city;
    }

    public int getDonorId() {
        return donorId;
    }

    public String getDonorName() {
        return donorName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getCity() {
        return city;
    }
}
