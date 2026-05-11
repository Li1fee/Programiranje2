package preiskava;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test01a {

    @Test
    void testClassExists() {
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Valuta"), "Razred 'Valuta' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.KriptoValuta"), "Razred 'KriptoValuta' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.FiatValuta"), "Razred 'FiatValuta' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.MenjalnoRazmerje"), "Razred 'MenjalnoRazmerje' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("kriptoborza.Borza"), "Razred 'Borza' bi moral biti v paketu 'kriptoborza'.");
        assertDoesNotThrow(() -> Class.forName("preiskava.DN09"), "Razred 'DN09' bi moral biti v paketu 'preiskava'.");
    }


    @Test
    void testIzracunajZgoscenoVrednostExists() {
        Method metoda = null;

        try {
            metoda = kriptoborza.KriptoValuta.class.getDeclaredMethod("izracunajZgoscenoVrednost");
        } catch (NoSuchMethodException e) {
            fail("Privatna metoda izracunajZgoscenoVrednost() ni implementirana.");
        }

        assertTrue(Modifier.isPrivate(metoda.getModifiers()), "Metoda izracunajZgoscenoVrednost() mora biti private.");
    }

}
