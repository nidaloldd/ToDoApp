package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *  CetliContainer is a class where every Cetli stored.
 */
public class CetliContainer {
    private static List<Cetli> cetlis = new ArrayList<>();

    public static Cetli get(int i){
        return cetlis.get(i);
    }
    public static void add(Cetli cetli){
        cetlis.add(cetli);
    };
    public static void remove(Cetli cetli){
        cetlis.remove(cetli);
    }

}
