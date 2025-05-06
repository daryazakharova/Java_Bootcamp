package edu.school21.classes;

import java.util.StringJoiner;

public class Car {

    private String model;
    private String color;
    private int mileage;

    public Car() {
        this.model = "Default model";
        this.color = "Default color";
        this.mileage = 0;
    }

    public Car(String model, String color, int mileage) {
        this.model = model;
        this.color = color;
        this.mileage = mileage;
    }

    public int updateMileage(int value) {
        this.mileage += value;
        return mileage;
    }

    public String updateColor(String newColor) {
        this.color = newColor;
        return color;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("model='" + model + "'")
                .add("color='" + color + "'")
                .add("year=" + mileage)
                .toString();
    }
}
