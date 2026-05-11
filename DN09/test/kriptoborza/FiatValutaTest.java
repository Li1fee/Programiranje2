package kriptoborza;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class FiatValutaTest {
    private FiatValuta fiatValuta;

    @BeforeEach
    void setup() {
        fiatValuta = new FiatValuta("evro", "EUR", "Evropska unija");
    }

    @Test
    void testAttributesFiatValuta() {
        try {
            Class<?> razred = FiatValuta.class;
            Field atribut = razred.getDeclaredField("drzavaIzdajateljica");
            assertTrue(Modifier.isPrivate(atribut.getModifiers()), "Atribut 'drzavaIzdajateljica' bi morali biti private.");
            assertEquals(String.class, atribut.getType(), "Atribut 'drzavaIzdajateljica' bi moral biti tipa String.");
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testGetOznaka() {
        assertEquals("EUR", fiatValuta.getOznaka(), "Oznaka bi morala biti EUR.");
    }

    @Test
    void testPridobiTip() {
        assertEquals("Fiat/Državna; Evropska unija", fiatValuta.pridobiTip(), "Metoda pridobiTip() bi morala vrniti: Fiat/Državna; Evropska unija");
    }

    @Test
    void testToString() {
        String s = fiatValuta.toString();
        assertTrue(s.startsWith("EUR"), "Niz, ki ga vrne toString(), se mora začeti z oznako valute.");
        assertTrue(s.contains("evro"), "Niz, ki ga vrne toString(), mora vsebovati naziv valute.");
        assertEquals("EUR (Fiat/Državna; Evropska unija; evro)", s, "Napačno sestavljen niz v toString().");
    }

}
