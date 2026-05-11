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

public class DN09Test01b {
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
    void testBranjeInIzpisValut() throws Exception {
        boolean rezultat = borza.preberiValute("test/resources/valute.txt");
        assertTrue(rezultat);

        borza.izpisiValute();
        String dejanskiIzpis = izpis.toString();
        String pricakovaniIzpis = Files.readString(Path.of("test/resources/pricakovani-izpis-valut.txt"));
        //assertEquals(pricakovaniIzpis, dejanskiIzpis);
        assertLinesMatch(pricakovaniIzpis.lines(), dejanskiIzpis.lines());
    }

    @Test
    void testBranjeInIzpisTecajev() throws Exception {
        borza.preberiValute("test/resources/valute.txt");
        boolean rezultat = borza.preberiTecajnico("test/resources/tecaji.txt");
        assertTrue(rezultat);

        borza.izpisiTecajnico();
        String dejanskiIzpis = izpis.toString();
        String pricakovaniIzpis = Files.readString(Path.of("test/resources/pricakovani-izpis-tecajev.txt"));
        //assertEquals(pricakovaniIzpis, dejanskiIzpis);
        assertLinesMatch(pricakovaniIzpis.lines(), dejanskiIzpis.lines());
    }

}
