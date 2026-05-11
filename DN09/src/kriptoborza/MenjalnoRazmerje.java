package kriptoborza;

public class MenjalnoRazmerje {
    private Valuta izvornaValuta;
    private Valuta ciljnaValuta;
    private double tecaj;
    private double provizija;


    public MenjalnoRazmerje(Valuta izvornaValuta, Valuta ciljnaValuta, double tecaj, double provizija) {
        this.izvornaValuta = izvornaValuta;
        this.ciljnaValuta = ciljnaValuta;
        this.tecaj = tecaj;
        this.provizija = provizija;
    }

    @Override
    public String toString() {
        return String.format("%s -> %s : %11.6f (provizija: %.2f %%)",
                izvornaValuta.getOznaka(),
                ciljnaValuta.getOznaka(),
                tecaj,
                provizija * 100);
    }

    public double pretvori(double kolicina) {
        double pretvorba = kolicina * tecaj;
        return pretvorba - (pretvorba * provizija);
    }

    public String vrniMenjalniPar() {
        return String.format("%s:%s", izvornaValuta.getOznaka(), ciljnaValuta.getOznaka());
    }
}
