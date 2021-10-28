package org.query.calc.domain;

public class Row {

    public static final String DELIM = " ";

    private final int num;

    private int firstOcc;

    private final Double field1;

    private final Double field2;

    public Row(int num, Double field1, Double field2) {
        this.num = num;
        this.field1 = field1;
        this.field2 = field2;
    }

    public Double getField1() {
        return field1;
    }

    public Double getField2() {
        return field2;
    }

    public int getNum() {
        return num;
    }

    public int getFirstOcc() {
        return firstOcc;
    }

    public void setFirstOcc(int firstOcc) {
        this.firstOcc = firstOcc;
    }

}
