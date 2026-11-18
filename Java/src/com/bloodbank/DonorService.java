
package com.bloodbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DonorService {

    // 1. ADD DONOR
    public void addDonor(Donor donor) {

        String sql = "INSERT INTO donor " +
                "(donor_id, donor_name, age, gender, phone, blood_group, city) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donor.getDonorId());
            ps.setString(2, donor.getDonorName());
            ps.setInt(3, donor.getAge());
            ps.setString(4, donor.getGender());
            ps.setString(5, donor.getPhone());
            ps.setString(6, donor.getBloodGroup());
            ps.setString(7, donor.getCity());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Donor added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Add donor error: " + e.getMessage());
        }
    }

    // 2. VIEW ALL DONORS
    public void viewAllDonors() {

        String sql = "SELECT * FROM donor ORDER BY donor_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n========== DONOR DETAILS ==========");

            boolean found = false;

            while (rs.next()) {
                found = true;
                printDonor(rs);
            }

            if (!found) {
                System.out.println("No donors found!");
            }

        } catch (SQLException e) {
            System.out.println("View donor error: " + e.getMessage());
        }
    }

    // 3. SEARCH DONOR
    public void searchDonor(int donorId) {

        String sql = "SELECT * FROM donor WHERE donor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donorId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nDonor Found!");
                    printDonor(rs);
                } else {
                    System.out.println("Donor not found!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    // 4. UPDATE DONOR
    public void updateDonor(int donorId, String phone, String city) {

        String sql = "UPDATE donor SET phone = ?, city = ? WHERE donor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, city);
            ps.setInt(3, donorId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Donor updated successfully!");
            } else {
                System.out.println("Donor not found!");
            }

        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    // 5. DELETE DONOR
    public void deleteDonor(int donorId) {

        String sql = "DELETE FROM donor WHERE donor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, donorId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Donor deleted successfully!");
            } else {
                System.out.println("Donor ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting donor: " + e.getMessage());
        }
    }

    // 6. DISPLAY DONOR DETAILS
    private void printDonor(ResultSet rs) throws SQLException {

        System.out.println(
                "Donor ID: " + rs.getInt("donor_id") +
                " | Name: " + rs.getString("donor_name") +
                " | Age: " + rs.getInt("age") +
                " | Gender: " + rs.getString("gender") +
                " | Phone: " + rs.getString("phone") +
                " | Blood Group: " + rs.getString("blood_group") +
                " | City: " + rs.getString("city")
        );
    }
}
