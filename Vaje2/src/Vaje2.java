public class Vaje2 {
    public static void main(String[] args) {
        System.out.printf("%3c%16s%23s%16s\n", 'n', "n!", "Stirling(n)", "napaka (%)");
        System.out.println("----------------------------------------------------------");
        for (int i = 1; i <= 20; i++) {
            izpisNapakeL(fakultetaL(i), stirlingL(i), i);
        }

        // izpis je dober do vključno števila 20, število 21 je že napaka premalo bitov za zapis rezultata

        System.out.printf("\n%3c%11s%23s%16s\n", 'n', "n!", "Stirling(n)", "napaka (%)");
        System.out.println("----------------------------------------------------");
        for (int i = 1; i <= 100; i++) {
            izpisNapakeD(fakultetaD(i), stirlingD(i), i);
        }

        System.out.printf("\n%3c%11s%28s%12s\n", 'k', "Math.PI", "PI (Nilakantha)", "razlika");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 1; i <= 22; i++) {
            izpisPiNilakantha(izracunajPiNilakantha(i), i);
        }
    }

    public static long fakultetaL(int n) {
        long fakulteta = 1;
        for (int i = 1; i <= n; i++) {
            fakulteta *= i;
        }
        return fakulteta;
    }

    public static long stirlingL(int n) {
        return Math.round(Math.sqrt(2 * Math.PI * n) * Math.pow((n / Math.E), n));
    }

    public static void izpisNapakeL(long f1, long f2, int n) {
        System.out.printf("%3d", n);
        System.out.printf("%21d", f1);
        System.out.printf("%21d", f2);
        System.out.printf("%12.7f",  ((double)(f1 - f2) / f1) * 100);
        System.out.println();
    }


    public static double fakultetaD(int n) {
        double fakulteta = 1;
        for (int i = 1; i <= n; i++) {
            fakulteta *= i;
        }
        return fakulteta;
    }

    public static double stirlingD(int n) {
        return Math.sqrt(2 * Math.PI * n) * Math.pow((n / Math.E), n);
    }

    public static void izpisNapakeD(double f1, double f2, int n) {
        System.out.printf("%3d", n);
        System.out.printf("%18.9e", f1);
        System.out.printf("%18.9e", f2);
        System.out.printf("%12.7f"  , ((f1 - f2) / f1) * 100);
        System.out.println();
    }


    public static double izracunajPiNilakantha(int k) {
        double pi = 3;
        for (int i = 1; i < k; i++) {
            pi = pi + ((double) 4 / ((2 * i) * (2 * i + 1) * (2 * i + 2))) * (i % 2 == 1 ? 1 : -1);
        }
        return pi;
    }

    public static void izpisPiNilakantha(double PiNilakantha, int k) {
        System.out.printf("%3d", k);
        System.out.printf("%20.15f", Math.PI);
        System.out.printf("%20.15f", PiNilakantha);
        System.out.printf("%+21.15f", Math.PI - PiNilakantha);
        System.out.println();
    }
}