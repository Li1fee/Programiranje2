package kriptoborza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class KriptoValutaTest {
    private KriptoValuta kriptoValuta;
    private KriptoValuta btc;

    @BeforeEach
    void setUp() {
        kriptoValuta = new KriptoValuta("testna kripto valuta", "ABC", "SHA", 1234567890);
        btc = new KriptoValuta("bitcoin", "BTC", "SHA-256", 19000050L);
    }

    @Test
    void testAttributesKriptoValuta() {
        try {
            Class<?> razred = KriptoValuta.class;
            Field atribut1 = razred.getDeclaredField("zgoscenaVrednost");
            Field atribut2 = razred.getDeclaredField("algoritem");
            Field atribut3 = razred.getDeclaredField("trenutnaZaloga");

            assertTrue(Modifier.isPrivate(atribut1.getModifiers()), "Atribut 'zgoscenaVrednost' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut2.getModifiers()), "Atribut 'algoritem' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut3.getModifiers()), "Atribut 'trenutnaZaloga' bi morali biti private.");

            assertEquals(String.class, atribut1.getType(), "Atribut 'zgoscenaVrednost' bi moral biti tipa String.");
            assertEquals(String.class, atribut2.getType(), "Atribut 'algoritem' bi moral biti tipa String.");
            assertEquals(long.class, atribut3.getType(), "Atribut 'trenutnaZaloga' bi moral biti tipa long.");
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testGetOznaka() {
        assertEquals("ABC", kriptoValuta.getOznaka(), "Oznaka bi morala biti ABC");
        assertEquals("BTC", btc.getOznaka(), "Oznaka bi morala biti BTC");
    }

    @Test
    void testGetZgoscenaVrednost() {
        assertEquals("ABC90SHA", kriptoValuta.getZgoscenaVrednost(), "Zgoščena bi morala biti ABC90SHA");
        assertEquals("BTC50SHA-256", btc.getZgoscenaVrednost(), "Zgoščena vrednost bi morala biti BTC50SHA-256");
    }

    @Test
    void testZgoscenoVrednostPriNulah() {
        // Testiramo robni primer: ali format pravilno doda vodilno ničlo (npr. 5 -> 05)
        KriptoValuta eth = new KriptoValuta("ethereum", "ETH", "PoS", 105L);
        // 105 % 100 = 5 -> format mora vrniti "05"
        assertEquals("ETH05PoS", eth.getZgoscenaVrednost());
        KriptoValuta k = new KriptoValuta("testna kripto", "TTK", "PoS", 3L);
        // 3 % 100 = 3 -> format mora vrniti "03"
        assertEquals("TTK03PoS", k.getZgoscenaVrednost());
        KriptoValuta kv = new KriptoValuta("testna 2 kripto", "T2K", "PoS", 3000000L);
        // 3000000 % 100 = 0 -> format mora vrniti "00"
        assertEquals("T2K00PoS", kv.getZgoscenaVrednost());
    }

    @Test
    void testPridobiTip() {
        String tip = btc.pridobiTip();
        assertTrue(tip.contains("Kripto/Blockchain"), "Tip mora vsebovati pravilen tip valute.");
        assertTrue(tip.contains("SHA-256"), "Tip mora vsebovati ime algoritma.");
        assertTrue(tip.contains(btc.getZgoscenaVrednost()), "Tip mora vsebovati zgoščeno vrednost.");

        assertEquals("Kripto/Blockchain; SHA [ABC90SHA]", kriptoValuta.pridobiTip(), "Tip bi moral biti: Kripto/Blockchain; SHA [ABC90SHA]");
        assertEquals("Kripto/Blockchain; SHA-256 [BTC50SHA-256]", btc.pridobiTip(), "Tip bi moral biti: Kripto/Blockchain; SHA-256 [BTC50SHA-256]");
    }

    @Test
    void testToString() {
        String s = btc.toString();
        assertTrue(s.startsWith("BTC"), "Niz, ki ga vrne toString(), se mora začeti z oznako valute.");
        assertTrue(s.contains(btc.getZgoscenaVrednost()), "Niz, ki ga vrne toString(), mora vsebovati zgoščeno vrednost valute.");
        assertTrue(s.contains("bitcoin"), "Niz, ki ga vrne toString(), mora vsebovati naziv valute.");

        assertEquals("ABC (Kripto/Blockchain; SHA [ABC90SHA]; testna kripto valuta)", kriptoValuta.toString(), "Napačno sestavljen niz v toString().");
    }

}
