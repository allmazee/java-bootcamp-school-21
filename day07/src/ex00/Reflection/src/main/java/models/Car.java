package models;

import java.util.StringJoiner;

public class Car {
    private String manufacturer;
    private String model;
    private int price;

    public Car() {
        this.manufacturer = "Default manufacturer";
        this.model = "Default model";
        this.price = 0;
    }

    public Car(String manufacturer, String model, int price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
    }

    public int increasePrice(int value) {
        this.price += value;
        return price;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("manufacturer='" + manufacturer + "'")
                .add("model='" + model + "'")
                .add("price='" + price + "'")
                .toString();
    }
}
