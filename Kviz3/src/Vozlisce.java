public class Vozlisce {
    private int vrednost;
    private Vozlisce levo;
    private Vozlisce desno;

    public Vozlisce(int v) {
        this.vrednost = v;
    }

    public Vozlisce() {

    }

    public int vrniVrednost() {
        return vrednost;
    }

    public Vozlisce vrniLevoPoddrevo() {
        return levo;
    }

    public Vozlisce vrniDesnoPoddrevo() {
        return desno;
    }

    public void dodajLevo(Vozlisce v) {
        this.levo = v;
    }

    public void dodajDesno(Vozlisce v) {
        this.desno = v;
    }
}
