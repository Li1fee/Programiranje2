package kriptoborza;

public class KriptoValuta extends Valuta {
    private String algoritem;
    private String zgoscenaVrednost;
    private long trenutnaZaloga;

    public KriptoValuta(String naziv, String oznaka, String algoritem, long trenutnaZaloga) {
        super(naziv, oznaka);
        this.algoritem = algoritem;
        this.trenutnaZaloga = trenutnaZaloga;
        this.zgoscenaVrednost = izracunajZgoscenoVrednost();
    }

    @Override
    public String pridobiTip() {
        return String.format("Kripto/Blockchain; %s [%s]", algoritem, zgoscenaVrednost);
    }

    public String getZgoscenaVrednost() {
        return zgoscenaVrednost;
    }

    private String izracunajZgoscenoVrednost() {
        return String.format("%s%02d%s", getOznaka(), trenutnaZaloga % 100, algoritem);
    }

}
