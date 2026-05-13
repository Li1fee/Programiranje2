import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kviz4 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(getVrstica(11)));
        izpisiBesedilo("src/test.txt", 20, 30);
    }

    void dvojnaNagrada(String igralkeFilename, String igralciFilename) {
        java.util.TreeMap<String, String[]> filmi = new java.util.TreeMap<>();

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(igralkeFilename))) {
            while (sc.hasNextLine()) {
                // Indeks, Leto, Starost, Ime in priimek, Naslov filma
                String[] filmPodatki = sc.nextLine().split(",");
                // Leto, Ime in priimek, Naslov filma
                String[] podatki = new String[]{filmPodatki[1], filmPodatki[3], ""};

                filmi.put(filmPodatki[4], podatki);
            }
        } catch (java.io.FileNotFoundException e) {
            return;
        }

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(igralciFilename))) {
            while (sc.hasNextLine()) {
                // Indeks, Leto, Starost, Ime in priimek, Naslov filma
                String[] filmPodatki = sc.nextLine().split(",");

                if (filmi.containsKey(filmPodatki[4])) {
                    filmi.get(filmPodatki[4])[2] = filmPodatki[3];
                }

            }
        } catch (java.io.FileNotFoundException e) {
            return;
        }

        for (java.util.Map.Entry<String, String[]> p : filmi.entrySet()) {
            String[] podatki = p.getValue();
            if (podatki[2].isEmpty()) continue;
            System.out.printf("Film:%s, Leto:%s, Igralka:%s, Igralec:%s\n", p.getKey(), podatki[0], podatki[1], podatki[2]);
        }
    }

    void poisciInIzpisiBarve(String imeDatoteke) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(?<!\\S)color:\\s*#([0-9a-fA-F]{6})");

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                java.util.regex.Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    String barva = matcher.group(1);

                    int rr = Integer.parseInt(barva.substring(0, 2), 16);
                    int gg = Integer.parseInt(barva.substring(2, 4), 16);
                    int bb = Integer.parseInt(barva.substring(4, 6), 16);

                    double r = rr / 255.0;
                    double g = gg / 255.0;
                    double b = bb / 255.0;

                    double Cmin = Math.min(Math.min(r, g), b);
                    double Cmax = Math.max(Math.max(r, g), b);
                    double delta = Cmax - Cmin;

                    double L = (Cmax + Cmin) / 2.0;
                    double S = (delta == 0) ? 0 : delta / (1 - Math.abs(2 * L - 1));

                    double H = 0;
                    if (delta != 0) {
                        if (Cmax == r) {
                            H = 60 * (((g - b) / delta) % 6);
                        } else if (Cmax == g) {
                            H = 60 * (((b - r) / delta) + 2);
                        } else {
                            H = 60 * (((r - g) / delta) + 4);
                        }
                    }
                    if (H < 0) H += 360;

                    long resH = Math.round(H);
                    long resS = Math.round(S * 100);
                    long resL = Math.round(L * 100);

                    System.out.printf("#%s -> rgb(%d, %d, %d) -> hsl(%d, %d, %d)\n", barva, rr, gg, bb, resH, resS, resL);
                }
            }
        } catch (java.io.FileNotFoundException e) {
            return;
        }
    }

    static void statistikaStavkov(String imeDatoteke) throws IzjemaManjkajocegaLocila {
        java.util.TreeMap<Integer, Integer> frekvenca = new java.util.TreeMap<>();
        int dolzinaTrenutnegaStavka = 0;
        boolean koncanoZLocilom = true;

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {

            while (sc.hasNext()) {
                String beseda = sc.next();
                koncanoZLocilom = false;
                dolzinaTrenutnegaStavka++;

                if (beseda.matches(".*[.!?]")) {
                    frekvenca.put(dolzinaTrenutnegaStavka, frekvenca.getOrDefault(dolzinaTrenutnegaStavka, 0) + 1);
                    dolzinaTrenutnegaStavka = 0;
                    koncanoZLocilom = true;
                }

            }

            if (!koncanoZLocilom && dolzinaTrenutnegaStavka > 0) {
                throw new IzjemaManjkajocegaLocila("Izjema manjkajocega locila.");
            }

            for (java.util.Map.Entry<Integer, Integer> f : frekvenca.entrySet()) {
                System.out.printf("Stavki dolzine %d se pojavijo: %dx.\n", f.getKey(), f.getValue());
            }
        } catch (java.io.FileNotFoundException e) {
            System.out.printf("Napaka pri branju datoteke.");
            return;
        }
    }

    static class IzjemaManjkajocegaLocila extends java.lang.RuntimeException {
        public IzjemaManjkajocegaLocila(String sporocilo) {
            super(sporocilo);
        }
    }

    static void preberiRacunInIzpisi(String imeDatoteke) {
        double skupniDDV = 0;
        double skupniZnesek = 0;
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([0-9]). ([a-zA-Z])");

        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                java.util.regex.Matcher matcher = pattern.matcher(line);
                String[] lineSplited = line.split("\\s");

                if (!matcher.find() || lineSplited.length < 4) continue;

                double znesek = Double.parseDouble(lineSplited[lineSplited.length - 1].replace(",", "."));
                double ddv = Double.parseDouble(lineSplited[lineSplited.length - 2].replace(",", "."));

                skupniZnesek += znesek;
                skupniDDV += ddv;
            }
            System.out.printf("Skupaj brez DDV:%7.2f\n" +
                    "DDV:%19.2f\n" +
                    "ZNESEK SKUPAJ:%9.2f\n"
                    ,skupniZnesek - skupniDDV, skupniDDV, skupniZnesek);

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Napaka pri branju datoteke!\n");
            return;
        }
    }

    void izpisi(String imeDatoteke) {

    }

    void preveri(String stevilka, String imeDatoteke) {

    }

    static int[] getVrstica(int n) {
        int[][] vrstica = new int[n][];

        for (int i = 0; i < n; i++) {
            int[] novaVrstica = new int[i + 1];
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    novaVrstica[j] = (i + 1) % 10;
                } else {
                    novaVrstica[j] = (novaVrstica[j - 1] + vrstica[i - 1][j - 1]) % 10;
                }
            }
            vrstica[i] = novaVrstica;
        }

        return vrstica[n - 1];
    }

    static void izpisiBesedilo(String imeDatoteke, int n, int s) {
        int i = 0;
        StringBuilder vrstica = new StringBuilder();
        java.util.ArrayList<String> besede = new java.util.ArrayList<>();


        try (java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                besede.addAll(java.util.Arrays.asList(line.split("\\s")));
            }


            while (i < besede.size()) {
                String beseda = besede.get(i);
                if (beseda.length() > n) break;

                if (vrstica.isEmpty()) {
                    vrstica.append(beseda);
                    i++;
                } else if (vrstica.length() + beseda.length() + 1 <= n) {
                    vrstica.append(" ").append(beseda);
                    i++;
                } else {
                    int dolzinaVrstice = vrstica.length();
                    System.out.printf("%s%s%s\n", ".".repeat((s - dolzinaVrstice) / 2), vrstica, ".".repeat((s - dolzinaVrstice) / 2 + ((s - dolzinaVrstice) % 2)));
                    vrstica.setLength(0);
                }
            }
            int dolzinaVrstice = vrstica.length();
            System.out.printf("%s%s%s\n", ".".repeat((s - dolzinaVrstice) / 2), vrstica, ".".repeat((s - dolzinaVrstice) / 2 + ((s - dolzinaVrstice) % 2)));;

        } catch (java.io.FileNotFoundException e) {
            System.out.println("Napaka pri branju datoteke!\n");
            return;
        }
    }
}
