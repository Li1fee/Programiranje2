public class Teserakt {
    private double x;

    public Teserakt(double x) {
        this.x = x;
    }

    double getX() {
        return x;
    }

    void setX(double x) {
        this.x = x;
    }

    double getHiperVolumen() {
        return Math.pow(x, 4);
    }

    double getPovrsinskiVolumen() {
        return 8 * Math.pow(x, 3);
    }

    double getStranicnoDiagonalo() {
        return x * Math.sqrt(2);
    }

    double getTelesnoDiagonalo() {
        return x * Math.sqrt(3);
    }

    double get4DDiagonalo() {
        return 2 * x;
    }

    public String toString() {
        return String.format("[ Tesarakt: x=%s ]", x);
    }
}
