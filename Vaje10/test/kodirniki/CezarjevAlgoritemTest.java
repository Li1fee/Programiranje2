package kodirniki;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CezarjevAlgoritemTest {

    @Test
    void testZakodirajPozitivniZamik() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(3);
        assertEquals(8, alg.zakodiraj(5));
    }

    @Test
    void testZakodirajNegativniZamik() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(-2);
        assertEquals(3, alg.zakodiraj(5));
    }

    @Test
    void testOdkodirajPozitivniZamik() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(3);
        assertEquals(5, alg.odkodiraj(8));
    }

    @Test
    void testOdkodirajNegativniZamik() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(-2);
        assertEquals(5, alg.odkodiraj(3));
    }

    @Test
    void testZakodirajInOdkodiraj() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(10);
        int original = 42;
        int encoded = alg.zakodiraj(original);
        int decoded = alg.odkodiraj(encoded);
        assertEquals(original, decoded);
    }

    @Test
    void testPonastaviNeSpremeniStanja() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(5);
        alg.ponastavi();

        // ker ni stanja, mora delovati enako
        assertEquals(10, alg.zakodiraj(5));
        assertEquals(5, alg.odkodiraj(10));
    }

    @Test
    void testRobneVrednosti() {
        CezarjevAlgoritem alg = new CezarjevAlgoritem(1);

        assertEquals(Integer.MAX_VALUE, alg.zakodiraj(Integer.MAX_VALUE - 1));
        assertEquals(Integer.MIN_VALUE, alg.odkodiraj(Integer.MIN_VALUE + 1));
    }
}