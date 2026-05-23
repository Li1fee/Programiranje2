import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Dolgovi {
    ArrayList<Prijatelj> seznamPrijateljev = new ArrayList<>();
    private int i = 1;

    public void preberiPrijatelje(File datoteka) {
        try (Scanner sc = new Scanner(datoteka)) {
            while (sc.hasNextLine()) {
                seznamPrijateljev.add(new Prijatelj(sc.nextLine()));
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }

    public void izpisi() {
        for (Prijatelj prijatelj : seznamPrijateljev) {
            Map<Prijatelj, Double> dolgi = prijatelj.getDolguje();

            System.out.printf("%s %s\n", prijatelj.toString(), !dolgi.isEmpty() ? "ima dolgove do naslednjih prijateljev:" : "nima dolgov.");
            for (Map.Entry<Prijatelj, Double> dolg : dolgi.entrySet()) {
                System.out.printf("   --> %s (%.2f EUR)\n", dolg.getKey().toString(), dolg.getValue());
            }
            System.out.println();
        }
    }

    private Prijatelj vrniOsebo(String ime) {
        for (Prijatelj prijatelj : seznamPrijateljev) {
            if (prijatelj.getIme().equals(ime)) {
                return prijatelj;
            }
        }
        return null;
    }

    public void preberiDolgove(File datoteka) {
        try (Scanner sc = new Scanner(datoteka)) {
            while (sc.hasNextLine()) {
                String[] line = sc.nextLine().split(";");
                Prijatelj ime1 = vrniOsebo(line[0]);
                Prijatelj ime2 = vrniOsebo(line[1]);
                if (line.length != 3 || ime1 == null || ime2 == null) continue;
                double znesek = Double.parseDouble(line[2]);
                if (znesek >= 0) {
                    ime1.dodajDolg(ime2, znesek);
                } else {
                    ime2.dodajDolg(ime1, znesek * -1);
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }

    public Set<Prijatelj> vrniBrezDolga() {
        Set<Prijatelj> seznamBrezDolga = new TreeSet<>();
        for (Prijatelj prijatelj : seznamPrijateljev) {
            if (prijatelj.dolgujeDenar())
                seznamBrezDolga.add(prijatelj);
        }
        return seznamBrezDolga;
    }

}
