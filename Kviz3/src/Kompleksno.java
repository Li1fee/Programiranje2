public class Kompleksno {
    String ime;
    double realnoStevilo;
    double imaginarnoStevilo;

    public Kompleksno(String ime, double realnoStevilo, double imaginarnoStevilo) {
        this.ime = ime;
        this.realnoStevilo = realnoStevilo;
        this.imaginarnoStevilo = imaginarnoStevilo;
    }

    public Kompleksno(double re, double im) {
        this("", re, im);
    }

    public double velikost() {
        return Math.sqrt(Math.pow(this.realnoStevilo, 2) + Math.pow(this.imaginarnoStevilo, 2));
    }

    public void pristej(Kompleksno b) {
        this.realnoStevilo += b.realnoStevilo;
        this.imaginarnoStevilo += b.imaginarnoStevilo;
    }

    public void pomnozi(Kompleksno b) {
        double starRe = this.realnoStevilo;

        this.realnoStevilo = starRe * b.realnoStevilo - this.imaginarnoStevilo * b.imaginarnoStevilo;
        this.imaginarnoStevilo = starRe * b.imaginarnoStevilo + this.imaginarnoStevilo * b.realnoStevilo;
    }

    public String toString() {
        return this.ime + " = (" + String.format("%.3f", this.realnoStevilo) + " + " + String.format("%.3f", this.imaginarnoStevilo) + "*i)";
    }
}
