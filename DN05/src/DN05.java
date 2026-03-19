import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DN05 {
    public static void main(String[] args) {
        if (args.length != 2 || !"branje".equals(args[0])) {
            System.out.println("Napaka: neustrezni argumenti.");
            return;
        }

        String imeDatoteke = args[1];

        String tipDatoteke;
        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return;
            }
            tipDatoteke = sc.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            return;
        }
        char[][] rezultat;
        if (tipDatoteke.equals("UREJENO")) {
            rezultat = preberiUrejenoDatoteko(imeDatoteke);
        } else if (tipDatoteke.equals("NEUREJENO")) {
            rezultat = preberiNeurejenoDatoteko(imeDatoteke);
        } else {
            System.out.println("Napačna vrsta datoteke.");
            return;
        }

        izpisRezultat(rezultat);
    }

    static void izpisRezultat(char[][] rezultat) {
        if (rezultat == null) return;
        for (char[] vrstica: rezultat) {
            System.out.println(new String(vrstica));
        }
    }

    static char[][] preberiUrejenoDatoteko(String imeDatoteke) {
        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            // tip datoteke check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            if (!sc.nextLine().equals("UREJENO")) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            // dimenzija check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            String[] dimenzijaStrani = sc.nextLine().split("x");
            if (dimenzijaStrani.length != 2) {
                System.out.println("Napaka: nepravilen format datoteke.");
                sc.close();
                return null;
            }

            int sirinaStrani = Integer.parseInt(dimenzijaStrani[0]);
            int visinaStrani = Integer.parseInt(dimenzijaStrani[1]);


            char[][] tabelaStrani = new char[visinaStrani][];

            for (int i = 0; i < visinaStrani; i++) {
                if (!sc.hasNextLine()) {
                    System.out.println("Napaka: nepravilne dimenzije strani.!");
                    return null;
                }

                String vrsticaStrani = sc.nextLine();
                if (vrsticaStrani.length() != sirinaStrani) {
                    System.out.println("Napaka: nepravilne dimenzije strani.!");
                    return null;
                }
                tabelaStrani[i] = vrsticaStrani.toCharArray();
            }
            // preveri če je še več vrstic
            if (sc.hasNextLine()) {
                System.out.println("Napaka: nepravilne dimenzije strani.");
                return null;
            }

            return tabelaStrani;

        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            return null;
        }
    }

    static char[][] preberiNeurejenoDatoteko(String imeDatoteke) {
        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            // tip datoteke check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            if (!sc.nextLine().equals("NEUREJENO")) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            // dimenzija check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            String[] dimenzijaStrani = sc.nextLine().split("x");
            if (dimenzijaStrani.length != 2) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            int sirinaStrani = Integer.parseInt(dimenzijaStrani[0]);
            int visinaStrani = Integer.parseInt(dimenzijaStrani[1]);

            // vsebina chech
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            char[][] tabelaStrani = new char[visinaStrani][];

            String[] besedeStrani = sc.nextLine().split(" ");

            int steviloVrstic = 0;
            StringBuilder trenutniStavek = new StringBuilder();

            for (String beseda : besedeStrani) {
                int dolzinaBesede = beseda.length();

                if (dolzinaBesede > sirinaStrani || steviloVrstic == visinaStrani) {
                    System.out.println("Napaka: premajhne dimenzije strani.");
                    return null;
                }

                int dodatno = trenutniStavek.isEmpty() ? dolzinaBesede : dolzinaBesede + 1;

                if (trenutniStavek.length() + dodatno > sirinaStrani) {
                    String celStavek = trenutniStavek + ("_").repeat(sirinaStrani - trenutniStavek.length());
                    tabelaStrani[steviloVrstic++] = celStavek.toCharArray();
                    trenutniStavek.setLength(0); // -> ""
                }

                trenutniStavek.append(trenutniStavek.isEmpty() ? "" : "_").append(beseda);
            }

            // edge case
            if (!trenutniStavek.isEmpty()) {
                if (steviloVrstic >= visinaStrani) {
                    System.out.println("Napaka: premajhne dimenzije strani.");
                    return null;
                }
                String celStavek = trenutniStavek + ("_").repeat(sirinaStrani - trenutniStavek.length());
                tabelaStrani[steviloVrstic++] = celStavek.toCharArray();
            }

            // manjkajoče linije
            while (steviloVrstic < visinaStrani) {
                String praznaVrstica = ("_").repeat(sirinaStrani);
                tabelaStrani[steviloVrstic++] = praznaVrstica.toCharArray();
            }

            return tabelaStrani;

        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            return null;
        }
    }
}