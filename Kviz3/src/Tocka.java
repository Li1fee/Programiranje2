public class Tocka {
    int x;
    int y;

    public Tocka(int x, int y) {
        this.x = x;
        this.y = y;
    }

    static Tocka[] preberiTocke(String imeDatoteke) {
        java.util.ArrayList<Tocka> seznamTock = new java.util.ArrayList<>();
        java.util.Scanner sc = null;
        try {
            sc = new java.util.Scanner(new java.io.File(imeDatoteke));
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (sc.hasNextInt()) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            seznamTock.add(new Tocka(x, y));
        }
        sc.close();
        return seznamTock.toArray(new Tocka[0]);
    }

    public static String tabelaToString(Tocka[] tocke) {
        StringBuilder tabelaVNiz = new StringBuilder("[");
        for (int i = 0; i < tocke.length; i++) {
            tabelaVNiz.append("(")
                    .append(tocke[i].x)
                    .append(",")
                    .append(tocke[i].y)
                    .append(")");

            if (i < tocke.length - 1) {
                tabelaVNiz.append(", ");
            }
        }
        return tabelaVNiz.append("]").toString();
    }

    public static void najblizji(Tocka[] t1, Tocka[] t2) {
        if (t1.length == 0) {
            System.out.println("Prva tabela ne vsebuje točk");
            return;
        } else if (t2.length == 0) {
            System.out.println("Druga tabela ne vsebuje točk");
            return;
        }
        double najblizjaRazdalja = Math.sqrt(Math.pow(t2[0].x - t1[0].x, 2) + Math.pow(t2[0].y - t1[0].y, 2));
        int[] tockeIndex = new int[]{0, 0};

        for (int i = 0; i < t1.length; i++) {
            for (int j = 0; j < t2.length; j++) {
                double razdaljaMedTockama = Math.sqrt(Math.pow(t2[j].x - t1[i].x, 2) + Math.pow(t2[j].y - t1[i].y, 2));
                if (razdaljaMedTockama < najblizjaRazdalja) {
                    najblizjaRazdalja = razdaljaMedTockama;
                    tockeIndex = new int[]{i, j};
                }
            }
        }
        System.out.printf("Najbližji točki sta Tocka (%d, %d) in Tocka (%d, %d), razdalja med njima je %.2f", t1[tockeIndex[0]].x,t1[tockeIndex[0]].y, t2[tockeIndex[1]].x, t2[tockeIndex[1]].y ,najblizjaRazdalja);
    }
}
