package zbirke;

import izpis.Znaki;

public class Seznam {
    private static String[] seznam;
    private static int steviloElementov = 0;


     public static boolean narediSeznam(int n) {
        if (seznam != null || n <= 0) {
            return false;
        }

        seznam = new String[n];
        steviloElementov = 0;
        return true;
    }

    public static boolean dodajNaKonecSeznama(String element) {
        if (seznam == null) {
            return false;
        }

        if (seznam.length <= steviloElementov) {
            String[] podaljsanSeznam = new String[seznam.length * 2];
            for (int i = 0; i < seznam.length; i++) {
                podaljsanSeznam[i] = seznam[i];
            }
            seznam = podaljsanSeznam.clone();
        }

        seznam[steviloElementov++] = element;
        return true;
    }

    public static void izpisiSeznam() {
        if (steviloElementov == 0) {
            System.out.println("Seznam je prazen (nima elementov).");
            return;
        }
        if (seznam == null) {
            System.out.println("NAPAKA: Seznam ne obstaja.");
            return;
        }

        System.out.println("  Na seznamu so naslednji elementi:");
        for (int i = 0; i < seznam.length; i++) {
            System.out.printf("%3d: %s%n", i+1, seznam[i]);
        }
    }

    public static String odstraniIzSeznama(int mesto) {
        if (mesto > steviloElementov || seznam[mesto - 1] == null || mesto < 1) {
            return null;
        }

        int indeks = mesto - 1;
        String izbrisaniElement = seznam[indeks];

        for (int i = indeks; i < steviloElementov - 1; i++) {
            seznam[i] = seznam[i + 1];
        }

        seznam[steviloElementov - 1] = null;
        steviloElementov--;

        return izbrisaniElement;
    }

    public static boolean dodajVSeznam(String element, int mesto) {
        if (mesto < 1 || seznam == null || steviloElementov >= seznam.length) {
            return false;
        }

        for (int i = seznam.length - 1; i > mesto - 1 ; i--) {
            seznam[i] = seznam[i - 1];
        }

        seznam[mesto - 1] = element;
        steviloElementov++;
        return true;
    }

    public static int dolzinaSeznama() {
        return seznam == null ? -1 : steviloElementov;
    }

    public static boolean uniciSeznam() {
        if (seznam == null) {
            return false;
        }
        seznam = null;
        steviloElementov = 0;
        return true;
    }

    public static void izpisiSeznam64Bit() {
        if (seznam == null) {
            System.out.println("NAPAKA: Seznam ne obstaja.");
            return;
        }

        if (steviloElementov == 0) {
            System.out.println("Seznam je prazen (nima elementov).");
            return;
        }

        System.out.println("Elementi seznama (64-bitni zapis):");

        for (int i = 0; i < seznam.length; i++) {
            Znaki.izpisi64bit(String.format("%d. ", i+1) + seznam[i]);
        }

    }
}
