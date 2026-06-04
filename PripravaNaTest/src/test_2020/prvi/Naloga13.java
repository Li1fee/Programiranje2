package test_2020.prvi;

import java.util.Locale;

public class Naloga13 {
    public static void main(String[] args) {
        StringBuilder argumentiBesedila = new StringBuilder();
        for (String arg : args) {
            argumentiBesedila.append(arg).append(" ");
        }
        System.out.println(popraviBesedilo(argumentiBesedila.toString()));
    }

    private static String popraviBesedilo(String besedilo) {
        StringBuilder popravljenoBesedilo = new StringBuilder();
        String prevWord = "";

        for (String beseda : besedilo.split(" ")) {
            StringBuilder popravljenaBeseda = new StringBuilder();
            popravljenaBeseda.append(beseda.charAt(0));
            for (int i = 1; i < beseda.length(); i++) {
                if (popravljenaBeseda.toString().toLowerCase().charAt(Math.min(popravljenaBeseda.length() - 1, i - 1)) == beseda.toLowerCase().charAt(i)) continue;

                popravljenaBeseda.append(beseda.charAt(i));
            }
            if (!prevWord.contentEquals(popravljenaBeseda.toString().toLowerCase())) {
                popravljenoBesedilo.append(popravljenaBeseda).append(" ");
                prevWord = popravljenaBeseda.toString().toLowerCase();
            }
        }

        return popravljenoBesedilo.substring(0, popravljenoBesedilo.length() - 1);
    }

}
