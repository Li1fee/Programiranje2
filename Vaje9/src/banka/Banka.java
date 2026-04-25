package banka;

public class Banka {
    private int steviloRacunov;
    private Racun[] racuni;

    public Banka() {
        this.steviloRacunov = 0;
        this.racuni = new Racun[500];
    }

    private int poisciIndeksRacuna(String stevilka) {
        for (int i = 0; i < steviloRacunov; i++) {
            if (racuni[i].getStevilka().equals(stevilka)) {
                return i;
            }
        }
        return -1;
    }

    public boolean dodajTekociRacun(String stevilka, double limit) {
        if (steviloRacunov >= 500 || poisciIndeksRacuna(stevilka) != -1) {
            return false;
        }
        racuni[steviloRacunov++] = new TekociRacun(stevilka, limit);
        return true;
    }

    public boolean dodajVarcevalniRacun(String stevilka, double obresti) {
        if (steviloRacunov >= 500 || poisciIndeksRacuna(stevilka) != -1) {
            return false;
        }
        racuni[steviloRacunov++] = new VarcevalniRacun(stevilka, obresti);
        return true;
    }

    public void izpisiRacune() {
        for (int i = 0; i < steviloRacunov; i++) {
            System.out.println(racuni[i].toString());
        }
        System.out.println("Skupaj izpisanih računov: " + steviloRacunov);
    }

    public void izpisiRacune(boolean varcevalni) {
        int stevec = 0;
        for (int i = 0; i < steviloRacunov; i++) {
            Racun r = racuni[i];
            if (varcevalni && r instanceof VarcevalniRacun) {
                System.out.println(r.toString());
                stevec++;
            } else if (!varcevalni && r instanceof TekociRacun) {
                System.out.println(r.toString());
                stevec++;
            }
        }
        System.out.println("Skupaj izpisanih računov: " + stevec);
    }

    public boolean dvig(String stevilka, double znesek) {
        int indeks = poisciIndeksRacuna(stevilka);
        if (indeks != -1) {
            return racuni[indeks].dvig(znesek);
        }
        return false;
    }

    public boolean polog(String stevilka, double znesek) {
        int indeks = poisciIndeksRacuna(stevilka);
        if (indeks != -1) {
            return racuni[indeks].polog(znesek);
        }
        return false;
    }

    public void dodajObresti() {
        for (int i = 0; i < steviloRacunov; i++) {
            if (racuni[i] instanceof VarcevalniRacun) {
                // Ker vemo, da je to VarcevalniRacun, ga "pretipamo" (cast),
                // da lahko pokličemo metodo dodajObresti(), ki obstaja samo tam.
                ((VarcevalniRacun) racuni[i]).dodajObresti();
            }
        }
    }

    public void sortirajRacune(String polje, boolean padajoce) {
        for (int i = 0; i < steviloRacunov - 1; i++) {
            for (int j = 0; j < steviloRacunov - i - 1; j++) {
                boolean zamenjava = false;


                if (polje.equals("stevilka")) {
                    int primerjava = racuni[j].getStevilka().compareTo(racuni[j +1].getStevilka());

                    if (padajoce) {
                        if (primerjava < 0) zamenjava = true;
                    } else {
                        if (primerjava > 0) zamenjava = true;
                    }

                } else if (polje.equals("stanje")) {
                    double s1 = racuni[j].getStanje();
                    double s2 = racuni[j + 1].getStanje();

                    if (padajoce) {
                        if (s1 < s2) zamenjava = true;
                    } else {
                        if (s1 > s2) zamenjava = true;
                    }
                }

                if (zamenjava) {
                    Racun zacasni = racuni[j];
                    racuni[j] = racuni[j + 1];
                    racuni[j + 1] = zacasni;
                }
            }
        }
    }
}
