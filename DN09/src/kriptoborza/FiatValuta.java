package kriptoborza;

public class FiatValuta extends Valuta {
    private String drzavaIzdajateljica;

    public FiatValuta(String naziv, String oznaka, String drzavaIzdajateljica) {
        super(naziv, oznaka);
        this.drzavaIzdajateljica = drzavaIzdajateljica;
    }

    @Override
    public String pridobiTip() {
        return String.format("Fiat/Državna; %s", drzavaIzdajateljica);
    }
}
