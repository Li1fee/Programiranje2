package preiskava;

import kriptoborza.Borza;
import kriptoborza.Denarnica;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class DN09Test03 {

    @Test
    public void testClassExists() {
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Denarnica"), "Razred 'Denarnica' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Borza"), "Razred 'Borza' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Transakcija"), "Razred 'Transakcija' bi moral biti v paketu 'kriptoborza'.");
    }

    @SuppressWarnings("unchecked")
    private Map<String, Double> getStanja(Denarnica d) throws Exception {
        Field f = Denarnica.class.getDeclaredField("stanja");
        f.setAccessible(true);
        return (Map<String, Double>) f.get(d);
    }

    @Test
    public void dodajPozitivenZnesekDodaNovoValuto() throws Exception {
        Denarnica d = new Denarnica("ACC2002");

        boolean rezultat = d.dodaj("EUR", 560.0);

        assertTrue(rezultat);
        assertEquals(560.0, getStanja(d).get("EUR"));
    }

    @Test
    public void dodajPozitivenZnesekObstojeciValutiPovecaStanje() throws Exception {
        Denarnica d = new Denarnica("ACC2002");

        d.dodaj("EUR", 100.0);
        d.dodaj("EUR", 50.0);

        assertEquals(150.0, getStanja(d).get("EUR"));
    }


    @Test
    public void dodajNegativenZnesekNeSpremeniStanja() throws Exception {
        Denarnica d = new Denarnica("ACC2002");

        boolean rezultat = d.dodaj("EUR", -10.0);

        assertFalse(rezultat);
        assertFalse(getStanja(d).containsKey("EUR"));
    }

    @Test
    public void odstejPozitivenZnesekZmanjsaStanje() throws Exception {
        Denarnica d = new Denarnica("ACC2002");
        d.dodaj("EUR", 100.0);

        boolean rezultat = d.odstej("EUR", 40.0);

        assertTrue(rezultat);
        assertEquals(60.0, getStanja(d).get("EUR"));
    }

    @Test
    public void odstejVecKotJeNaStanjuNeSpremeniStanja() throws Exception {
        Denarnica d = new Denarnica("ACC2002");
        d.dodaj("EUR", 100.0);

        boolean rezultat = d.odstej("EUR", 150.0);

        assertFalse(rezultat);
        assertEquals(100.0, getStanja(d).get("EUR"));
    }

    @Test
    public void izpisiStanjeIzpiseIdInValute() {
        Locale.setDefault(new Locale("en", "US"));

        Denarnica d = new Denarnica("ACC2002");
        d.dodaj("EUR", 560.0);
        d.dodaj("GBP", 210.3);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(out));

        d.izpisiStanje();

        System.setOut(originalOut);

        String izpis = out.toString();

        assertTrue(izpis.contains("Stanje denarnice ACC2002:"));
        assertTrue(izpis.contains("EUR"));
        assertTrue(izpis.contains("560"));
        assertTrue(izpis.contains("GBP"));
        assertTrue(izpis.contains("210"));
    }

    @Test
    public void ustvariDenarniceUstvariCiljneDenarniceInDodaZneske() {
        Borza b = new Borza("TemnaBorza");

        // Prilagodi konstruktor Transakcija svoji implementaciji.
        b.dodajTransakcijo("ACC1001", "ACC2002", "EUR", 100.0, "2026-05-01T10:15:23.123456789");
        b.dodajTransakcijo("ACC1003", "ACC2002", "EUR", 50.0, "2026-05-01T10:15:23.123456789");
        b.dodajTransakcijo("ACC1004", "ACC3003", "GBP", 25.0, "2026-05-01T10:15:23.123456789");

        b.ustvariDenarnice();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        b.izpisiStanjeDenarnice("ACC2002");

        System.setOut(originalOut);

        String izpis = out.toString();

        assertTrue(izpis.contains("Stanje denarnice ACC2002:"));
        assertTrue(izpis.contains("EUR"));
        assertTrue(izpis.contains("150"));
    }
}
