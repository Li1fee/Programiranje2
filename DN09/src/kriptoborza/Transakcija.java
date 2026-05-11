package kriptoborza;

public class Transakcija {
    private int id;
    private String izvor;
    private String cilj;
    private String valuta;
    private String cas;
    private double znesek;

    public Transakcija(int id, String izvor, String cilj, String valuta, double znesek, String cas) {
        this.id = id;
        this.izvor = izvor;
        this.cilj = cilj;
        this.valuta = valuta;
        this.znesek = znesek;
        this.cas = cas;
    }

    public String getValuta() {
        return valuta;
    }

    public String getIzvor() {
        return izvor;
    }

    public int getId() {
        return id;
    }

    public double getZnesek() {
        return znesek;
    }

    public String getCilj() {
        return cilj;
    }

    @Override
    public String toString() {
        return String.format("[%s] ID: %d | IZVOR: %s | CILJ: %s | ZNESEK: %.2f %s", cas, id, izvor, cilj, znesek, valuta);
    }
}
