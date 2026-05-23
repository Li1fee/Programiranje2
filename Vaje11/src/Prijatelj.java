import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Prijatelj implements Comparable<Prijatelj> {
    private String ime;
    private int id;
    private static int naslednjiId = 1;
    private Map<Prijatelj, Double> dolguje;

    public Prijatelj(String ime) {
        this.ime = ime;
        this.id = naslednjiId;
        naslednjiId++;
        dolguje = new TreeMap<>();
    }

    public String getIme() {
        return ime;
    }

    public Map<Prijatelj, Double> getDolguje() {
        return dolguje;
    }

    public void dodajDolg(Prijatelj p, double znesek) {
        dolguje.put(p, dolguje.getOrDefault(p, 0.0) + znesek);
    }

    public boolean dolgujeDenar() {
        return dolguje.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("[%03d] %s", id, ime);
    }

    @Override
    public int compareTo(Prijatelj p) {
        return this.ime.compareTo(p.ime);
    }
}
