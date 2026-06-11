package test_2020.drugi;

import java.util.Set;
import java.util.TreeSet;

public class Naloga21 {
    public static void main(String[] args) {
        String beseda = args[0];

        TreeSet<String> vseBesede = new TreeSet<>();

        generirajNize("", beseda, vseBesede);

        System.out.println(vseBesede);
    }

    private static void generirajNize(String prefix, String preostanek, TreeSet<String> vseBesede) {
        if (preostanek.length() == 0) {
            vseBesede.add(prefix);
            return;
        }

        for (int i = 0; i < preostanek.length(); i++) {
            char prvaCrka = preostanek.charAt(i);

            String novPreostanek = preostanek.substring(0, i) + preostanek.substring(i + 1);

            generirajNize(prefix + prvaCrka, novPreostanek, vseBesede);
        }
    }
}
