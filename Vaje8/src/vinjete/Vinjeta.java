package vinjete;

public class Vinjeta {
    private String registerska;
    private String razred;
    private String zacetekVeljavnosti;
    private String vrsta;

    public Vinjeta(String registerska, String razred, String zacetekVeljavnosti, String vrsta) {
        this.registerska = registerska;
        this.razred = razred;
        this.zacetekVeljavnosti = zacetekVeljavnosti;
        this.vrsta = vrsta;
    }

    public String getRegisterska() {
        return registerska;
    }

    public String getRazred() {
        return razred;
    }

    public String getZacetekVeljavnosti() {
        return zacetekVeljavnosti;
    }

    public String getVrsta() {
        return vrsta;
    }

    public String toString() {
        return String.format("%s [%s]: %s (%s)", registerska, razred, vrsta, zacetekVeljavnosti);
    }
}
