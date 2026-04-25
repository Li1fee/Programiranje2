package banka;

public class TekociRacun extends Racun {
    private double limit;

    public TekociRacun(String stevilka, double limit) {
        super(stevilka);
        this.limit = limit;
    }

    @Override
    public boolean dvig(double znesek) {
        if (znesek > limit) {
           return false;
        }
        return super.dvig(znesek);
    }

    String opisRacuna() {
        return String.format("tekoči z limitom dviga do %.2f EUR", limit);
    }
}
