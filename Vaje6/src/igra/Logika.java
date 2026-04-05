package igra;

import java.util.Random;

public class Logika {
    static int[][] polja;
    static int tocke;
    static boolean konec;
    static Random rnd = new Random();
    static int velikostPolja;
    static int maxVrenodst = 0;

    public static void zacniNovoIgro(int velikost) {
        polja = new int[velikost][];
        velikostPolja = velikost;

        for (int i = 0; i < velikost; i++) {
            polja[i] = new int[velikost];
        }
        tocke = 0;
        konec = false;

        izbiranjePolja();
        izbiranjePolja();
    }

    public static void koncajIgro() {
        if (konec) {
            System.exit(0);
        }
        konec = true;
    }

    public static int vrniPloscico(int i, int j) {
        return polja[i][j];
    }

    public static int vrniTocke() {
        return tocke;
    }

    public static boolean jeZmagal() {
        return maxVrenodst >= 2048;
    }

    public static boolean jeKonec() {
        return konec;
    }

    public static void naslednjaPoteza(int smer) {
        if (smer == 0) {
            Premik();
        } else if (smer == 1) {
            rotiraj();
            rotiraj();
            Premik();
            rotiraj();
            rotiraj();
        } else if (smer == 2) {
            rotiraj();
            rotiraj();
            rotiraj();
            Premik();
            rotiraj();
        } else {
            rotiraj();
            Premik();
            rotiraj();
            rotiraj();
            rotiraj();
        }
    }

    // pomožne funkcije
    static void izbiranjePolja() {
        int izbranoPolje;
        do {
            izbranoPolje = rnd.nextInt(velikostPolja * velikostPolja);
        } while (polja[izbranoPolje / velikostPolja][izbranoPolje % velikostPolja] != 0);

        polja[izbranoPolje / velikostPolja][izbranoPolje % velikostPolja] = izbranaVrednost();
    }

    static int izbranaVrednost() {
        return rnd.nextDouble() < 0.9 ? 2 : 4;
    }

    static void Premik() {
        if (!mozenPremik()) {
            konec = true;
            return;
        }

        boolean premik = false;

        for (int i = 0; i < velikostPolja; i++) {
            int pos = 0;
            int najdenPos = -1;
            int[] staraVrstica = polja[i].clone();

            for (int j = 0; j < velikostPolja; j++) {
                if (polja[i][j] == 0) continue;

                if (najdenPos == -1) {
                    najdenPos = polja[i][j];
                } else if (najdenPos == polja[i][j]) {
                    polja[i][pos++] = najdenPos * 2;
                    tocke += najdenPos * 2;
                    maxVrenodst = Math.max(maxVrenodst, najdenPos * 2);
                    najdenPos = -1;
                } else {
                    polja[i][pos++] = najdenPos;
                    najdenPos = polja[i][j];
                }

            }

            if (najdenPos != -1) {
                polja[i][pos++] = najdenPos;
            }

            for (int j = pos; j < velikostPolja; j++) {
                polja[i][j] = 0;
            }

            for (int j = 0; j < velikostPolja; j++) {
                if (polja[i][j] != staraVrstica[j]) {
                    premik = true;
                    break;
                }
            }
        }

        if (premik) {
            izbiranjePolja();
        }
    }


    private static void rotiraj() {
        for (int i = 0; i < velikostPolja; i++) {
            for (int j = i + 1; j < velikostPolja; j++) {
                int tmp = polja[i][j];
                polja[i][j] = polja[j][i];
                polja[j][i] = tmp;
            }
        }

        for (int i = 0; i < velikostPolja; i++) {
            for (int j = 0; j < velikostPolja / 2; j++) {
                int tmp = polja[i][j];
                polja[i][j] = polja[i][velikostPolja - 1 - j];
                polja[i][velikostPolja - 1 - j] = tmp;
            }
        }
    }

    private static boolean mozenPremik() {
        for (int x = 0; x < 4; x++) {
            for (int i = 0; i < velikostPolja; i++) {
                for (int j = 0; j < velikostPolja - 1; j++) {
                    if (polja[i][j] == polja[i][j + 1] || polja[i][j] == 0 || polja[i][j + 1] == 0) {
                        return true;
                    }
                }
            }
            rotiraj();
        }

        return false;
    }
}
