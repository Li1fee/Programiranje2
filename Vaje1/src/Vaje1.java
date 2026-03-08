public class Vaje1 {
    public static void main(String[] args) {
        pravokotnikStevilVrstice(7,3);
        System.out.println();
        pravokotnikStevilStolpci(15,3);
        System.out.println();
        pravokotnik(5,7,3);
        System.out.println();
        trikotnikStevilVrstice(3);
        System.out.println();
        trikotnikStevilStolpci(3);
        System.out.println();
        trikotnikStevilVrsticeObrnjen(3);
        System.out.println();
        trikotnikStevilStolpciObrnjen(3);
        System.out.println();
        trikotnikStevil(6);
        System.out.println();
        trikotnik(1,5);
        System.out.println();
        trikotnikObrnjen(1,5);
        System.out.println();
        romb(2,5);
        System.out.println();
        smreka(5);
        System.out.println();
        rombPrazen(3,5);
        System.out.println();
        iks(6);
    }

    public static void pravokotnikStevilVrstice(int sirina, int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.println(String.valueOf(i % 10).repeat(sirina));
        }
    }

    public static void pravokotnikStevilStolpci(int sirina, int visina) {
        for (int i = 0; i < visina; i++) {
            for (int j = 1; j <= sirina; j++) {
                System.out.print(String.valueOf(j % 10));
            }
            System.out.println();
        }
    }

    public static void pravokotnik(int odmik, int sirina, int visina) {
        for (int i = 0; i < visina; i++) {
            System.out.printf("%" + String.valueOf(odmik + sirina) + "s\n", ("x").repeat(sirina));
        }
    }

    public static void trikotnikStevilVrstice(int visina) {
        for (int i = 1; i <= visina; i++) {
            System.out.println(String.valueOf(i % 10).repeat(i));
        }
    }

    public static void trikotnikStevilStolpci(int visina) {
        for (int i = 1; i <= visina; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(String.valueOf(j % 10));
            }
            System.out.println();
        }
    }

    public static void trikotnikStevilVrsticeObrnjen(int visina) {
        for (int i = visina; i > 0; i--) {
            System.out.println(String.valueOf(i % 10).repeat(i));
        }
    }

    public static void trikotnikStevilStolpciObrnjen(int visina) {
        for (int i = 0; i < visina; i++) {
            for (int j = 1; j <= visina - i; j++) {
                System.out.print(String.valueOf(j % 10));
            }
            System.out.println();
        }
    }

    public static void trikotnikStevil(int visina) {
        for (int i = 0; i < visina; i++) {
            System.out.print((" ").repeat(visina - i - 1));
            for (int j = 1; j <= 2 * i + 1; j++) {
                System.out.print(String.valueOf(j % 10));
            }
            System.out.println();
        }
    }

    public static void trikotnik(int odmik, int visina) {
        for (int i = 0; i < visina; i++) {
            System.out.print((" ").repeat(visina - i - 1 + odmik));
            System.out.print(("*").repeat(i * 2 + 1));
            System.out.println();
        }
    }

    public static void trikotnikObrnjen(int odmik, int visina) {
        for (int i = 0; i < visina; i++) {
            System.out.print((" ".repeat(odmik + i)));
            System.out.print(("*").repeat(visina * 2 - i * 2 - 1));
            System.out.println();
        }
    }

    public static void romb(int odmik, int velikost) {
        trikotnik(odmik, velikost);
        trikotnikObrnjen(odmik + 1, velikost - 1);
    }

    public static void smreka(int v) {
        for (int i = 1; i <= v; i++) {
            trikotnik((v - i) * 2, i * 2);
        }
        pravokotnik((4 * v - 1 - (v % 2 == 0 ? v + 1 : v)) / 2, v % 2 == 0 ? v + 1 : v, v * 2);
    }

    public static void rombPrazen(int odmik, int velikost) {
        for (int i = 0; i < velikost; i++) {
            int presledek = i == 0 ? 0 : (i * 2 - 1) * 2;
            System.out.print((" ").repeat(odmik));
            System.out.print(("# ").repeat(velikost - i));
            System.out.print((" ").repeat(presledek));
            System.out.print(("# ").repeat(velikost - i - (i == 0 ? 1 : 0)));
            System.out.println();
        }

        for (int i = 1; i <= velikost - 1; i++) {
            int presledek = i == velikost - 1 ? 0 : ((velikost - i - 1) * 2 - 1) * 2;
            System.out.print((" ").repeat(odmik));
            System.out.print(("# ").repeat(i + 1));
            System.out.print((" ").repeat(presledek));
            System.out.print(("# ").repeat(i == velikost - 1 ? velikost - 1 : i + 1));
            System.out.println();
        }
    }

    public static void iks(int velikost) {
        for (int i = 0; i < velikost * 2 - 1; i++) {
            if (i < velikost - 1) {
                xSquareMaker(i, ((velikost - 1) * 2 - 1) - (i * 2));
            } else if (i >= velikost) {
                xSquareMaker(2 * velikost - 2 - i, ((i - velikost) * 2 + 1)); // (velikost - 1) - (i - velikost) - 1 = 2 * velikost - 2 - i
            } else {
                xSquareMaker(i,0);
            }
        }
    }

    public static void xSquareMaker(int razdaljaPred, int razdaljaVmes) {
        if (razdaljaVmes == 0) {
            for (int i = 0; i < 3; i++) {
                System.out.print((" ").repeat(razdaljaPred * 5));
                System.out.println(("X").repeat(5));
            }
        } else {
            for (int i = 0; i < 3; i++) {
                System.out.print((" ").repeat(razdaljaPred * 5));
                System.out.print(("X").repeat(5));
                System.out.print((" ").repeat(razdaljaVmes * 5));
                System.out.print(("X").repeat(5));
                System.out.println();
            }
        }
    }
}