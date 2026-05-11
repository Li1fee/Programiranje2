package kriptoborza;

public abstract class Valuta {
    private String naziv;
    private String oznaka;

    public Valuta(String naziv, String oznaka) {
        this.naziv = naziv;
        this.oznaka = oznaka;
    }

    public String getOznaka() {
        return oznaka;
    }

    public String getNaziv() {
        return naziv;
    }

    public abstract String pridobiTip();

    public String toString() {
        return String.format("%s (%s; %s)", oznaka, pridobiTip(), naziv);
    }

}
