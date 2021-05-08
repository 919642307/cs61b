package hw2;

import java.util.HashSet;

public class PercolationStats {
    public static void main(String[] args) {
        HashSet<Integer> test = new HashSet<>();
        int [] A = {1,7,3,4,5};
        int [] B = {5,6,8,9,10};
        for (int num : A) {
            test.add(num);
        }
        for (int num : B) {
            test.add(num);
        }
        System.out.println(test);
        System.out.println(test.size());
    }

}
