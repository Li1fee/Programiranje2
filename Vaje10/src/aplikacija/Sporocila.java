package aplikacija;

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
    }
}
