package test_2025_6_12_1000;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Naloga12 {
    public static void main(String[] args) {
        String args1 = args[0];

        MnozicaZnakov[] sudokuX = new MnozicaZnakov[9];

        for (int i = 0; i < 9; i++) {
            sudokuX[i] = new MnozicaZnakov();
            for (int j = 0; j < 9; j++) {
                boolean res = sudokuX[i].add(args1.charAt(i * 9 + j));
                if (!res) {
                    System.out.printf("Napaka v vrstici %d (znak '%c')", i + 1, args1.charAt(i * 9 + j));
                    return;
                }
            }
        }

        MnozicaZnakov[] sudokuY = new MnozicaZnakov[9];

        for (int i = 0; i < 9; i++) {
            sudokuY[i] = new MnozicaZnakov();
            for (int j = 0; j < 9; j++) {
                boolean res = sudokuY[i].add(args1.charAt(j * 9 + i));
                if (!res) {
                    System.out.printf("Napaka v stolpcu %d (znak '%c')", i + 1, args1.charAt(j * 9 + i));
                    return;
                }
            }
        }

        MnozicaZnakov[] sudokuXY = new MnozicaZnakov[9];
        for (int i = 0; i < 9; i++) {
            sudokuXY[i] = new MnozicaZnakov();
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boolean res = sudokuXY[(i / 3) * 3 + j / 3].add(args1.charAt(i * 9 + j));
                if (!res) {
                    System.out.printf("Napaka v kvadratu (%d, %d) (znak '%c')", i / 3 + 1, j / 3 + 1, args1.charAt(i * 9 + j));
                    return;
                }
            }
        }
        System.out.println("Postavitev je pravilna");
    }
}