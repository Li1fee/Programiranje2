import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kviz4 {
    public static void main(String[] args) {

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
}
