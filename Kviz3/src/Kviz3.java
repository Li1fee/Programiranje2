import java.util.Arrays;
import java.util.Scanner;

public class Kviz3 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(sestejPolinoma(new int[]{1,2,3}, new int[]{1,2,3,4,5,6})));
        System.out.println(Arrays.toString(zmnoziPolinoma(new int[]{1,2,3}, new int[]{4,5,6})));
        System.out.println(najdaljsiPalindrom("abc perica  reze raci rep def", true));
        System.out.println(izracunajRPN("1 2 3 4 + * +"));
        preveriInNarisi(new int[]{1,0,3,2});
    }

    public static int[] sestejPolinoma(int[] a, int[] b) {
        int[] c = a.clone();
        if (a.length < b.length) {
            a = b;
            b = c;
        }

        for (int i = 0; i < b.length; i++) {
            a[i] = a[i] + b[i];
        }
        return a;
    }

    public static int[] zmnoziPolinoma(int[] a, int[] b) {
        int aDolzina = a.length;
        int bDolzina = b.length;
        int najvecjaPotenca = aDolzina + bDolzina - 1;
        int[] vsePotenceXsa = new int[najvecjaPotenca];

        for (int i = 0; i < aDolzina; i++) {
            for (int j = 0; j < bDolzina; j++) {
                int potencaXsa = aDolzina - i + bDolzina - j - 2;
                vsePotenceXsa[najvecjaPotenca - potencaXsa - 1] += a[i] * b[j];
            }
        }
        return vsePotenceXsa;
    }

    public static boolean jeAnagram(String prvaBesede, String drugaBeseda, boolean zanemariVelikost) {
        if (zanemariVelikost) {
            prvaBesede = prvaBesede.toLowerCase();
            drugaBeseda = drugaBeseda.toLowerCase();
        }
        if (prvaBesede.equals(drugaBeseda) || prvaBesede.length() != drugaBeseda.length()) return false;

        java.util.HashMap<Character, Integer> crke = new java.util.HashMap<>();

        for (char c: prvaBesede.toCharArray()) {
            crke.put(c, crke.getOrDefault(c, 0) + 1);
        }
        for (char c: drugaBeseda.toCharArray()) {
            if (!crke.containsKey(c)) return false;
            crke.put(c, crke.get(c) - 1);
        }

        for (int steviloCrk: crke.values()) {
            if (steviloCrk != 0) return false;
        }
        return true;
    }

    public static String najdaljsiPalindrom(String niz, boolean presledki) {
        String najdaljsiPalindrom = "";

        for (int i = 0; i < niz.length(); i++) {
            for (int j = niz.length() - 1; j >= i; j--) {
                String podniz = niz.substring(i, j + 1);
                String presledkiPodniz = presledki ? podniz : podniz.replace(" ", "");

                if (podniz.length() > najdaljsiPalindrom.length() && jePalindorm(presledkiPodniz)) {
                    najdaljsiPalindrom = podniz;
                }
            }
        }
        return najdaljsiPalindrom;
    }

    public static boolean jePalindorm(String niz) {
        int leva = 0;
        int desna = niz.length() - 1;

        while (leva < desna) {
            if (niz.charAt(leva) != niz.charAt(desna)) {
                return false;
            }
            leva++;
            desna--;
        }
        return true;
    }

    static int izracunajRPN(String racun) {
        String[] splitanRacun = racun.split(" ");
        int[] sklad = new int[splitanRacun.length];
        int indexElementov = -1;

        for (String operator : splitanRacun) {
            if (operator.equals("+")) {
                sklad[indexElementov - 1] = sklad[indexElementov - 1] + sklad[indexElementov];
            } else if (operator.equals("-")) {
                sklad[indexElementov - 1] = sklad[indexElementov - 1] - sklad[indexElementov];
            } else if (operator.equals("*")) {
                sklad[indexElementov - 1] = sklad[indexElementov - 1] * sklad[indexElementov];
            } else if (operator.equals("/")) {
                sklad[indexElementov - 1] = sklad[indexElementov - 1] / sklad[indexElementov];
            } else {
                sklad[indexElementov + 1] = Integer.parseInt(operator);
                indexElementov += 2;
            }
            indexElementov--;
        }
        return sklad[0];
    }

    int bsdChecksum(String filename) {
        int checksum = 0;
        try {
            int ch;
            java.io.FileInputStream sc = new java.io.FileInputStream(filename);
            while ((ch = sc.read()) != -1) {
                checksum = (checksum >> 1) + ((checksum & 1) << 15);
                checksum += ch;
                checksum &= 0xffff;
            }
            sc.close();
        } catch (java.io.IOException e) {
            return 0;
        }
        return checksum;
    }

    static void preveriInNarisi(int [] kraljice) {
        for (int i = 0; i < kraljice.length; i++) {
            for (int j = 0; j < kraljice.length; j++) {
                if (i == kraljice[j]) {
                    System.out.print("K");
                } else {
                    System.out.print(".");
                }
                System.out.print(j == kraljice.length - 1 ? "" : " ");
            }
            System.out.println();
        }

        for (int i = 0; i < kraljice.length; i++) {
            for (int j = i + 1; j < kraljice.length; j++) {
                if (kraljice[j] == kraljice[i] + j - i || kraljice[j] == kraljice[i] - (j - i) || kraljice[j] == kraljice[i]) {
                    System.out.println("Kraljice se napadajo");
                    return;
                }
            }
        }
        System.out.println("Kraljice se ne napadajo");
    }

    public int steviloPostavitev(int n) {
        int[] polozaji = new int[n];
        return resi(0, n, polozaji);
    }

    private int resi(int vrstica, int n, int[] polozaji) {
        if (vrstica == n) {
            return 1;
        }

        int stevec = 0;
        for (int stolpec = 0; stolpec < n; stolpec++) {
            if (jeVarno(vrstica, stolpec, polozaji)) {
                polozaji[vrstica] = stolpec;
                stevec += resi(vrstica + 1, n, polozaji);
            }
        }
        return stevec;
    }

    private boolean jeVarno(int vrstica, int stolpec, int[] polozaji) {
        for (int i = 0; i < vrstica; i++) {
            int predhodniStolpec = polozaji[i];

            if (predhodniStolpec == stolpec) {
                return false;
            }

            if (Math.abs(predhodniStolpec - stolpec) == Math.abs(i - vrstica)) {
                return false;
            }
        }
        return true;
    }

    void preberiInIzpisi(String oznaka) {
        java.util.HashMap<String, Integer> studentTocke = new java.util.HashMap<>();
        java.util.ArrayList<String[]> studenti = new java.util.ArrayList<>();
        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(oznaka + "-prijave.txt"));
            while (sc.hasNextLine()) {
                String studentData = sc.nextLine();
                if (studentData.isEmpty()) continue;
                String[] id_ImePriimek = studentData.split(":");
                studenti.add(id_ImePriimek);
                studentTocke.put(id_ImePriimek[0], -1);
            }
            studenti.sort((a, b) -> a[1].compareTo(b[1]));

            for (int i = 1; i < 5; i++) {
                sc = new java.util.Scanner(new java.io.File(oznaka + "-n" + String.valueOf(i) + ".txt"));
                while (sc.hasNextLine()) {
                    String[] studentNalogaTocke = sc.nextLine().split(":");
                    int studentTrenutneTocke = studentTocke.get(studentNalogaTocke[0]) == -1 ? 0 : studentTocke.get(studentNalogaTocke[0]);
                    studentTocke.put(studentNalogaTocke[0], studentTrenutneTocke + Integer.parseInt(studentNalogaTocke[1]));
                }
            }

            for (String[] s : studenti) {
                String vpisnaStevilka = s[0];
                String ime = s[1];
                int tocke = studentTocke.get(vpisnaStevilka);

                System.out.println(vpisnaStevilka + ":" + ime + ":" + ((tocke == -1) ? "VP" : tocke));
            }

        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    void najdaljsaBeseda(String fileName) {
        String najdaljsaBeseda = "";
        try {
            Scanner sc = new Scanner(new java.io.File(fileName));
            while (sc.hasNext()) {
                String beseda = soSameCrke(sc.next());
                if (beseda.length() > najdaljsaBeseda.length()) {
                    najdaljsaBeseda = beseda;
                }
            }

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Datoteka ne obstaja!");
            return;
        }
        System.out.printf("Najdaljše zaporedje znakov brez prekinitve v datoteki '%s' je beseda '%s', ki je dolga %d znakov.", fileName, najdaljsaBeseda, najdaljsaBeseda.length());
    }

    String soSameCrke(String beseda) {
        if (beseda == null || beseda.isEmpty()) return "";

        for (int i = 0; i < beseda.length(); i++) {
            if ("?.!,".contains(String.valueOf(beseda.charAt(i)))) {
                if (i == beseda.length() - 1) {
                    return beseda.substring(0, beseda.length() - 1);
                }
                return "";
            } else if (Character.isDigit(beseda.charAt(i))) {
                return "";
            }
        }
        return beseda;
    }

    int countWords(String fileName) {
        try {
            int steviloBesed = 0;
            Scanner sc = new Scanner(new java.io.File(fileName));
            while (sc.hasNext()) {
                sc.next();
                steviloBesed++;
            }
            return steviloBesed;
        } catch (java.io.FileNotFoundException e) {
            return - 1;
        }
    }

    int countLines(String fileName) {
        try {
            int steviloVrstic = 0;
            Scanner sc = new Scanner(new java.io.File(fileName));
            while (sc.hasNextLine()) {
                sc.nextLine();
                steviloVrstic++;
            }
            return steviloVrstic;
        } catch (java.io.FileNotFoundException e) {
            return - 1;
        }
    }
}
