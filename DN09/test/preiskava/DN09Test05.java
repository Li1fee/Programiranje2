package preiskava;

import kriptoborza.Borza;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test05 {

    @Test
    public void testClassExists() {
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Denarnica"), "Razred 'Denarnica' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Borza"), "Razred 'Borza' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Transakcija"), "Razred 'Transakcija' bi moral biti v paketu 'kriptoborza'.");
    }

    private void dodaj(Borza b, String izvor, String cilj, String valuta, double znesek, String cas) {
        b.dodajTransakcijo(izvor, cilj, valuta, znesek, cas);
    }

    private void ustvariGrafBrezIzpisa(Borza b) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(out));
            b.ustvariGraf();
        } finally {
            System.setOut(originalOut);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Set<String>> getGraf(Borza b) throws Exception {
        Field f = Borza.class.getDeclaredField("graf");
        f.setAccessible(true);
        return (Map<String, Set<String>>) f.get(b);
    }

    @Test
    void ustvariGrafUstvariPravilneNeposrednePovezave() throws Exception {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "ACC1001", "ACC2001", "EUR", 120.50, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1001", "ACC1002", "EUR", 999.99, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1002", "ACC2003", "USD", 75.00, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1003", "ACC2002", "GBP", 210.30, "2026-05-01T10:15:23.123456789");

        ustvariGrafBrezIzpisa(b);

        Map<String, Set<String>> graf = getGraf(b);

        assertEquals(Set.of("ACC2001", "ACC1002"), graf.get("ACC1001"));
        assertEquals(Set.of("ACC2003"), graf.get("ACC1002"));
        assertEquals(Set.of("ACC2002"), graf.get("ACC1003"));
    }

    @Test
    void poisciCiljneDenarniceNajdeDirektneInPosredneCilje() {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "ACC1001", "ACC1002", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1002", "ACC1003", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1003", "ACC1004", "EUR", 100.0, "2026-05-01T10:15:23.123456789");

        ustvariGrafBrezIzpisa(b);

        Set<String> rezultat = b.poisciCiljneDenarnice("ACC1001");

        assertEquals(Set.of("ACC1002", "ACC1003", "ACC1004"), rezultat);
    }

    @Test
    void poisciCiljneDenarniceNajdeVozliscaPoVecVejah() {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "ACC1001", "ACC1002", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1001", "ACC1003", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1002", "ACC1004", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1003", "ACC1005", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1005", "ACC1006", "EUR", 100.0, "2026-05-01T10:15:23.123456789");

        ustvariGrafBrezIzpisa(b);

        Set<String> rezultat = b.poisciCiljneDenarnice("ACC1001");

        assertEquals(
                Set.of("ACC1002", "ACC1003", "ACC1004", "ACC1005", "ACC1006"),
                rezultat
        );
    }

    @Test
    void poisciCiljneDenarniceNeGreVCikel() {
        Borza b = new Borza("TemnaBorza");

        dodaj(b, "ACC1001", "ACC1002", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1002", "ACC1003", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        dodaj(b, "ACC1003", "ACC1001", "EUR", 100.0, "2026-05-01T10:15:23.123456789");

        ustvariGrafBrezIzpisa(b);

        Set<String> rezultat = b.poisciCiljneDenarnice("ACC1001");

        assertEquals(Set.of("ACC1002", "ACC1003"), rezultat);
        assertFalse(rezultat.contains("ACC1001"));
    }
}
