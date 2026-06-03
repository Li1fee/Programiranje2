package test_2025_12_6_1000;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Naloga13 {

    public static void main(String[] args) {
        HashMap<String, Drzava> drzave = preberiDrzave(args[0]);

        TreeMap<Integer, ArrayList<Drzava>> urejeneDrzave = new TreeMap<>();

        for (Map.Entry<String, Drzava> drzava : drzave.entrySet()) {
            int steviloPrebivalcevMiljon = (int) Math.round(drzava.getValue().getSteviloPrebivalcev() / Math.pow(10, 6));
            if (!urejeneDrzave.containsKey(steviloPrebivalcevMiljon)) {
                urejeneDrzave.put(steviloPrebivalcevMiljon, new ArrayList<>());
            }

            urejeneDrzave.get(steviloPrebivalcevMiljon).add(drzava.getValue());
        }

        for (Map.Entry<Integer, ArrayList<Drzava>> urejenaDrzava : urejeneDrzave.entrySet()) {
            System.out.println(urejenaDrzava.getKey() + "M");
            Collections.sort(urejenaDrzava.getValue());
            for (Drzava drzava : urejenaDrzava.getValue()) {
                System.out.printf(" - %s (%s), %d\n", drzava.getIme(), drzava.getGlavnoMesto(), drzava.getSteviloPrebivalcev());
            }
        }
    }

    private static HashMap<String, Drzava> preberiDrzave(String imeDatoteke) {
        HashMap<String, Drzava> drzave = new HashMap<>();

        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(":");
                Drzava drzava = new Drzava(line[0], line[1], Integer.parseInt(line[2]));
                drzave.put(line[0], drzava);
            }
        } catch (FileNotFoundException e) {
            return null;
        }
        return drzave;
    }

}

class Drzava implements Comparable<Drzava> {
    private String ime;
    private String glavnoMesto;
    private int steviloPrebivalcev;

    public Drzava(String glavnoMesto, String ime, int steviloPrebivalcev) {
        this.glavnoMesto = glavnoMesto;
        this.ime = ime;
        this.steviloPrebivalcev = steviloPrebivalcev;
    }

    public String getIme() {
        return ime;
    }

    public String getGlavnoMesto() {
        return glavnoMesto;
    }

    public int getSteviloPrebivalcev() {
        return steviloPrebivalcev;
    }

    @Override
    public int compareTo(Drzava druga) {
        return this.glavnoMesto.compareTo(druga.glavnoMesto);
    }
}