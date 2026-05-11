package kriptoborza;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class BorzaTest01 {
    private final ByteArrayOutputStream izpis = new ByteArrayOutputStream();
    private final PrintStream stdOut = System.out;

    private Borza borza;

    @TempDir
    Path tempDir; // JUnit sam ustvari to mapo

    @BeforeAll
    static void setupAll() {
        Locale.setDefault(Locale.of("en", "US"));
    }

    @BeforeEach
    void setUp() {
        // Preusmerimo System.out v naš tok izpis
        System.setOut(new PrintStream(izpis));
        borza = new Borza("Testna Borza");
    }

    @AfterEach
    void tearDown() {
        // Vrnemo System.out v prvotno stanje
        System.setOut(stdOut);
    }

    @Test
    void testPreberiValuteIzpisiValute() throws IOException {
        // Pripravimo začasno datoteko z dvema valutama
        Path valuteDat = tempDir.resolve("valute.txt");
        String vsebina = "K;bitcoin;BTC;SHA-256;19000000\nF;evro;EUR;Evropa";
        Files.writeString(valuteDat, vsebina);
        boolean uspeh = borza.preberiValute(valuteDat.toString());
        assertTrue(uspeh, "Branje valut bi moralo biti uspešno.");
        // Preveri še izpis valut
        borza.izpisiValute();
        String rezultat = izpis.toString();
        assertTrue(rezultat.startsWith("Na borzi 'Testna Borza' so naslednje valute (2):"));
        assertTrue(rezultat.contains(" - BTC (Kripto/Blockchain; SHA-256 [BTC00SHA-256]; bitcoin)")); // prva valuta
        assertTrue(rezultat.contains(" - EUR (Fiat/Državna; Evropa; evro)")); // druga valuta
    }

    @Test
    void testPreberiValuteDvojnikInIzpisiValute() throws IOException {
        Path valuteDat = tempDir.resolve("dvojniki.txt");
        // BTC se pojavi dvakrat
        String vsebina = "K;bitcoin;BTC;SHA-256;19000000\nK;bitcoin2;BTC;SHA-256;20000000";
        Files.writeString(valuteDat, vsebina);
        boolean uspeh = borza.preberiValute(valuteDat.toString());
        assertTrue(uspeh, "Branje valut bi moralo biti uspešno.");

        borza.izpisiValute();
        String rezultat = izpis.toString();
        assertTrue(rezultat.startsWith("Na borzi 'Testna Borza' so naslednje valute (1):"));
        assertTrue(rezultat.contains(" - BTC (Kripto/Blockchain; SHA-256 [BTC00SHA-256]; bitcoin)")); // prva valuta
        assertFalse(rezultat.contains("bitcoin2")); // druga valuta
    }

    @Test
    void testPreberiTecajnicoInIzpisiTecajnico() throws IOException {
        // Pripravi seznam valut
        Path valuteDat = tempDir.resolve("valute.txt");
        Files.writeString(valuteDat, "K;bitcoin;BTC;SHA-256;19000000\nF;evro;EUR;Evropa");
        borza.preberiValute(valuteDat.toString());

        // Pripravimo datoteko za tečaje (Format: Izvor Cilj Tecaj Provizija)
        Path tecajiDat = tempDir.resolve("tecaji.txt");
        Files.writeString(tecajiDat, "BTC EUR 45000.5 0.02\nEUR BTC 0.0005 0.01");
        boolean uspeh = borza.preberiTecajnico(tecajiDat.toString());
        assertTrue(uspeh, "Branje tečajnice bi moralo biti uspešno.");

        // Preveri še izpis tečajnice
        borza.izpisiTecajnico();
        String rezultat = izpis.toString();
        assertTrue(rezultat.startsWith("Menjalna razmerja na borzi 'Testna Borza':"));
        assertTrue(rezultat.contains(" - BTC -> EUR : 45000.500000 (provizija: 2.00 %)")); // prvi tečaj
        assertTrue(rezultat.contains(" - EUR -> BTC :    0.000500 (provizija: 1.00 %)")); // drugi tečaj
    }

    @Test
    void testPreberiTecajnicoNeobstojecaValuta() throws IOException {
        // Datoteka s tečajem za valuto, ki je ni na borzi
        Path tecajiDat = tempDir.resolve("neobstojeca.txt");
        Files.writeString(tecajiDat, "NON EXT 1.0 0.01");
        boolean uspeh = borza.preberiTecajnico(tecajiDat.toString());
        // Metoda vrne true (ker datoteka obstaja), a razmerja ne doda, izpis je prazen
        assertTrue(uspeh);
        borza.izpisiTecajnico();
        assertEquals("Menjalna razmerja na borzi 'Testna Borza':", izpis.toString().strip());
    }

    @Test
    void testFileNotFoundValute() {
        // Poskus branja neobstoječe datoteke
        boolean uspeh = borza.preberiValute("ne-obstajam.txt");
        assertFalse(uspeh, "Metoda bi morala vrniti false, če datoteke ni.");
    }

    @Test
    void testFileNotFoundTecaji() {
        // Poskus branja neobstoječe datoteke
        boolean uspeh = borza.preberiTecajnico("ne-obstajam.txt");
        assertFalse(uspeh, "Metoda bi morala vrniti false, če datoteke ni.");
    }

    @Test
    void testIzpisiValutePrazenSeznam() {
        borza.izpisiValute();
        String rezultat = izpis.toString();
        assertTrue(rezultat.startsWith("Na borzi 'Testna Borza' so naslednje valute"));
        assertTrue(rezultat.contains("(0)")); // Ker je seznam prazen
    }

    @Test
    void testIzpisiTecajnicoPrazenSeznam() {
        borza.izpisiTecajnico();
        assertEquals("Menjalna razmerja na borzi 'Testna Borza':", izpis.toString().strip()); // Tečajnica je prazna
    }

}
