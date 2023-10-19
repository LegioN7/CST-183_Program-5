package com.example.cst_183_program5;

public class carRental {
    // Initial Object Variables
    private String customerClassificationCode;
    private int carRentalDays;
    private int carOdometerStart;
    private int carOdometerEnd;


    // Pricing Constants

    // Budget
    // Customer Classification Code = "B"
    final double BUDGET_DAILY_RATE = 40.00;
    final double BUDGET_MILEAGE_RATE = 0.25;

    // Daily
    // Customer Classification Code = "D"
    final double DAILY_DAILY_RATE = 60.00;
    final double DAILY_MILEAGE_RATE = 0.25;
    final int DAILY_MILEAGE_LIMIT = 100;

    // Weekly
    // Customer Classification Code = "W"
    final double WEEKLY_BASE_RATE = 190.00;
    final double WEEKLY_MILEAGE_RATE = 0.25;

    // Because there is a variance in mileage charges for weekly customers
    // We need to break the rate up into 3 variables
    final double WEEKLY_MILEAGE_RATE_LOW = 0;
    final double WEEKLY_MILEAGE_RATE_MEDIUM = 100;
    final double WEEKLY_MILEAGE_RATE_HIGH = 200;
    final int WEEKLY_MILEAGE_LIMIT_LOW = 900;
    final int WEEKLY_MILEAGE_LIMIT_HIGH = 1500;

    public carRental() {
        customerClassificationCode = "";
        carRentalDays = 0;
        carOdometerStart = 0;
        carOdometerEnd = 0;
    }


    // Setting and Getting the Customer classification code
    // B, D or W only
    public void setCustomerClassification(String customerClassification) {
        customerClassificationCode = customerClassification;
    }

    public String getCustomerClassification() {
        return customerClassificationCode;
    }

    // Setting and Getting the Car Rental Days
    // this should be a whole number
    public void setCarRentalDays(int rentalDays) {
        carRentalDays = rentalDays;
    }

    public int getCarRentalDays() {
        return carRentalDays;
    }

    // Setting the Odometer Start
    public void setCarOdometerStart(int odometerStart) {
        carOdometerStart = odometerStart;
    }

    public int getCarOdometerStart() {
        return carOdometerStart;
    }

    // Setting the Odometer End
    public void setCarOdometerEnd(int odometerEnd) {
        carOdometerEnd = odometerEnd;
    }

    public int getCarOdometerEnd() {
        return carOdometerEnd;
    }

    // When you submit feedback, is it better to inline for readability or leave it separated?
    // IntelliJ mentions inlining these calculations
    public int calculateMileage(int carOdometerStart, int carOdometerEnd) {
        int mileage = carOdometerEnd - carOdometerStart;
        return mileage;
    }


    public double calculateRentalCost(String customerClassificationCode, int carRentalDays, int mileage) {
        // I'm leaving this Print Statement to ensure that it works correct
        System.out.println("Customer Code: " + customerClassificationCode);
        System.out.println("Rental Days: " + carRentalDays);
        System.out.println("Mileage: " + mileage);

        double rentalCost = 0;
        int mileageDifference;

        // For Weekly Customers
        int totalWeeks = (int) Math.ceil((double) carRentalDays / 7);
        int averageMilesPerWeek = mileage / totalWeeks;

        if (customerClassificationCode.equals("B") || customerClassificationCode.equals("b")) {
            // Budget Calculation Cost
            rentalCost = BUDGET_DAILY_RATE * carRentalDays + BUDGET_MILEAGE_RATE * mileage;
        } else if (customerClassificationCode.equals("D") || customerClassificationCode.equals("d")) {
            // Daily Calculation Cost
            if (mileage <= DAILY_MILEAGE_LIMIT) {
                rentalCost = DAILY_DAILY_RATE * carRentalDays;
            } else {
                mileageDifference = mileage - DAILY_MILEAGE_LIMIT;
                rentalCost = DAILY_DAILY_RATE * carRentalDays + DAILY_MILEAGE_RATE * mileageDifference;
            }
        } else if (customerClassificationCode.equals("W") || customerClassificationCode.equals("w")) {
            // Weekly Calculation Cost
            if (averageMilesPerWeek <= WEEKLY_MILEAGE_LIMIT_LOW) {
                rentalCost = WEEKLY_BASE_RATE * totalWeeks;
            } else if (averageMilesPerWeek < 1500) {
                rentalCost = WEEKLY_BASE_RATE * totalWeeks + WEEKLY_MILEAGE_RATE_MEDIUM * totalWeeks;
            } else if (averageMilesPerWeek > 1500) {
                mileageDifference = averageMilesPerWeek - WEEKLY_MILEAGE_LIMIT_HIGH;
                rentalCost = WEEKLY_BASE_RATE * totalWeeks + WEEKLY_MILEAGE_RATE_HIGH * totalWeeks + (WEEKLY_MILEAGE_RATE * mileageDifference * totalWeeks);
            }
        }
        return rentalCost;
    }

    // This will output the String information needed for the Rental info
    // I used some references from Reddit because I wanted to know how to append the String data easier.
    // I have trouble reading it when I am appending data with "\n" (similar to my hogshead ale calculation)
    // IntelliJ even mentions using just a String.

    public String rentalInfo(int mileage, int rentalDays) {
        double baseRentalCharge = 0;
        double mileageCharge;
        double totalRentalCharge = 0;

        int totalWeeks = (int) Math.ceil((double) carRentalDays / 7);

        if (customerClassificationCode.equals("b") || customerClassificationCode.equals("B")) {
            baseRentalCharge = BUDGET_DAILY_RATE * carRentalDays;
        } else if (customerClassificationCode.equals("d") || customerClassificationCode.equals("D")) {
            baseRentalCharge = DAILY_DAILY_RATE * carRentalDays;
        } else if (customerClassificationCode.equals("w") || customerClassificationCode.equals("W")) {
            baseRentalCharge = WEEKLY_BASE_RATE * totalWeeks;
        }

        // all mileage charges are the same at 0.25
        mileageCharge = mileage * WEEKLY_MILEAGE_RATE;

        totalRentalCharge = baseRentalCharge + totalRentalCharge;

        // Testing StringBuilder (I've only done this once)
        StringBuilder rentalInfo = new StringBuilder();
        rentalInfo.append("Miles driven: ").append(mileage).append("\n");
        rentalInfo.append("Days rented: ").append(rentalDays).append("\n");
        rentalInfo.append("Rental base charge: $").append(String.format("%.2f", baseRentalCharge)).append("\n");
        rentalInfo.append("Rental mileage charge: $").append(String.format("%.2f", mileageCharge)).append("\n");
        rentalInfo.append("Total rental charge: $").append(String.format("%.2f", totalRentalCharge)).append("\n");

        return rentalInfo.toString();
    }
}
