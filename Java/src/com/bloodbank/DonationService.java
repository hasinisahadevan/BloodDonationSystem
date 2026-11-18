
package com.bloodbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class DonationService {

    public void recordDonation(int donorId, String donationDate, int units) {

        String checkSql = "SELECT donor_id FROM donor WHERE donor_id = ?";
        String insertSql = "INSERT INTO blood_donation (donor_id, donation_date, units_donated) VALUES (?, ?, ?)";
        String stockSql = "UPDATE blood_stock SET available_units = available_units + ? WHERE blood_group = (SELECT blood_group FROM donor WHERE donor_id = ?)";

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement check = con.prepareStatement(checkSql);
            check.setInt(1, donorId);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                System.out.println("Donor not found!");
                return;
            }

            con.setAutoCommit(false);

            try {
                PreparedStatement insert = con.prepareStatement(insertSql);
                insert.setInt(1, donorId);
                insert.setDate(2, Date.valueOf(donationDate));
                insert.setInt(3, units);
                insert.executeUpdate();

                PreparedStatement stock = con.prepareStatement(stockSql);
                stock.setInt(1, units);
                stock.setInt(2, donorId);
                stock.executeUpdate();

                con.commit();

                System.out.println("Donation recorded successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Donation failed: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void viewDonationHistory() {

        String sql = "SELECT bd.donation_id, d.donor_name, bd.donation_date, bd.units_donated " +
                     "FROM blood_donation bd JOIN donor d ON bd.donor_id = d.donor_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Donation History ---");

            while (rs.next()) {
                System.out.println(
                    "Donation ID: " + rs.getInt("donation_id") +
                    " | Donor: " + rs.getString("donor_name") +
                    " | Date: " + rs.getDate("donation_date") +
                    " | Units: " + rs.getInt("units_donated")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
