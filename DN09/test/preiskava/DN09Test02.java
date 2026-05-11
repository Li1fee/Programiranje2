package preiskava;

import kriptoborza.Borza;
import kriptoborza.Transakcija;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test02 {

    @Test
    void transakcijaImaTocnoSestAtributov() {
        long steviloAtributov = Arrays.stream(Transakcija.class.getDeclaredFields())
                .filter(f -> !f.isSynthetic())
                .count();

        assertEquals(6, steviloAtributov);
    }

    @Test
    void atributIdJePrivateInTipaInt() throws NoSuchFieldException {
        Field id = Transakcija.class.getDeclaredField("id");

        assertTrue(Modifier.isPrivate(id.getModifiers()));
        assertEquals(int.class, id.getType());
    }

    @Test
    void atributIzvorJePrivateInTipaString() throws NoSuchFieldException {
        Field izvor = Transakcija.class.getDeclaredField("izvor");

        assertTrue(Modifier.isPrivate(izvor.getModifiers()));
        assertEquals(String.class, izvor.getType());
    }

    @Test
    void atributCiljJePrivateInTipaString() throws NoSuchFieldException {
        Field cilj = Transakcija.class.getDeclaredField("cilj");

        assertTrue(Modifier.isPrivate(cilj.getModifiers()));
        assertEquals(String.class, cilj.getType());
    }

    @Test
    void atributValutaJePrivateInTipaString() throws NoSuchFieldException {
        Field valuta = Transakcija.class.getDeclaredField("valuta");

        assertTrue(Modifier.isPrivate(valuta.getModifiers()));
        assertEquals(String.class, valuta.getType());
    }

    @Test
    void atributZnesekJePrivateInTipaDouble() throws NoSuchFieldException {
        Field znesek = Transakcija.class.getDeclaredField("znesek");

        assertTrue(Modifier.isPrivate(znesek.getModifiers()));
        assertEquals(double.class, znesek.getType());
    }

    @Test
    void atributCasJePrivateInTipaString() throws NoSuchFieldException {
        Field cas = Transakcija.class.getDeclaredField("cas");

        assertTrue(Modifier.isPrivate(cas.getModifiers()));
        assertEquals(String.class, cas.getType());
    }

    @Test
    void konstruktorImaPravilneParametre() throws NoSuchMethodException {
        Constructor<Transakcija> konstruktor = Transakcija.class.getDeclaredConstructor(
                int.class,
                String.class,
                String.class,
                String.class,
                double.class,
                String.class
        );

        assertNotNull(konstruktor);
    }

    @Test
    void konstruktorPravilnoNastaviVrednosti() throws Exception {
        Transakcija t = new Transakcija(
                1,
                "ACC1001",
                "ACC2001",
                "EUR",
                120.50,
                "2026-05-01T10:15:23.123456789"
        );

        assertEquals(1, t.getId());
        assertEquals("ACC1001", t.getIzvor());
        assertEquals("ACC2001", t.getCilj());
        assertEquals("EUR", t.getValuta());
        assertEquals(120.50, t.getZnesek(), 0.000001);

        Field cas = Transakcija.class.getDeclaredField("cas");
        cas.setAccessible(true);

        assertEquals("2026-05-01T10:15:23.123456789", cas.get(t));
    }

    @Test
    void toStringVrnePravilnoObliko() {
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        try {
            Transakcija t = new Transakcija(
                    7,
                    "ACC1001",
                    "ACC2001",
                    "EUR",
                    120.50,
                    "2026-05-01T10:15:23.123456789"
            );

            assertEquals(
                    "[2026-05-01T10:15:23.123456789] ID: 7 | IZVOR: ACC1001 | CILJ: ACC2001 | ZNESEK: 120.50 EUR",
                    t.toString()
            );
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

    @Test
    void toStringZaokroziZnesekNaDveDecimalki() {
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        try {
            Transakcija t = new Transakcija(
                    8,
                    "ACC1001",
                    "ACC2001",
                    "EUR",
                    120.567,
                    "2026-05-01T10:15:23.123456789"
            );

            assertEquals(
                    "[2026-05-01T10:15:23.123456789] ID: 8 | IZVOR: ACC1001 | CILJ: ACC2001 | ZNESEK: 120.57 EUR",
                    t.toString()
            );
        } finally {
            Locale.setDefault(originalLocale);
        }
    }

    @Test
    void casImaPricakovanoObliko() throws Exception {
        Transakcija t = new Transakcija(
                1,
                "ACC1001",
                "ACC2001",
                "EUR",
                120.50,
                "2026-05-01T10:15:23.123456789"
        );

        Field cas = Transakcija.class.getDeclaredField("cas");
        cas.setAccessible(true);

        String vrednostCasa = (String) cas.get(t);

        assertTrue(vrednostCasa.matches(
                "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d+"
        ));
    }

    @SuppressWarnings("unchecked")
    private List<Transakcija> getZgodovina(Borza b) throws Exception {
        Field zgodovina = Borza.class.getDeclaredField("zgodovina");
        zgodovina.setAccessible(true);

        return (List<Transakcija>) zgodovina.get(b);
    }

    private String getCas(Transakcija t) throws Exception {
        Field cas = Transakcija.class.getDeclaredField("cas");
        cas.setAccessible(true);

        return (String) cas.get(t);
    }

    @Test
    void preberiTransakcijeVrneTrueZaVeljavnoDatoteko() throws Exception {
        Borza b = new Borza("TemnaBorza");

        Path datoteka = Files.createTempFile("transakcije", ".txt");

        Files.writeString(
                datoteka,
                """
                ACC1001,ACC2001,EUR,120.50,2026-05-01T10:15:23.123456789
                ACC1002,ACC2003,USD,75.00,2026-05-01T10:16:23.123456789
                ACC1003,ACC2002,GBP,210.30,2026-05-01T10:17:23.123456789
                """
        );

        boolean rezultat = b.preberiTransakcije(datoteka.toString());

        assertTrue(rezultat);
    }

    @Test
    void preberiTransakcijeDodaVseTransakcijeVZgodovino() throws Exception {
        Borza b = new Borza("TemnaBorza");

        Path datoteka = Files.createTempFile("transakcije-test", ".txt");

        Files.writeString(
                datoteka,
                """
                ACC1001,ACC2001,EUR,120.50,2026-05-01T10:15:23.123456789
                ACC1002,ACC2003,USD,75.00,2026-05-01T10:16:23.123456789
                ACC1003,ACC2002,GBP,210.30,2026-05-01T10:17:23.123456789
                """
        );

        b.preberiTransakcije(datoteka.toString());

        List<Transakcija> zgodovina = getZgodovina(b);

        assertEquals(3, zgodovina.size());
    }

    @Test
    void preberiTransakcijePravilnoPreberePrvoTransakcijo() throws Exception {
        Borza b = new Borza("TemnaBorza");

        Path datoteka = Files.createTempFile("transakcije-test", ".txt");

        Files.writeString(
                datoteka,
                """
                ACC1001,ACC2001,EUR,120.50,2026-05-01T10:15:23.123456789
                ACC1002,ACC2003,USD,75.00,2026-05-01T10:16:23.123456789
                """
        );

        b.preberiTransakcije(datoteka.toString());

        List<Transakcija> zgodovina = getZgodovina(b);

        Transakcija prva = zgodovina.get(0);

        assertEquals("ACC1001", prva.getIzvor());
        assertEquals("ACC2001", prva.getCilj());
        assertEquals("EUR", prva.getValuta());
        assertEquals(120.50, prva.getZnesek(), 0.000001);
        assertEquals("2026-05-01T10:15:23.123456789", getCas(prva));
    }

    @Test
    void preberiTransakcijePravilnoPrebereVecRazlicnihValut() throws Exception {
        Borza b = new Borza("TemnaBorza");

        Path datoteka = Files.createTempFile("transakcije-test", ".txt");

        Files.writeString(
                datoteka,
                """
                ACC1001,ACC2001,EUR,120.50,2026-05-01T10:15:23.123456789
                ACC1002,ACC2003,USD,75.00,2026-05-01T10:16:23.123456789
                ACC1003,ACC2002,GBP,210.30,2026-05-01T10:17:23.123456789
                ACC1005,ACC2001,CHF,340.00,2026-05-01T10:18:23.123456789
                """
        );

        b.preberiTransakcije(datoteka.toString());

        List<Transakcija> zgodovina = getZgodovina(b);

        assertEquals("EUR", zgodovina.get(0).getValuta());
        assertEquals("USD", zgodovina.get(1).getValuta());
        assertEquals("GBP", zgodovina.get(2).getValuta());
        assertEquals("CHF", zgodovina.get(3).getValuta());
    }

    @Test
    void preberiTransakcijeVrneFalseZaNeobstojecoDatoteko() {
        Borza b = new Borza("TemnaBorza");

        boolean rezultat = b.preberiTransakcije("datoteka-ki-ne-obstaja.txt");

        assertFalse(rezultat);
    }

    @Test
    void preberiTransakcijeOmogocaPravilenToStringPrebranihTransakcij() throws Exception {
        Locale originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);

        try {
            Borza b = new Borza("TemnaBorza");

            Path datoteka = Files.createTempFile("transakcije-test", ".txt");

            Files.writeString(
                    datoteka,
                    """
                    ACC1001,ACC2001,EUR,120.567,2026-05-01T10:15:23.123456789
                    """
            );

            b.preberiTransakcije(datoteka.toString());

            List<Transakcija> zgodovina = getZgodovina(b);

            Transakcija t = zgodovina.get(0);

            assertTrue(t.toString().contains("IZVOR: ACC1001"));
            assertTrue(t.toString().contains("CILJ: ACC2001"));
            assertTrue(t.toString().contains("ZNESEK: 120.57 EUR"));
            assertTrue(t.toString().contains("[2026-05-01T10:15:23.123456789]"));
        } finally {
            Locale.setDefault(originalLocale);
        }
    }
}
