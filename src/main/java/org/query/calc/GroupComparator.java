package org.query.calc;

import java.util.Comparator;
import org.query.calc.domain.Group;

public class GroupComparator implements Comparator<Group> {

    @Override
    public int compare(Group a, Group b) {

        //ORDER BY s DESC
        int result = a.getSum().compareTo(b.getSum());
        if (result != 0) {
            return result * (-1);
        }
        //STABLE ORDER BY s DESC
        return Integer.compare(a.getFirstOcc(), b.getFirstOcc());
    }
}