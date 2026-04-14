public class Elipsa {
    private double x;
    private double y;
    private double malaPolos;
    private double velikaPolos;

    public Elipsa(double x, double y, double malaPolos, double velikaPolos) {
        this.x = x;
        this.y = y;
        this.malaPolos = malaPolos;
        this.velikaPolos = velikaPolos;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMalaPolos() {
        return malaPolos;
    }

    public double getVelikaPolos() {
        return velikaPolos;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setMalaPolos(double malaPolos) {
        this.malaPolos = malaPolos;
    }

    public void setVelikaPolos(double velikaPolos) {
        this.velikaPolos = velikaPolos;
    }

    public double getPovrsina() {
        return Math.PI * this.malaPolos * this.velikaPolos;
    }

    public double getObseg() {
        double a = this.velikaPolos;
        double b = this.malaPolos;
        return Math.PI * (3 * (a + b) - Math.sqrt((3 * a + b) * (a + 3 * b)));
    }

    public String toString() {
        return String.format("Ellipse [x=%s, y=%s, malaPolos=%s, velikaPolos=%s]", this.x, this.y, this.malaPolos, this.velikaPolos);
    }
}
