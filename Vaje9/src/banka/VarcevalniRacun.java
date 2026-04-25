package banka;

public class VarcevalniRacun extends Racun {
    private double obresti;

    public VarcevalniRacun(String stevilka, double obresti) {
        super(stevilka);
        this.obresti = obresti;
    }


    void dodajObresti() {
        double trenutnoStanje = getStanje();
        if (trenutnoStanje < 0) {
            setStanje(trenutnoStanje - 1);
        } else {
            setStanje(trenutnoStanje + (trenutnoStanje * obresti));
        }
    }

    String opisRacuna() {
        return String.format("varčevalni z obrestno mero %.2f %%", obresti * 100);
    }
}
