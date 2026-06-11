package test_2025_6_24_1100;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Naloga23 {
    public static void main(String[] args) {
        if (args.length != 2) return;
        String imeDatoteke = args[0];
        int n = Integer.parseInt(args[1]);
        StringBuilder crke = new StringBuilder();

        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            while (sc.hasNextLine()) {
                crke.append(sc.nextLine());
                if (sc.hasNextLine()) {
                    crke.append("\n");
                }
            }
            crke.append("\n");
        } catch (FileNotFoundException e) {
            return;
        }

        int dolzina = crke.length();

        for (int i = 0; i < dolzina; i += n) {
            StringBuilder hexZapis = new StringBuilder();
            StringBuilder crkeZapis = new StringBuilder();

            for (int j = 0; j < n; j++) {
                if (i + j < dolzina) {
                    char c = crke.charAt(i + j);
                    String hex = String.format("%s ", Integer.toHexString(c)).toUpperCase();
                    hexZapis.append(hex.length() == 2 ? "0" + hex : hex);

                    if (c < 32 || c > 127) {
                        crkeZapis.append('.');
                    } else {
                        crkeZapis.append(c);
                    }
                } else {
                    hexZapis.append("   ");
                    crkeZapis.append(" ");
                }
            }
            System.out.println(hexZapis + "| " + crkeZapis + " |");
        }
    }
}
