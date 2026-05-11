package kriptoborza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class TransakcijaTest {
    private Transakcija transakcija;

    @BeforeEach
    void setup() {
        transakcija = new Transakcija(1, "ACC5555", "ACC6666", "USD", 12.34, "2026-05-02T22:10:05.161803398");
    }

    @Test
    void testAttributesTransakcija() {
        try {
            Class<?> razred = Transakcija.class;

            Field atribut1 = razred.getDeclaredField("id");
            Field atribut2 = razred.getDeclaredField("izvor");
            Field atribut3 = razred.getDeclaredField("cilj");
            Field atribut4 = razred.getDeclaredField("znesek");
            Field atribut5 = razred.getDeclaredField("cas");
            Field atribut6 = razred.getDeclaredField("valuta");

            assertTrue(Modifier.isPrivate(atribut1.getModifiers()), "Atribut 'id' bi morali biti private.");
            assertEquals(int.class, atribut1.getType(), "Atribut 'id' bi moral biti tipa int.");

            assertTrue(Modifier.isPrivate(atribut2.getModifiers()), "Atribut 'izvor' bi morali biti private.");
            assertEquals(String.class, atribut2.getType(), "Atribut 'izvor' bi moral biti tipa String.");

            assertTrue(Modifier.isPrivate(atribut3.getModifiers()), "Atribut 'cilj' bi morali biti private.");
            assertEquals(String.class, atribut3.getType(), "Atribut 'cilj' bi moral biti tipa String.");

            assertTrue(Modifier.isPrivate(atribut4.getModifiers()), "Atribut 'znesek' bi morali biti private.");
            assertEquals(double.class, atribut4.getType(), "Atribut 'izvor' bi moral biti tipa double.");

            assertTrue(Modifier.isPrivate(atribut5.getModifiers()), "Atribut 'cas' bi morali biti private.");
            assertEquals(String.class, atribut5.getType(), "Atribut 'cas' bi moral biti tipa double.");

            assertTrue(Modifier.isPrivate(atribut6.getModifiers()), "Atribut 'valuta' bi morali biti private.");
            assertEquals(String.class, atribut6.getType(), "Atribut 'valuta' bi moral biti tipa double.");
        } catch (NoSuchFieldException e) {
            fail();
        }
    }

    @Test
    void testValuta() {
        assertEquals("USD", transakcija.getValuta());
    }

    @Test
    void testToString() {
        assertEquals("[2026-05-02T22:10:05.161803398] ID: 1 | IZVOR: ACC5555 | CILJ: ACC6666 | ZNESEK: 12.34 USD", transakcija.toString());
    }

}
