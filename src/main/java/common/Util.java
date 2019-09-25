package common;

import java.util.List;

public class Util {

    public static <T> T getLastElementOfList(List<T> list) {
        return list.get(list.size() - 1);
    }
}
