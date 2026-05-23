package kodirniki;

public class CezarjevAlgoritem implements Kodirnik {
    private int zamik;

    public CezarjevAlgoritem(int zamik) {
        this.zamik = zamik;
    }

    @Override
    public int zakodiraj(int vrednost) {
        return zamik + vrednost;
    }

    @Override
    public int odkodiraj(int vrednost) {
        return vrednost - zamik;
    }

    @Override
    public void ponastavi() {};
}
