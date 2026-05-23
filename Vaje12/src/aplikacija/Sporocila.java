package aplikacija;

import izjeme.IzjemaDatoteke;
import kodirniki.CezarjevAlgoritem;
import kodirniki.Kodiranje;

public class Sporocila {
    public static String besedilo = "Tukaj je dolg stavek.";

    static void main(String[] args) {
        CezarjevAlgoritem cezarjevAlgoritem = new CezarjevAlgoritem(1);

        Kodiranje kodiranje = new Kodiranje(cezarjevAlgoritem);

        besedilo = kodiranje.zakodiranjeBesedila(besedilo);
        System.out.println(besedilo);
        besedilo = kodiranje.odkodiranjeBesedila(besedilo);
        System.out.println(besedilo);
        try {
            kodiranje.zakodiranjeTekstovneDatoteke("randomDatoteka.txt", "vhodnaDatoteka.txt");
            kodiranje.odkodiranjeTekstovneDatoteke("vhodnaDatoteka.txt", "izhodnaDatoteka.txt");

            kodiranje.zakodiranjeBinarneDatoteke("slika.png", "vhodnaDatoteka.txt");
            kodiranje.odkodiranjeBinarneDatoteke("vhodnaDatoteka.txt", "slika2.png");
        } catch (IzjemaDatoteke e) {
            System.out.println("Napaka: " + e.getMessage());
        }

    }
}
