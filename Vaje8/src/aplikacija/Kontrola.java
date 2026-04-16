package aplikacija;

import vinjete.SeznamVinjet;

public class Kontrola {
    public static void main(String[] args) {
        SeznamVinjet seznamVinjet = new SeznamVinjet();
        if (seznamVinjet.preberiPodatke(args[0])) {
            seznamVinjet.izpisiVinjete();
            System.out.println();
            System.out.println(seznamVinjet.preveriVinjeto("MBSK345"));
            System.out.println();
            seznamVinjet.izpisiVinjete("polletna");
            System.out.println();
            seznamVinjet.izpisiVeljavneLetne("15.04.2026");
            System.out.println();
            seznamVinjet.statistika();
        } else {
            System.out.println("Napaka pri branju datoteke.");
        }
    }
}
