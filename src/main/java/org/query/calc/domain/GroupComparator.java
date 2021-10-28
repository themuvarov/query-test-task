package org.query.calc.domain;

import java.util.Comparator;

public class GroupComparator implements Comparator<Group> {

    @Override
    public int compare(Group a, Group b) {
        int result = a.getSum().compareTo(b.getSum());
        if (result != 0) {
            return result * (-1);
        }
        //STABLE ORDER BY s DESC
        return Integer.compare(a.getFirstOcc(), b.getFirstOcc());
    }
}