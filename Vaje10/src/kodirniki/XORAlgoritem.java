package kodirniki;

public class XORAlgoritem implements Kodirnik {
    private String geslo;
    private int index = 0;

    public XORAlgoritem(String geslo) {
        this.geslo = geslo;
    }

    @Override
    public int zakodiraj(int vrednost) {
        char gesloCrka = geslo.charAt(index);
        index = (index + 1) % geslo.length();
        return vrednost ^ (int) gesloCrka;
    }

    @Override
    public int odkodiraj(int vrednost) {
        return zakodiraj(vrednost);
    }

    @Override
    public void ponastavi() {
        index = 0;
    }
}
