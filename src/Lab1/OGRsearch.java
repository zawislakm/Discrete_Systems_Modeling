package Lab1;

import java.util.ArrayList;
import java.util.Arrays;
// 2/2pts
public class OGRsearch {

    public static boolean check(int[] ruler) {
        // check if Golomb ruler
        ArrayList<Integer> diffs = new ArrayList<>();
        for (int i = 0; i < ruler.length; i++) {
            for (int j = i + 1; j < ruler.length; j++) {
                int dif = ruler[j] - ruler[i];
                if (!diffs.contains(dif)) {
                    diffs.add(dif);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean combination(int[] arr, int[] data, int start, int end, int index, int r) {

        if (index == r) {
            int[] ruler = new int[r+1];
            ruler[0] = 0;
            for (int i = 0; i < r; i++) {
                ruler[i+1] = data[i];
            }
            if (check(ruler)) {
                System.out.println(Arrays.toString(ruler));
                return true;
            }
            return false;
        }
        // for to pick next number on ruler, higher than previous and lower end value
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr[i];
            if (combination(arr, data, i + 1, end, index + 1, r)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        int order = 6; //number of ruler

        int end = order;
        int[] arr = new int[order];
        for (int i = 0; i < order; i++) {
            arr[i] = i+1;
        }
        while (true) {
            int[] data = new int[order-1];
            data[order-2] = end;
            if (combination(arr, data, 0, arr.length-2, 0, order-1)) {
                break;
            }
            end += 1;
            arr = Arrays.copyOf(arr, arr.length+1);
            arr[arr.length-1] = end;
        }
    }
}
