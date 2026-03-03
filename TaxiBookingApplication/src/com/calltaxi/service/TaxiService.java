package com.calltaxi.service;

import com.calltaxi.model.Taxi;
import com.calltaxi.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class TaxiService {

    private List<Taxi> taxis = new ArrayList<>();
    private int bookingCounter = 1;

    public TaxiService(int taxiCount) {
        for (int i = 1; i <= taxiCount; i++) {
            taxis.add(new Taxi(i));
        }
    }

    public void bookTaxi(int customerId, char from, char to, int pickupTime) {

        Taxi allocatedTaxi = null;
        int minDistance = Integer.MAX_VALUE;

        for (Taxi taxi : taxis) {

            if (taxi.getFreeTime() <= pickupTime) {

                int distance = Math.abs(taxi.getCurrentLocation() - from);

                if (distance < minDistance) {
                    minDistance = distance;
                    allocatedTaxi = taxi;
                } 
                else if (distance == minDistance &&
                        allocatedTaxi != null &&
                        taxi.getTotalEarnings() < allocatedTaxi.getTotalEarnings()) {
                    allocatedTaxi = taxi;
                }
            }
        }

        if (allocatedTaxi == null) {
            System.out.println("No Taxi Available. Booking Rejected.");
            return;
        }

        int distance = Math.abs(from - to) * 15;
        int travelTime = Math.abs(from - to);
        int dropTime = pickupTime + travelTime;

        int amount = calculateFare(distance);

        Booking booking = new Booking(
                bookingCounter++,
                customerId,
                from,
                to,
                pickupTime,
                dropTime,
                amount
        );

        allocatedTaxi.addBooking(booking);
        allocatedTaxi.addEarnings(amount);
        allocatedTaxi.setCurrentLocation(to);
        allocatedTaxi.setFreeTime(dropTime);

        System.out.println("Taxi-" + allocatedTaxi.getTaxiId() + " is allotted.");
    }

    private int calculateFare(int distance) {
        if (distance <= 5)
            return 100;
        return 100 + (distance - 5) * 10;
    }

    public void displayTaxiDetails() {

        for (Taxi taxi : taxis) {

            if (taxi.getBookings().isEmpty())
                continue;

            System.out.println("\nTaxi-" + taxi.getTaxiId() +
                    " Total Earnings: Rs." + taxi.getTotalEarnings());

            System.out.println("BookingID CustomerID From To Pickup Drop Amount");

            for (Booking b : taxi.getBookings()) {
                System.out.println(
                        b.getBookingId() + "         " +
                        b.getCustomerId() + "         " +
                        b.getFrom() + "    " +
                        b.getTo() + "   " +
                        b.getPickupTime() + "      " +
                        b.getDropTime() + "    " +
                        b.getAmount()
                );
            }
        }
    }
}