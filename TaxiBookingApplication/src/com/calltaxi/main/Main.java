package com.calltaxi.main;

import com.calltaxi.service.TaxiService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        TaxiService taxiService = new TaxiService(4);
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n1. Book Taxi");
            System.out.println("2. Display Taxi Details");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Customer ID: ");
                    int customerId = sc.nextInt();

                    System.out.print("Pickup Point (A-F): ");
                    char from = sc.next().toUpperCase().charAt(0);

                    System.out.print("Drop Point (A-F): ");
                    char to = sc.next().toUpperCase().charAt(0);

                    System.out.print("Pickup Time: ");
                    int pickupTime = sc.nextInt();

                    taxiService.bookTaxi(customerId, from, to, pickupTime);
                    break;

                case 2:
                    taxiService.displayTaxiDetails();
                    break;

                case 3:
                    System.out.println("Program Ended.");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}