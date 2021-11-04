package org.query.calc.domain;

import java.util.List;

public class Node {

    private List<Double> yz;

    private Node next;

    public Node(List<Double> yz) {
        this.yz = yz;
    }

    public void addYz(Double yz) {
        this.yz.add(yz);
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public List<Double> getYz() {
        return yz;
    }

    public Node getNext() {
        return this.next;
    }
}
