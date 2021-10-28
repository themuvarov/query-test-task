package org.query.calc.domain;


public class Group {

    private final Double value;

    private final int firstOcc;

    private Double sum = 0.;

    public Group(int firstOcc, Double value) {
        this.firstOcc = firstOcc;
        this.value = value;
    }

    public Double getValue() {
        return this.value;
    }

    public int getFirstOcc() {
        return this.firstOcc;
    }

    public void add(Double amount) {
        this.sum += amount;
    }

    public Double getSum() {
        return this.sum;
    }

}
