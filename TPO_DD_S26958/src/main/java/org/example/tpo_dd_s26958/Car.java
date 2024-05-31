package org.example.tpo_dd_s26958;

public class Car {
    private String type;
    private String brand;
    private int year;
    private double fuelConsumption;

    public Car(String type, String brand, int year, double fuelConsumption) {
        this.type = type;
        this.brand = brand;
        this.year = year;
        this.fuelConsumption = fuelConsumption;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }

    public int getYear() {
        return year;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }
}
