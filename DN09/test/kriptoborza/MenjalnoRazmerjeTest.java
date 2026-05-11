package kriptoborza;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class MenjalnoRazmerjeTest {

    private MenjalnoRazmerje tecaj, tecaj2, tecaj3;
    private KriptoValuta btc;
    private KriptoValuta eth;
    private FiatValuta eur;

    @BeforeAll
    static void setLocale() {
        // Nastavi privzeto lokalizacijo na angleško
        Locale.setDefault(Locale.of("en", "US"));
    }

    @BeforeEach
    void setUp() {

        btc = new KriptoValuta("bitcoin", "BTC", "SHA-256", 100L);
        eth = new KriptoValuta("ethereum", "ETH", "PoS", 200L);
        eur = new FiatValuta("evro", "EUR", "Evropska unija");
        // Postavimo tečaj 1 BTC = 15.5 ETH s provizijo 0.5 odstotka
        tecaj = new MenjalnoRazmerje(btc, eth, 15.5, 0.005);
        // Postavimo tečaj 1 EUR = 0.00031 ETH s provizijo 1 odstotka
        tecaj2 = new MenjalnoRazmerje(eur, eth, 0.00031, 0.01);
        // Postavimo tečaj 1 BTC = 20.45 ETH brez provizije
        tecaj3 = new MenjalnoRazmerje(btc, eth, 20.45, 0);
    }

    @Test
    void testAttributesKriptoValuta() {
        try {
            Class<?> razred = MenjalnoRazmerje.class;
            Field atribut1 = razred.getDeclaredField("izvornaValuta");
            Field atribut2 = razred.getDeclaredField("ciljnaValuta");
            Field atribut3 = razred.getDeclaredField("tecaj");
            Field atribut4 = razred.getDeclaredField("provizija");

            assertTrue(Modifier.isPrivate(atribut1.getModifiers()), "Atribut 'izvornaValuta' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut2.getModifiers()), "Atribut 'ciljnaValuta' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut3.getModifiers()), "Atribut 'tecaj' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut4.getModifiers()), "Atribut 'provizija' bi morali biti private.");

            assertEquals(Valuta.class, atribut1.getType(), "Atribut 'izvornaValuta' bi moral biti tipa Valuta.");
            assertEquals(Valuta.class, atribut2.getType(), "Atribut 'ciljnaValuta' bi moral biti tipa Valuta.");
            assertEquals(double.class, atribut3.getType(), "Atribut 'tecaj' bi moral biti tipa double.");
            assertEquals(double.class, atribut4.getType(), "Atribut 'provizija' bi moral biti tipa double.");
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testVrniMenjalniPar() {
        assertEquals("BTC:ETH", tecaj.vrniMenjalniPar(), "Menjalni par mora biti v formatu IZVORNA:CILJNA");
        assertEquals("EUR:ETH", tecaj2.vrniMenjalniPar(), "Menjalni par mora biti v formatu IZVORNA:CILJNA");
    }

    @Test
    void testPretvori() {
        // Izračun: 10 * 15.5 * (1 - 0.005) = 155.0 * 0.995 = 154.225
        double kolicina = 10.0;
        double pricakovano = 154.225;
        assertEquals(pricakovano, tecaj.pretvori(kolicina), "Pretvorba ni pravilna: 10 * 15.5 * (1 - 0.005) = 154.225");
        // Izračun: 1000 * 0.00031 * (1 - 0.01) = 0.31 * 0.99 = 0.3069
        kolicina = 1000;
        pricakovano = 0.3069;
        assertEquals(pricakovano, tecaj2.pretvori(kolicina), "Pretvorba ni pravilna: 1000 * 0.00031 * (1 - 0.01) = 0.3069");
    }

    @Test
    void testPretvoriBrezProvizijeInKolicinoNic() {
        // Testiramo menjavo, če je količina 0 ali če ni provizije
        assertEquals(0, tecaj.pretvori(0), "Pretvorba za količino 0 ni pravilna");
        assertEquals(204.5, tecaj3.pretvori(10), "Pretvorba ni pravilna: 10 * 20.45 = 204.5");
    }

    @Test
    void testToString() {
        String s = tecaj.toString();
        // Preverimo, če vsebuje ključne dele
        assertTrue(s.contains("BTC -> ETH"), "ToString mora vsebovati smer menjave.");
        assertTrue(s.contains("15.500000"), "ToString mora vsebovati tečaj s 6 decimalkami.");
        // Provizija 0.005 je v toString izražena v odstotkih: 0.005 * 100 = 50.00 %
        assertTrue(s.contains("0.50 %"), "Provizija mora biti izražena v odstotkih.");
        assertEquals("EUR -> ETH :    0.000310 (provizija: 1.00 %)", tecaj2.toString());
    }

}
