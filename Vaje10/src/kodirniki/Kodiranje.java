package kodirniki;

public class Kodiranje {
    Kodirnik kodirnik;

    public Kodiranje(Kodirnik kodirnik) {
        this.kodirnik = kodirnik;
    }

    public String zakodiranjeBesedila(String besedilo) {
        StringBuilder zakodiranoBesedilo = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            int zakodiranaCrka = kodirnik.zakodiraj((int) c);
            zakodiranoBesedilo.append((char) zakodiranaCrka);
        }

        return zakodiranoBesedilo.toString();
    }

    public String odkodiranjeBesedila(String besedilo) {
        StringBuilder odkodiranoBesedilo = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            int odkodiranaCrka = kodirnik.odkodiraj((int) c);
            odkodiranoBesedilo.append((char) odkodiranaCrka);
        }

        return odkodiranoBesedilo.toString();
    }
}
