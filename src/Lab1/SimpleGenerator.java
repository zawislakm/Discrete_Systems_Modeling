package Lab1;

import java.util.*;
// 3/3pts
public class SimpleGenerator {
    // simple generator Golomb Ruler
    // using Wikipedia formula
    // https://en.wikipedia.org/wiki/Golomb_ruler
    // 2pk+(k^2 % p), k -> [0,p-1]
    public static void main(String[] args) {

        int[] prime_numbers = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41};

        for (int p : prime_numbers) {

            List<Integer> ruler = new ArrayList<>();

            for (int k = 0; k < p; k++) {

                int tmp = 2 * p * k + ((k * k) % p);
                ruler.add(tmp);
            }
            System.out.println(ruler);

            Set<Integer> difs = new HashSet<>();

            for (int k = 0; k < ruler.size(); k++) {
                for (int j = k + 1; j < ruler.size(); j++) {
                    int dif = ruler.get(j) - ruler.get(k);
                    if (difs.contains(dif)) {
                        System.out.println("Repeat!");
                        System.exit(1);

                    } else {
                        difs.add(dif);
                    }
                }
            }

            System.out.println("All diffrent");
            System.out.println(difs);
        }

    }
}