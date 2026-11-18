
package com.bloodbank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockService {

    public void viewBloodStock() {

        String sql = "SELECT blood_group, available_units FROM blood_stock";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n--- Blood Stock Details ---");
            System.out.println("---------------------------");

            while (rs.next()) {

                String bloodGroup = rs.getString("blood_group");
                int units = rs.getInt("available_units");

                System.out.println(
                    "Blood Group: " + bloodGroup +
                    " | Available Units: " + units
                );
            }

        } catch (SQLException e) {
            System.out.println("Error viewing blood stock: " + e.getMessage());
        }
    }
}
