package preiskava;

import kriptoborza.Borza;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test06 {

    @Test
    public void testClassExists() {
        assertDoesNotThrow(
                () -> Class.forName("kriptoborza.Denarnica"),
                "Razred 'Denarnica' bi moral biti v paketu 'kriptoborza'."
        );

        assertDoesNotThrow(
                () -> Class.forName("kriptoborza.Borza"),
                "Razred 'Borza' bi moral biti v paketu 'kriptoborza'."
        );

        assertDoesNotThrow(
                () -> Class.forName("kriptoborza.Transakcija"),
                "Razred 'Transakcija' bi moral biti v paketu 'kriptoborza'."
        );
    }

    private void setGraf(Borza b, Map<String, Set<String>> graf) throws Exception {
        Field f = Borza.class.getDeclaredField("graf");
        f.setAccessible(true);
        f.set(b, graf);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Set<String>> getGraf(Borza b) throws Exception {
        Field f = Borza.class.getDeclaredField("graf");
        f.setAccessible(true);
        return (Map<String, Set<String>>) f.get(b);
    }

    private Map<String, Set<String>> primerGrafa() {
        Map<String, Set<String>> graf = new LinkedHashMap<>();

        graf.put("ACC1001", new LinkedHashSet<>(Set.of("ACC2001", "ACC1002")));
        graf.put("ACC1002", new LinkedHashSet<>(Set.of("ACC2003")));
        graf.put("ACC1003", new LinkedHashSet<>(Set.of("ACC2002")));

        return graf;
    }

    private void dodaj(Borza b, String izvor, String cilj, String valuta, double znesek, String cas) {
        b.dodajTransakcijo(izvor, cilj, valuta, znesek, cas);
    }

    @Test
    void borzaImaMetodoPridobiVsaVozlisca() throws Exception {
        Method metoda = Borza.class.getDeclaredMethod("pridobiVsaVozlisca");

        assertEquals(Set.class, metoda.getReturnType());
    }

    @Test
    void borzaImaMetodoRazporediVozlisca() throws Exception {
        Method metoda = Borza.class.getDeclaredMethod("razporediVozlisca", Set.class);

        assertEquals(Map.class, metoda.getReturnType());
    }

    @Test
    void borzaImaMetodoIzrisiGraf() throws Exception {
        Method metoda = Borza.class.getDeclaredMethod("izrisiGraf", String.class);

        assertEquals(void.class, metoda.getReturnType());
    }

    @Test
    void pridobiVsaVozliscaVrneVseIzvorneInCiljneDenarnice() throws Exception {
        Borza b = new Borza("TemnaBorza");
        setGraf(b, primerGrafa());

        Set<String> vozlisca = b.pridobiVsaVozlisca();

        assertEquals(
                Set.of("ACC1001", "ACC2001", "ACC1002", "ACC2003", "ACC1003", "ACC2002"),
                vozlisca
        );
    }

    @Test
    void razporediVozliscaRazporediVsaVozliscaNaKroznicoZRAdijem035() {
        Borza b = new Borza("TemnaBorza");

        Set<String> vozlisca = new LinkedHashSet<>();
        vozlisca.add("A");
        vozlisca.add("B");
        vozlisca.add("C");
        vozlisca.add("D");

        Map<String, double[]> lokacije = b.razporediVozlisca(vozlisca);

        double centerX = 0.5;
        double centerY = 0.5;
        double radij = 0.35;
        double delta = 0.000001;

        for (double[] p : lokacije.values()) {
            double dx = p[0] - centerX;
            double dy = p[1] - centerY;
            double razdalja = Math.sqrt(dx * dx + dy * dy);

            assertEquals(radij, razdalja, delta);
        }
    }

    @Test
    void ustvariGrafIzTransakcijUstvariPricakovanePovezave() throws Exception {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "A", "B", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "A", "C", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "B", "D", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "X", "Y", "EUR", 100.0, "2026-05-01T10:15:23.123456789");

        b.ustvariGraf();

        Map<String, Set<String>> graf = getGraf(b);

        assertEquals(Set.of("B", "C"), graf.get("A"));
        assertEquals(Set.of("D"), graf.get("B"));
        assertEquals(Set.of("Y"), graf.get("X"));
    }

    @Test
    void poisciCiljneDenarniceVrneVsaDosegljivaVozliscaIzIzhodisca() {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "A", "B", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "A", "C", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "B", "D", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "X", "Y", "EUR", 100.0, "2026-05-01T10:15:23.123456789");

        b.ustvariGraf();

        Set<String> dosegljive = b.poisciCiljneDenarnice("A");

        assertEquals(Set.of("B", "C", "D"), dosegljive);
        assertFalse(dosegljive.contains("X"));
        assertFalse(dosegljive.contains("Y"));
    }
}