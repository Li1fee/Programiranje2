package test_2020.drugi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Naloga24 {
    public static void main(String[] args) {
        if (args.length != 1) return;

        ArrayList<Povezava> vsePovezave = preberiPovezave(args[0]);

        System.out.println(poisciPot(vsePovezave, "0", 0));
    }

    private static ArrayList<Povezava> preberiPovezave(String imeDatoteke) {
        ArrayList<Povezava> vsePovezave = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            while (sc.hasNext()) {
                vsePovezave.add(
                        new Povezava(sc.next(), sc.next(), sc.nextInt())
                );
            }
        } catch (FileNotFoundException e) {
            return null;
        }

        return vsePovezave;
    }

    private static List<String> poisciPot(ArrayList<Povezava> povezave, String zacetek, int kako) {
        ArrayList<String> videnePovezave = new ArrayList<>();
        String trenutnoVozlisce = zacetek;

        while (true) {
            if (videnePovezave.contains(trenutnoVozlisce)) {
                videnePovezave.add(trenutnoVozlisce);
                break;
            }

            videnePovezave.add(trenutnoVozlisce);

            Povezava izbranaPovezava = null;

            for(Povezava p : povezave) {
                if (p.getZacetnoVozliscePovezave().equals(trenutnoVozlisce)) {
                    if (izbranaPovezava == null) {
                        izbranaPovezava = p;
                    } else {
                        if (kako == 0 && p.getCenaPovezave() < izbranaPovezava.getCenaPovezave()) {
                            izbranaPovezava = p;
                        } else if (kako == 1 && p.getCenaPovezave() > izbranaPovezava.getCenaPovezave()) {
                            izbranaPovezava = p;
                        }
                    }
                }
            }
            trenutnoVozlisce = izbranaPovezava.getKoncnoVozliscePovezave();
        }
        return videnePovezave;
    }
}

class Povezava {
    private final String zacetnoVozliscePovezave;
    private final String koncnoVozliscePovezave;
    private final int cenaPovezave;

    public Povezava(String zacetnoVozliscePovezave, String koncnoVozliscePovezave, int cenaPovezave) {
        this.zacetnoVozliscePovezave = zacetnoVozliscePovezave;
        this.koncnoVozliscePovezave = koncnoVozliscePovezave;
        this.cenaPovezave = cenaPovezave;
    }

    public int getCenaPovezave() {
        return cenaPovezave;
    }

    public String getKoncnoVozliscePovezave() {
        return koncnoVozliscePovezave;
    }

    public String getZacetnoVozliscePovezave() {
        return zacetnoVozliscePovezave;
    }

    @Override
    public String toString() {
        return Integer.toString(cenaPovezave);
    }
}