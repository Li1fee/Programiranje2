package kriptoborza;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

class ValutaTest {

    @Test
    void testIsAbstractValuta() {
        int modifiers = Valuta.class.getModifiers();
        assertTrue(Modifier.isAbstract(modifiers), "Razred Valuta mora biti abstrakten.");
    }

    @Test
    void testAttributesValuta() {
        try {
            Class<?> razred = Valuta.class;
            Field atribut1 = razred.getDeclaredField("oznaka");
            Field atribut2 = razred.getDeclaredField("naziv");

            assertTrue(Modifier.isPrivate(atribut1.getModifiers()), "Atribut 'oznaka' bi morali biti private.");
            assertTrue(Modifier.isPrivate(atribut2.getModifiers()), "Atribut 'naziv' bi morali biti private.");

            assertEquals(String.class, atribut1.getType(), "Atribut 'oznaka' bi moral biti tipa String.");
            assertEquals(String.class, atribut2.getType(), "Atribut 'naziv' bi moral biti tipa String.");
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testAbstractMethod() throws NoSuchMethodException {
        Class<?> razred = Valuta.class;
        Method method = razred.getDeclaredMethod("pridobiTip");
        assertTrue(Modifier.isAbstract(method.getModifiers()), "Metoda pridobiTip() bi morala biti abstraktna.");
    }

    @Test
    void testGetOznaka() {
       Valuta v = new IzpeljanaValuta("Test", "TST");
        assertEquals("TST", v.getOznaka(), "Getter getOznaka() mora vrniti pravilno oznako.");
    }

    @Test
    void testToString() {
        Valuta v = new IzpeljanaValuta("Testna izpeljana valuta", "TST");
        assertEquals("TST (Testna valuta; Testna izpeljana valuta)", v.toString(), "Metoda toString mora vrniti pravilen opis valute.");
    }

}

class IzpeljanaValuta extends Valuta {
    public IzpeljanaValuta(String naziv, String oznaka) {
        super(naziv, oznaka);
    }

    public String pridobiTip() {
        return "Testna valuta";
    }
}
