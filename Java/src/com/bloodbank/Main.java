
package com.bloodbank;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        DonorService donorService = new DonorService();
        DonationService donationService = new DonationService();
        StockService stockService = new StockService();
        HospitalService hospitalService = new HospitalService();

        while (true) {

            System.out.println("\n==============================================");
            System.out.println("       BLOOD DONATION MANAGEMENT SYSTEM");
            System.out.println("==============================================");

            System.out.println("1.  Add Donor");
            System.out.println("2.  View All Donors");
            System.out.println("3.  Search Donor");
            System.out.println("4.  Update Donor");
            System.out.println("5.  Delete Donor");
            System.out.println("----------------------------------------------");
            System.out.println("6.  Record Donation");
            System.out.println("7.  View Donation History");
            System.out.println("----------------------------------------------");
            System.out.println("8.  View Blood Stock");
            System.out.println("----------------------------------------------");
            System.out.println("9.  Register Hospital Request");
            System.out.println("10. View Hospital Requests");
            System.out.println("11. Update Request Status");
            System.out.println("----------------------------------------------");
            System.out.println("0.  Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number!");
                continue;
            }

            switch (choice) {

                case 1:
                    System.out.println("\n--- ADD DONOR ---");

                    int id = readInt(sc, "Enter Donor ID: ");

                    System.out.print("Enter Donor Name: ");
                    String name = sc.nextLine();

                    int age = readInt(sc, "Enter Age: ");

                    System.out.print("Enter Gender: ");
                    String gender = sc.nextLine();

                    System.out.print("Enter Phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter Blood Group: ");
                    String bloodGroup = sc.nextLine().trim().toUpperCase();

                    System.out.print("Enter City: ");
                    String city = sc.nextLine();

                    Donor donor = new Donor(
                            id, name, age, gender,
                            phone, bloodGroup, city
                    );

                    donorService.addDonor(donor);
                    break;

                case 2:
                    donorService.viewAllDonors();
                    break;

                case 3:
                    int searchId = readInt(sc, "Enter Donor ID: ");
                    donorService.searchDonor(searchId);
                    break;

                case 4:
                    int updateId = readInt(sc, "Enter Donor ID: ");

                    System.out.print("Enter New Phone: ");
                    String newPhone = sc.nextLine();

                    System.out.print("Enter New City: ");
                    String newCity = sc.nextLine();

                    donorService.updateDonor(
                            updateId, newPhone, newCity
                    );
                    break;

                case 5:
                    int deleteId = readInt(sc, "Enter Donor ID: ");

                    System.out.print("Confirm deletion (yes/no): ");
                    String confirmation = sc.nextLine();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        donorService.deleteDonor(deleteId);
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                    break;

                case 6:
                    int donorId = readInt(sc, "Enter Donor ID: ");

                    System.out.print("Donation Date (YYYY-MM-DD): ");
                    String donationDate = sc.nextLine();

                    int units = readInt(sc, "Enter Demo Units: ");

                    donationService.recordDonation(
                            donorId, donationDate, units
                    );
                    break;

                case 7:
                    donationService.viewDonationHistory();
                    break;

                case 8:
                    stockService.viewBloodStock();
                    break;

                case 9:
                    System.out.print("Enter Hospital Name: ");
                    String hospitalName = sc.nextLine();

                    System.out.print("Enter Blood Group: ");
                    String requestGroup = sc.nextLine().trim().toUpperCase();

                    int requiredUnits = readInt(
                            sc, "Enter Required Demo Units: "
                    );

                    System.out.print("Request Date (YYYY-MM-DD): ");
                    String requestDate = sc.nextLine();

                    hospitalService.registerRequest(
                            hospitalName,
                            requestGroup,
                            requiredUnits,
                            requestDate
                    );
                    break;

                case 10:
                    hospitalService.viewRequests();
                    break;

                case 11:
                    int requestId = readInt(sc, "Enter Request ID: ");

                    System.out.print(
                            "Enter Status (PENDING/APPROVED/REJECTED): "
                    );

                    String status = sc.nextLine();

                    hospitalService.updateRequestStatus(
                            requestId, status
                    );
                    break;

                case 0:
                    System.out.println("Thank you for using the system!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // INTEGER INPUT VALIDATION
    private static int readInt(Scanner sc, String message) {

        while (true) {

            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number!");
            }
        }
    }
}
