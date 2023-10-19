// CST-183
// Aaron Pelto
// Fall 2023

package com.example.cst_183_program5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Driver extends Application {

    // This method will return a boolean value for the Classification
    // Customer Classification must be B, D or W (or b, d, or w)
    // B = Budget
    // D = Daily
    // W = Weekly
    public boolean validateCustomerClassification(String customerClassification) {
        return customerClassification.equals("B") || customerClassification.equals("D") || customerClassification.equals("W") || customerClassification.equals("b") || customerClassification.equals("d") || customerClassification.equals("w");
    }

    // Validates the Odometer Readings
    // Can not be less than 0
    // End can not be less than the start
    public boolean validateOdometer(int odometerStart, int odometerEnd) {
        return odometerStart >= 0 && odometerEnd >= 0 && odometerEnd >= odometerStart;
    }

    // Rentals can not be equal to or less than 0
    // Rentals can not be more than 60 days
    public boolean validateRentalDays(int rentalDays) {
        return rentalDays >= 0 && rentalDays <= 60;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Car Rental Calculator");

        // Setting the GridPane Stage
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 700, 500);
        primaryStage.setScene(scene);

        // Calculate Button
        // Calculates the Rental Cost
        Button calculateButton = new Button("Calculate Cost");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(calculateButton);
        grid.add(hbBtn, 1, 7);


        // Clear Button
        // Clears Fields
        Button clearButton = new Button("Clear");
        HBox hbBtn2 = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(clearButton);
        grid.add(hbBtn2, 1, 8);

        // This holds the labels!
        // This is way easier!
        final Text actionTarget = new Text();
        grid.add(actionTarget, 1, 8);


        //Setting the scene title
        // This gives the whole window a label above the input fields
        Text sceneTitle = new Text("Calculate Rental Cost");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);


        // Text Field 1
        // Customer Classification (B, D or W)
        Label customerClassificationLabel = new Label("Customer Classification");
        grid.add(customerClassificationLabel, 0, 1);

        TextField customerClassificationTextField = new TextField();
        grid.add(customerClassificationTextField, 1, 1);

        // Text Field 2
        // Days Rented (1 - 60 Days)
        Label rentalDaysLabel = new Label("Days Vehicle Rented");
        grid.add(rentalDaysLabel, 0, 2);

        TextField rentalDaysTextField = new TextField();
        grid.add(rentalDaysTextField, 1, 2);

        // Text Field 3
        // Odometer Start
        // Greater than 0 but less than Odometer End
        Label odometerStartLabel = new Label("Odometer Start");
        grid.add(odometerStartLabel, 0, 3);

        TextField odometerStartTextField = new TextField();
        grid.add(odometerStartTextField, 1, 3);

        // Text Field 4
        // Odometer End
        // Greater than 0 but greater than Odometer Start
        Label odometerEndLabel = new Label("Odometer End");
        grid.add(odometerEndLabel, 0, 4);

        TextField odometerEndTextField = new TextField();
        grid.add(odometerEndTextField, 1, 4);

        // This will calculate the rental cost based on the customer code
        calculateButton.setOnAction(e -> {
            // This grabs the String value from the customer classification text field
            String customerClassification = customerClassificationTextField.getText();
            try {

                // This grabs the integers from the odometer fields, and days rented
                int odometerStart = Integer.parseInt(odometerStartTextField.getText());
                int odometerEnd = Integer.parseInt(odometerEndTextField.getText());
                int rentalDays = Integer.parseInt(rentalDaysTextField.getText());

                if (validateCustomerClassification(customerClassification)) {
                    if (validateRentalDays(rentalDays)) {
                        if (validateOdometer(odometerStart, odometerEnd)) {


                            // Setting the object and referencing the getters and settings in carRental.Java
                            carRental carRental = new carRental();
                            carRental.setCustomerClassification(customerClassification);
                            carRental.setCarRentalDays(rentalDays);
                            carRental.setCarOdometerStart(odometerStart);
                            carRental.setCarOdometerEnd(odometerEnd);

                            int mileage = carRental.calculateMileage(carRental.getCarOdometerStart(), carRental.getCarOdometerEnd());
                            double rentalCost = carRental.calculateRentalCost(carRental.getCustomerClassification(), carRental.getCarRentalDays(), mileage);


                            actionTarget.setText(carRental.rentalInfo(mileage, rentalDays));
                            // This is a test print statement
                            // I am leaving this in even for submission because it's helpful for debugging
                            // I know when production releases are ran, we should remove but there is a few object tests I wanted to verify
                            System.out.println("Total Rental Cost: $ " + rentalCost);
                        } else {
                            actionTarget.setFill(Color.RED);
                            actionTarget.setText("Invalid Odometer Entry.");
                        }
                    } else {
                        actionTarget.setFill(Color.RED);
                        actionTarget.setText("Invalid Days Rented Entry.");
                    }
                } else {
                    actionTarget.setFill(Color.RED);
                    actionTarget.setText("Invalid customer classification code. Please enter B, D or W.");
                }
            } catch (NumberFormatException exception) {
                actionTarget.setFill(Color.RED);
                actionTarget.setText("Invalid number entry. Check Car Rental Days, Car Odometer Start and End Values");
            }
        });

        // This button will clear the fields and show a prompt notifying the user that the fields were cleared.
        clearButton.setOnAction(e -> {
            customerClassificationTextField.clear();
            rentalDaysTextField.clear();
            odometerStartTextField.clear();
            odometerEndTextField.clear();

            actionTarget.setFill(Color.RED);
            actionTarget.setText("Fields Cleared");
        });


        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}