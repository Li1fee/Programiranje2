import java.io.File;
import java.util.Set;

public class CistiRacuni {
    static void main(String[] args) {
        Dolgovi dolgovi = new Dolgovi();
        dolgovi.preberiPrijatelje(new File("prijatelji.txt"));
        dolgovi.preberiDolgove(new File("dolgovi.txt"));
        dolgovi.izpisi();

        Set<Prijatelj> brezDolgov = dolgovi.vrniBrezDolga();
        System.out.println(brezDolgov.isEmpty() ? "Vsi prijatelji imajo dolgove." : "Prijatelji, ki nimajo dolgov:");

        for (Prijatelj p : brezDolgov) {
            System.out.println(p.toString());
        }

        System.out.println();
    }
}
