package kodirniki;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KodirnikTest {

    private static class DummyKodirnik implements Kodirnik {
        @Override
        public int zakodiraj(int vrednost) {
            return vrednost + 1;
        }

        @Override
        public int odkodiraj(int vrednost) {
            return vrednost - 1;
        }

        @Override
        public void ponastavi() {
            // nič
        }
    }

    @Test
    void testImplementacijaDeluje() {
        Kodirnik k = new DummyKodirnik();
        assertEquals(6, k.zakodiraj(5));
        assertEquals(5, k.odkodiraj(6));
    }

}
