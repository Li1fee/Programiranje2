package preiskava;

import kriptoborza.Borza;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class DN09Test02b {
    private final ByteArrayOutputStream izpis = new ByteArrayOutputStream();
    private final PrintStream stdOut = System.out;

    private Borza borza;

    @BeforeAll
    static void setupAll() {
        Locale.setDefault(Locale.of("en", "US"));
    }

    @BeforeEach
    void setUp() {
        // Preusmerimo System.out v naš tok izpis
        System.setOut(new PrintStream(izpis));
        borza = new Borza("Temna borza");
    }

    @AfterEach
    void tearDown() {
        // Vrnemo System.out v prvotno stanje
        System.setOut(stdOut);
    }

    @Test
    void testBranjeInIzpisTransakcij() throws Exception{
        boolean rezultat = borza.preberiTransakcije("test/resources/transakcije.txt");
        assertTrue(rezultat);

        borza.izpisiTransakcije();
        String dejanskiIzpis = izpis.toString();
        String pricakovaniIzpis = Files.readString(Path.of("test/resources/pricakovani-izpis-transakcij.txt"));
        //assertEquals(pricakovaniIzpis, dejanskiIzpis);
        assertLinesMatch(pricakovaniIzpis.lines(), dejanskiIzpis.lines());
    }
}
