package kodirniki;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KodiranjeTest {

    @Test
    void testZakodiranjeBesedilaCezar() {
        Kodiranje kodiranje = new Kodiranje(new CezarjevAlgoritem(1));
        String rezultat = kodiranje.zakodiranjeBesedila("abc");
        assertEquals("bcd", rezultat);
    }

    @Test
    void testOdkodiranjeBesedilaCezar() {
        Kodiranje kodiranje = new Kodiranje(new CezarjevAlgoritem(1));
        String rezultat = kodiranje.odkodiranjeBesedila("bcd");
        assertEquals("abc", rezultat);
    }

    @Test
    void testZakodirajInOdkodirajBesediloCezar() {
        Kodiranje kodiranje = new Kodiranje(new CezarjevAlgoritem(5));
        String original = "Test123";
        String encoded = kodiranje.zakodiranjeBesedila(original);
        String decoded = kodiranje.odkodiranjeBesedila(encoded);
        assertEquals(original, decoded);
    }

    @Test
    void testPrazenNizCezar() {
        Kodiranje kodiranje = new Kodiranje(new CezarjevAlgoritem(3));
        assertEquals("", kodiranje.zakodiranjeBesedila(""));
        assertEquals("", kodiranje.odkodiranjeBesedila(""));
    }

    @Test
    void testPosebniZnakiCezar() {
        Kodiranje kodiranje = new Kodiranje(new CezarjevAlgoritem(1));
        String original = "a! b?";
        String encoded = kodiranje.zakodiranjeBesedila(original);
        String decoded = kodiranje.odkodiranjeBesedila(encoded);
        assertEquals(original, decoded);
    }

//    @Test
//    void testZakodirajInOdkodirajBesediloXOR() {
//        Kodiranje kodiranje = new Kodiranje(new XORAlgoritem("geslo"));
//        String original = "Pozdravljeni, danes kodiramo in dekodiramo!";
//        String encoded = kodiranje.zakodiranjeBesedila(original);
//        String decoded = kodiranje.odkodiranjeBesedila(encoded);
//        assertEquals(original, decoded);
//    }

}
