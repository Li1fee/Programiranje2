import java.util.Random;

public class Kaca {

    private static Random random = new Random();

    private static final int VISINA = 20;
    private static final int SIRINA = 32;
    private static final int SIRINA_KVADRATKA = 30;

    private static int[][] kaca;
    private static int xGlava;
    private static int yGlava;
    private static int dolzina;

    private static int[][] hrana = new int[5][2];
    private static int[][] strup = new int[5][2];

    private static boolean igranje = true;
    private static String konec = "KONEC IGRE!";
    private static int zamik = 10;

    private static final int GOR = 0;
    private static final int DESNO = 1;
    private static final int DOL = 2;
    private static final int LEVO = 3;

    private static int smer = DESNO;
    private static int naslednjaSmer = DESNO;

    private static int tocke = 0;

    private static final int TIPKA_LEVO = 37;
    private static final int TIPKA_GOR = 38;
    private static final int TIPKA_DESNO = 39;
    private static final int TIPKA_DOL = 40;

    private static int[] zadnjiDelKace;

    public static void main(String[] args) {
        novaIgra();

        int i = 0;
        while (igranje) {
            spremeniSmer();

            if (i++ % 10 == 0) {
                premakni();
                prikazi();
            }

            StdDraw.pause(zamik);
        }
        while (dolzina > 0) {
            dolzina--;
            prikazi();
            StdDraw.pause(2 * zamik);
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(StdDraw.getFont().deriveFont(40f));
        StdDraw.text(SIRINA / 2.0, VISINA / 2.0, konec);
        StdDraw.text(SIRINA / 2.0, (VISINA / 2.0) - 1, "Dosežene točke: " + tocke);
    }

    private static void novaIgra() {
        StdDraw.setCanvasSize(SIRINA * SIRINA_KVADRATKA, VISINA * SIRINA_KVADRATKA);
        StdDraw.setXscale(0, SIRINA);
        StdDraw.setYscale(0, VISINA);

        for (int i = 0; i < SIRINA * VISINA; i++) {
            int x = i % SIRINA;
            int y = i / SIRINA;
            StdDraw.setPenColor(((x + y) % 2 == 0) ? StdDraw.BOOK_BLUE : StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledRectangle(x + 0.5, y + 0.5, 0.5, 0.5);
        }

        kaca = new int[SIRINA * VISINA][2];
        dolzina = 3;
        smer = DESNO;
        naslednjaSmer = DESNO;
        igranje = true;
        tocke = 0;

        xGlava = SIRINA / 2;
        yGlava = VISINA / 2;

        kaca[0][0] = xGlava;        kaca[0][1] = yGlava;
        kaca[1][0] = xGlava - 1;    kaca[1][1] = yGlava;
        kaca[2][0] = xGlava - 2;    kaca[2][1] = yGlava;

        zadnjiDelKace = new int[]{xGlava - 2, yGlava};

        for (int i = 0; i < hrana.length; i++) {
            nastaviHrano(i);
            nastaviStrup(i);
        }
    }

    private static void prikazi() {
        StdDraw.setPenColor((zadnjiDelKace[0] + zadnjiDelKace[1]) % 2 == 0 ? StdDraw.BOOK_BLUE : StdDraw.BOOK_LIGHT_BLUE);
        StdDraw.filledRectangle(zadnjiDelKace[0] + 0.5, zadnjiDelKace[1] + 0.5, 0.5, 0.5);

        for (int i = 0; i < hrana.length; i++) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(hrana[i][0] + 0.5, hrana[i][1] + 0.5, 0.4);
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledCircle(strup[i][0] + 0.5, strup[i][1] + 0.5, 0.4);
        }

        StdDraw.setPenColor(StdDraw.GRAY);
        for (int i = 1; i < dolzina; i++) {
            StdDraw.filledRectangle(kaca[i][0] + 0.5, kaca[i][1] + 0.5, 0.5, 0.5);
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledRectangle(kaca[0][0] + 0.5, kaca[0][1] + 0.5, 0.5, 0.5);
    }

    private static void premakni() {
        smer = naslednjaSmer;

        zadnjiDelKace[0] = kaca[dolzina - 1][0];
        zadnjiDelKace[1] = kaca[dolzina - 1][1];

        int novaGlavaX = kaca[0][0];
        int novaGlavaY = kaca[0][1];

        if (smer == DESNO) novaGlavaX++;
        else if (smer == LEVO) novaGlavaX--;
        else if (smer == GOR) novaGlavaY++;
        else if (smer == DOL) novaGlavaY--;

        if (novaGlavaX < 0 || novaGlavaX >= SIRINA || novaGlavaY < 0 || novaGlavaY >= VISINA) {
            igranje = false;
            return;
        }

        if (vKaci(novaGlavaX, novaGlavaY) || naStrupu(novaGlavaX, novaGlavaY)) {
            igranje = false;
            return;
        }

        boolean pojedel = false;
        for (int i = 0; i < hrana.length; i++) {
            if (novaGlavaX == hrana[i][0] && novaGlavaY == hrana[i][1]) {
                dolzina++;
                tocke++;
                System.out.println("Tocke: " + tocke);
                nastaviHrano(i);
                pojedel = true;
                break;
            }
        }

        for (int i = dolzina - 1; i > 0; i--) {
            kaca[i][0] = kaca[i - 1][0];
            kaca[i][1] = kaca[i - 1][1];
        }

        kaca[0][0] = novaGlavaX;
        kaca[0][1] = novaGlavaY;

        if (naStrupuRep()) {
            igranje = false;
        }
    }

    private static void spremeniSmer() {
        if (StdDraw.isKeyPressed(TIPKA_GOR) && smer != DOL) naslednjaSmer = GOR;
        else if (StdDraw.isKeyPressed(TIPKA_DOL) && smer != GOR) naslednjaSmer = DOL;
        else if (StdDraw.isKeyPressed(TIPKA_LEVO) && smer != DESNO) naslednjaSmer = LEVO;
        else if (StdDraw.isKeyPressed(TIPKA_DESNO) && smer != LEVO) naslednjaSmer = DESNO;
    }

    private static void nastaviHrano(int i) {
        int x, y;
        do {
            x = random.nextInt(SIRINA);
            y = random.nextInt(VISINA);
        } while (vKaci(x, y) || naStrupu(x, y));
        hrana[i][0] = x;
        hrana[i][1] = y;
    }

    private static void nastaviStrup(int i) {
        int x, y;
        do {
            x = random.nextInt(SIRINA);
            y = random.nextInt(VISINA);
        } while (vKaci(x, y) || naHrani(x, y));
        strup[i][0] = x;
        strup[i][1] = y;
    }

    private static boolean vKaci(int x, int y) {
        for (int i = 0; i < dolzina; i++) {
            if (kaca[i][0] == x && kaca[i][1] == y) return true;
        }
        return false;
    }

    private static boolean naStrupu(int x, int y) {
        for (int i = 0; i < strup.length; i++) {
            if (strup[i][0] == x && strup[i][1] == y) return true;
        }
        return false;
    }

    private static boolean naStrupuRep() {
        for (int i = 0; i < dolzina; i++) {
            if (naStrupu(kaca[i][0], kaca[i][1])) return true;
        }
        return false;
    }

    private static boolean naHrani(int x, int y) {
        for (int i = 0; i < hrana.length; i++) {
            if (hrana[i][0] == x && hrana[i][1] == y) return true;
        }
        return false;
    }
}