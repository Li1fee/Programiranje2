import java.io.File;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Vaje4 {
    public static void main(String[] args) throws FileNotFoundException {
        // preberiBesede("src/besede.txt");
        preberiSlovar("src/slovar.txt");

        while (true) {
            igra();
            System.out.print("Nova igra? (d/n): ");
            String odg = sc.nextLine();
            if (odg.toLowerCase().charAt(0) != 'd') {
                break;
            }
        }
    }

    // Konstante barv
    static final int BELA = 0;
    static final int CRNA = 1;
    static final int RUMENA = 2;
    static final int ZELENA = 3;

    // ANSI ukazi (za barvni izpis)
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_GREEN_BG = "\u001b[42m";
    static final String ANSI_YELLOW_BG = "\u001b[43m";
    static final String ANSI_WHITE_BG = "\u001b[47;1m";
    static final String ANSI_BLACK_BG = "\u001b[40m";
    static final String ANSI_WHITE = "\u001b[37m";

    static final String ANSI_BLACK = "\u001b[30m";
    static final String abeceda = "ABCČDEFGHIJKLMNOPRSŠTUVZŽ"; // Veljavne črke

    static final int MAX_POSKUSOV = 6; // Število poskusov
    static String[] seznamBesed; // Seznam vseh možnih besed
    static String[] slovar; // Seznam vseh možnih besed
    static String iskanaBeseda; // Iskana beseda trenutne igre

    static int[] barveAbecede; // Barve črk pri izpisu abecede

    static Scanner sc = new Scanner(System.in);

    static void izpisiZBarvo(char znak, int barva) {
        String slog;
        if (barva == ZELENA) {
            slog = ANSI_BLACK + ANSI_GREEN_BG;
        } else if (barva == RUMENA) {
            slog = ANSI_BLACK + ANSI_YELLOW_BG;
        } else if (barva == BELA) {
            slog = ANSI_BLACK + ANSI_WHITE_BG;
        } else {
            slog = ANSI_WHITE + ANSI_BLACK_BG;
        }
        System.out.print(slog + " " + znak + " " + ANSI_RESET);
    }

    static void preberiBesede(String datoteka) throws FileNotFoundException {
        Scanner fileData = new Scanner(new File(datoteka));
        int steviloBesed = Integer.parseInt(fileData.nextLine());
        seznamBesed = new String[steviloBesed];

        for (int i = 0; i < steviloBesed; i++) {
            seznamBesed[i] = fileData.nextLine().toUpperCase();
        }
    }

    static void novaIgra() {
        Random rnd = new Random();

        int ranomStevilo = rnd.nextInt(slovar.length);
        iskanaBeseda = slovar[ranomStevilo];

        barveAbecede = new int[abeceda.length()];

    }

    static void izpisiAbecedo() {
        System.out.print("Preostale črke: ");
        for (int i = 0; i < barveAbecede.length; i++) {
            izpisiZBarvo(abeceda.charAt(i), barveAbecede[i]);
        }
    }

    static boolean veljavnaBeseda(String beseda) {
        if (beseda.length() != 5) {
            System.out.println("Nepravilna dolžina besede!\n");
        } else if (!veljavneCrke(beseda)) {
            System.out.println("V besedi so neveljavni znaki!\n");
        } else if (!besedaVSlovarju(beseda)) {
            System.out.println("Besede ni v slovarju!\n");
        } else {
            return true;
        }
        return false;
    }

    static boolean veljavneCrke(String beseda) {
        for (char c: beseda.toCharArray()) {
            if (!abeceda.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    static boolean besedaVSlovarju(String beseda) {
        for (String slovarBeseda: slovar) {
            if (slovarBeseda.equals(beseda)) {
                return true;
            }
        }
        return false;
    }

    static int[] pobarvajBesedo(String ugibanaBeseda) {
        int dolzinaBesede = ugibanaBeseda.length();
        int[] pobarvanaBeseda = new int[dolzinaBesede];

        for (int i = 0; i < dolzinaBesede; i++) {
            char crkaUgibaneBesede = ugibanaBeseda.charAt(i);
            int pozicijaCrkeVAbecedi = abeceda.indexOf(crkaUgibaneBesede);

            if (crkaUgibaneBesede == iskanaBeseda.charAt(i)) {
                pobarvanaBeseda[i] = ZELENA;
                barveAbecede[pozicijaCrkeVAbecedi] = ZELENA;
            } else if (iskanaBeseda.contains(String.valueOf(crkaUgibaneBesede))) {
                pobarvanaBeseda[i] = RUMENA;
                if (barveAbecede[pozicijaCrkeVAbecedi] != ZELENA) {
                    barveAbecede[pozicijaCrkeVAbecedi] = RUMENA;
                }
            } else {
                barveAbecede[pozicijaCrkeVAbecedi] = CRNA;
                pobarvanaBeseda[i] = BELA;
            }
        }
        return pobarvanaBeseda;
    }

    static void izpisiBesedo(String ugibanaBeseda, int[] barve) {
        for (int i = 0; i < ugibanaBeseda.length(); i++) {
            izpisiZBarvo(ugibanaBeseda.charAt(i), barve[i]);
        }
        System.out.println("\n");
    }

    static void preberiSlovar(String datoteka) throws FileNotFoundException {
        Scanner fileData = new Scanner(new File(datoteka));
        int steviloBesed = Integer.parseInt(fileData.nextLine());
        slovar = new String[steviloBesed];

        for (int i = 0; i < steviloBesed; i++) {
            slovar[i] = fileData.nextLine().toUpperCase();
        }
    }

    static int[] pravilnoPobarvajBesedo(String ugibanaBeseda) {
        int dolzinaBesede = ugibanaBeseda.length();
        int[] pobarvanaBeseda = new int[dolzinaBesede];
        HashMap<Character, Integer> steviloCrk = new HashMap<>();

        // STEVILO CRK
        for (char c: iskanaBeseda.toCharArray()) {
            steviloCrk.put(c, steviloCrk.getOrDefault(c, 0) + 1);
        }

        // ZELENA
        for (int i = 0; i < dolzinaBesede; i++) {
            char crkaUgibaneBesede = ugibanaBeseda.charAt(i);
            int pozicijaCrkeVAbecedi = abeceda.indexOf(crkaUgibaneBesede);

            if (crkaUgibaneBesede == iskanaBeseda.charAt(i)) {
                pobarvanaBeseda[i] = ZELENA;
                barveAbecede[pozicijaCrkeVAbecedi] = ZELENA;
                steviloCrk.put(crkaUgibaneBesede, steviloCrk.get(crkaUgibaneBesede) - 1);
            }
        }

        // RUMENA, ČRNA
        for (int i = 0; i < dolzinaBesede; i++) {
            if (pobarvanaBeseda[i] == ZELENA) continue;

            char crkaUgibaneBesede = ugibanaBeseda.charAt(i);
            int pozicijaCrkeVAbecedi = abeceda.indexOf(crkaUgibaneBesede);

            if (steviloCrk.getOrDefault(crkaUgibaneBesede, 0) > 0 && iskanaBeseda.contains(String.valueOf(crkaUgibaneBesede))) {
                pobarvanaBeseda[i] = RUMENA;
                steviloCrk.put(crkaUgibaneBesede, steviloCrk.get(crkaUgibaneBesede) - 1);

                if (barveAbecede[pozicijaCrkeVAbecedi] != ZELENA) {
                    barveAbecede[pozicijaCrkeVAbecedi] = RUMENA;
                }
            } else {
                pobarvanaBeseda[i] = BELA;

                if (barveAbecede[pozicijaCrkeVAbecedi] != ZELENA
                    && barveAbecede[pozicijaCrkeVAbecedi] != RUMENA) {
                    barveAbecede[pozicijaCrkeVAbecedi] = CRNA;
                }
            }
        }
        return pobarvanaBeseda;
    }


    static void igra() {
        novaIgra();
        System.out.println(iskanaBeseda);
        iskanaBeseda = "DREVO";

        int poskus = 1;
        boolean uganil = false;
        while (poskus <= MAX_POSKUSOV) {
            izpisiAbecedo();
            System.out.printf("\nPoskus %d/%d: ", poskus, MAX_POSKUSOV);
            String ugibanaBeseda = sc.nextLine().toUpperCase();

            if (!veljavnaBeseda(ugibanaBeseda))
                continue;

            int[] barve = pravilnoPobarvajBesedo(ugibanaBeseda);

            izpisiBesedo(ugibanaBeseda, barve);

            if (ugibanaBeseda.equals(iskanaBeseda)) {
                uganil = true;
                break;
            }
            poskus++;
        }

        if (uganil) {
            System.out.printf("Bravo! Besedo si uganil/a v %d poskusih.\n", poskus);
        } else {
            System.out.printf("Žal ti ni uspelo. Pravilna beseda: %s\n", iskanaBeseda);
        }
    }
}

