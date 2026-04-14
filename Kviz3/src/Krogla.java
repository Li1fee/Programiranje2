public class Krogla {
    private double polmer;

    public Krogla(double polmer) {
        this.polmer = polmer;
    }

    double getPolmer() {
        return polmer;
    }

    void setPolmer(double polmer) {
        this.polmer = polmer;
    }

    double getPremer() {
        return polmer * 2;
    }

    double getObseg() {
        return 2 * Math.PI * polmer;
    }

    double getPovrsina() {
        return 4 * Math.PI * Math.pow(polmer, 2);
    }

    double getVolumen() {
        return 4.0 / 3.0 * Math.PI * Math.pow(polmer, 3);
    }

    public String toString() {
        return String.format("[ Krogla polmer=%s ]", polmer);
    }
}
