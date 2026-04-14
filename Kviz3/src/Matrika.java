public class Matrika {
    int[][] matrika;

    public Matrika(int[][] matrika2D) {
        this.matrika = matrika2D;
    }

    static Matrika preberiMatriko(String imeDatoteke) {
        java.util.Scanner sc = null;
        try {
            sc = new java.util.Scanner(new java.io.File(imeDatoteke));
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int dolzinaMatrike = sc.nextInt();
        int[][] matrika2D = new int[dolzinaMatrike][];

        for (int i = 0; i < dolzinaMatrike; i++) {
            int[] vrsticaMatrike = new int[dolzinaMatrike];
            for (int j = 0; j < dolzinaMatrike; j++) {
                vrsticaMatrike[j] = sc.nextInt();
            }
            matrika2D[i] = vrsticaMatrike;
        }

        sc.close();
        return new Matrika(matrika2D);
    }

    public Matrika zmnozi(Matrika b) {
        int[][] zmnozenaMatrika = new int[this.matrika.length][b.matrika[0].length];

        if (this.matrika[0].length != b.matrika.length) {
            System.out.println("Tabeli nemoremo zmnožiti!");
            return null;
        }

        for (int i = 0; i < this.matrika.length; i++) {
            for (int j = 0; j < this.matrika[i].length; j++) {
                for (int k = 0; k < b.matrika[j].length; k++) {
                    zmnozenaMatrika[i][k] += this.matrika[i][j] * b.matrika[j][k];
                }
            }
        }

        return new Matrika(zmnozenaMatrika);
    }

    public void izpisi() {
        for (int[] ints : this.matrika) {
            for (int n : ints) {
                System.out.printf("%3d", n);
            }
            System.out.println();
        }
    }
}
