package kodirniki;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class XORAlgoritemTest {

    @Test
    void testZakodirajInOdkodiraj() {
        XORAlgoritem alg = new XORAlgoritem("k");

        int original = 'A';
        int encoded = alg.zakodiraj(original);
        alg.ponastavi();
        int decoded = alg.odkodiraj(encoded);

        assertEquals(original, decoded);
    }

    @Test
    void testZaporednoKodiranjeZGeslom() {
        XORAlgoritem alg = new XORAlgoritem("ab");

        int e1 = alg.zakodiraj('A'); // XOR z 'a'
        int e2 = alg.zakodiraj('A'); // XOR z 'b'

        assertNotEquals(e1, e2);
    }

    @Test
    void testCiklicnoGeslo() {
        XORAlgoritem alg = new XORAlgoritem("ab");

        int e1 = alg.zakodiraj('A'); // 'a'
        int e2 = alg.zakodiraj('A'); // 'b'
        int e3 = alg.zakodiraj('A'); // spet 'a'

        assertEquals(e1, e3);
        assertNotEquals(e1, e2);
    }

    @Test
    void testPonastavi() {
        XORAlgoritem alg = new XORAlgoritem("ab");

        int e1 = alg.zakodiraj('A');
        alg.zakodiraj('A'); // premik indeksa
        alg.ponastavi();
        int e2 = alg.zakodiraj('A');

        assertEquals(e1, e2);
    }

}
