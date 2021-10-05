package twitter;

import java.util.Comparator;
import java.util.Map;

class MyComparator implements Comparator {

Map map;

public MyComparator(Map map) {
    this.map = map;
}

public int compare(Object o1, Object o2) {

    return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));

}
}