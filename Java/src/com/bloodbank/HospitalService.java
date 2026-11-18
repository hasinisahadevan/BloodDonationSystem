
package com.bloodbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class HospitalService {

    public void registerRequest(String hospitalName, String bloodGroup,
                                int requiredUnits, String requestDate) {

        String sql = "INSERT INTO hospital_request " +
                     "(hospital_name, blood_group, required_units, request_date) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hospitalName);
            ps.setString(2, bloodGroup);
            ps.setInt(3, requiredUnits);
            ps.setDate(4, Date.valueOf(requestDate));

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Hospital request registered successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error registering request: " + e.getMessage());
        }
    }

    public void viewRequests() {

        String sql = "SELECT * FROM hospital_request";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Hospital Requests ---");

            while (rs.next()) {
                System.out.println(
                    "Request ID: " + rs.getInt("request_id") +
                    " | Hospital: " + rs.getString("hospital_name") +
                    " | Blood Group: " + rs.getString("blood_group") +
                    " | Required Units: " + rs.getInt("required_units") +
                    " | Date: " + rs.getDate("request_date") +
                    " | Status: " + rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error viewing requests: " + e.getMessage());
        }
    }

    public void updateRequestStatus(int requestId, String status) {

        if (!status.equalsIgnoreCase("PENDING") &&
            !status.equalsIgnoreCase("APPROVED") &&
            !status.equalsIgnoreCase("REJECTED")) {

            System.out.println("Invalid status! Use PENDING, APPROVED or REJECTED.");
            return;
        }

        String sql = "UPDATE hospital_request SET status = ? WHERE request_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status.toUpperCase());
            ps.setInt(2, requestId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Request status updated successfully!");
            } else {
                System.out.println("Request ID not found!");
            }

        } catch (SQLException e) {
            System.out.println("Error updating request: " + e.getMessage());
        }
    }
}
