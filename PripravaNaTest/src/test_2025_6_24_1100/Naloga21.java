package test_2025_6_24_1100;

public class Naloga21 {
    public static void main(String[] args) {
        if (args.length != 2) return;
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        int[][] kvadrat = new Naloga21().ustvariKvadrat(n);
        new Naloga21().izpisiKvadrat(kvadrat, m);
    }

    int[][] ustvariKvadrat(int n) {
        int[][] kvadrat = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= n; j++) {
                kvadrat[i][j - 1] = j + i * n;
            }
        }
        return kvadrat;
    }

    void izpisiKvadrat(int[] [] kvadrat, int m) {
        for (int i = 0; i < kvadrat.length; i++) {
            for (int j = 1; j <= kvadrat.length; j++) {
                if (i == kvadrat.length - 2 && i >= m) {
                    System.out.printf(String.format("%4s", "*").repeat(m + 2) + "\n");
                    break;
                }
                else if (i == kvadrat.length - 1 && (j <= m || j == kvadrat.length)) {
                    if (j == kvadrat.length && j > m) {
                        System.out.printf("%4s", "*");
                    }
                    System.out.printf("%4s", kvadrat[i][j - 1]);
                }
                else if ((j <= m || j == kvadrat.length) && i < m) {
                    System.out.printf("%4s", kvadrat[i][j - 1]);
                    if (j == kvadrat.length) {
                        System.out.println();
                    }
                }
                else if (j == kvadrat.length - 1 && i < m) {
                    System.out.printf("%4s", "*");
                }
            }
        }
    }

}
