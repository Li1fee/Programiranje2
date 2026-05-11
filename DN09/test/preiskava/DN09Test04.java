package preiskava;

import kriptoborza.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test04 {
    private Borza borza;
    private Denarnica d1;
    private Denarnica d2;

    @BeforeEach
    void setUp() {
        borza = new Borza("Test borza");

        d1 = new Denarnica("ACC1");
        d2 = new Denarnica("ACC2");

        // začetna stanja
        d1.dodaj("EUR", 1000);
        d2.dodaj("EUR", 100);

        FiatValuta eur = new FiatValuta("evro", "EUR", "Evropska unija");
        FiatValuta dolar = new FiatValuta("ameriški dolar", "USD", "Združene države Amerike");

        // dodamo menjalni par EUR -> USD (npr. 1 EUR = 1.1 USD)
        MenjalnoRazmerje par = new MenjalnoRazmerje(eur, dolar, 1.08, 0.001);
        borza.dodajPar(par);
    }

    @Test
    void testUspesnaTransakcija() {
        boolean rezultat = borza.izvediTransakcijo(d1, d2, "EUR", 200);

        assertTrue(rezultat);
        assertEquals(800, d1.stanje("EUR"), 0.001);
        assertEquals(300, d2.stanje("EUR"), 0.001);
    }

    @Test
    void testNeuspesnaTransakcijaPremaloSredstev() {
        boolean rezultat = borza.izvediTransakcijo(d2, d1, "EUR", 1000);

        assertFalse(rezultat);
        assertEquals(100, d2.stanje("EUR"), 0.001);
        assertEquals(1000, d1.stanje("EUR"), 0.001);
    }

    @Test
    void testNeveljavenZnesek() {
        boolean rezultat = borza.izvediTransakcijo(d1, d2, "EUR", -50);

        assertFalse(rezultat);
    }

    @Test
    void testUspesnaMenjava() {
        boolean rezultat = borza.izvediMenjavo(d1, "EUR", "USD", 100);

        assertTrue(rezultat);

        // EUR se zmanjša
        assertEquals(900, d1.stanje("EUR"), 0.001);

        assertEquals(107.892, d1.stanje("USD"), 0.001);
    }

    @Test
    void testNeuspesnaMenjavaPremaloSredstev() {
        boolean rezultat = borza.izvediMenjavo(d2, "EUR", "USD", 500);

        assertFalse(rezultat);

        // stanje ostane nespremenjeno
        assertEquals(100, d2.stanje("EUR"), 0.001);
        assertEquals(0, d2.stanje("USD"), 0.001);
    }

    @Test
    void testMenjalniParNeObstaja() {
        boolean rezultat = borza.izvediMenjavo(d1, "EUR", "CHF", 50);

        assertFalse(rezultat);
    }
}
