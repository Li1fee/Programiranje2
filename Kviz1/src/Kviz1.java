import java.util.ArrayList;

public class Kviz1 {
    public static void main(String[] args) {
        java();
        kalkulator(42, 13);
        nicli(1,2,2);
        krog(7.5,3);
        System.out.println(pretvoriSekunde(65));
        javaJavaJava(3);
        System.out.println(jeFibonaccijevo(13));
        System.out.println(jePrastevilo(42));
        izrisiZastavo(2);
        vDesetisko(120);
        System.out.println(pretvoriVDesetisko("FF", 2));
        System.out.println(vsotaPrvih(10));
        pitagoroviTrojcki(25);
        stik(new int[]{3,6,9,1,3,5}, new int[]{3,4,6,1,3,8});
        presek(new int[]{3,6,9,1,3,5}, new int[]{3,4,6,1,3,8});
        System.out.println(izracunajRazliko("08:23:10", "10:10:05"));
        praDvojcek(10);
        trikotnik(5,7);
        metulj(5,3);
        veckratnikDelitelj(12,-24);
    }

    public static void java() {
        System.out.println("   J    a   v     v  a");
        System.out.println("   J   a a   v   v  a a");
        System.out.println("J  J  aaaaa   V V  aaaaa");
        System.out.println(" JJ  a     a   V  a     a");
    }

    public static void kalkulator(int a, int b) {
        if (b == 0) {
            System.out.println("Napaka: deljenje z 0");
            return;
        }
        System.out.printf("%d + %d = %d\n", a, b, a + b);
        System.out.printf("%d - %d = %d\n", a, b, a - b);
        System.out.printf("%d x %d = %d\n", a, b, a * b);
        System.out.printf("%d / %d = %d\n", a, b, a / b);
        System.out.printf("%d %% %d = %d\n", a, b, a % b);
    }

    public static void nicli(int a, int b, int c) {
        int diskriminanta = b * b - 4 * a * c;
        if (diskriminanta < 0) {
            System.out.println("Napaka: nicli enacbe ne obstajata.");
            return;
        }
        double prvaNicla = (-b + Math.sqrt(diskriminanta)) / (2.0 * a) ;
        double drugaNicla = (-b - Math.sqrt(diskriminanta)) / (2.0 * a) ;

        System.out.printf("x1=%.2f, x2=%.2f", prvaNicla, drugaNicla);
    }

    public static void krog(double r, int d) {
        if (r < 0) {
            System.out.println("Napaka: negativen polmer");
            return;
        } else if (d < 0) {
            System.out.println("Napaka: negativen d");
            return;
        }

        double obseg = 2 * Math.PI * r;
        double ploscina = r * r * Math.PI;
        String decimalPrecision = "%." + d + "f";

        System.out.println("Obseg kroga s polmerom r=" + String.format("%.2f", r) + " je " + String.format(decimalPrecision, obseg));
        System.out.println("Ploscina kroga s polmerom r=" + String.format("%.2f", r) + " je " + String.format(decimalPrecision, ploscina));

    }

    public static String pretvoriSekunde(int sekunde) {
        if (sekunde < 0) {
            return "Število sekund ne more biti negativno";
        }
        int hh = sekunde / 3600;
        sekunde -= hh * 3600;
        int mm = sekunde / 60;
        sekunde -= mm * 60;

        return String.format("%02d:%02d:%02d", hh, mm, sekunde);
    }


    private static void javaJavaJava(int n) {
        if (n < 0) {
            System.out.println("Napaka: negativen n");
            return;
        }

        System.out.println(("     J    a   v     v  a   ").repeat(n));
        System.out.println(("     J   a a   v   v  a a  ").repeat(n));
        System.out.println(("  J  J  aaaaa   V V  aaaaa ").repeat(n));
        System.out.println(("   JJ  a     a   V  a     a").repeat(n));
    }

    private static boolean jeFibonaccijevo(int n) {
        int a = 0;
        int b = 1;
        while (a + b < n) {
            b = a + b;
            a = b - a;
        }
        return n == a + b;
    }

    public static boolean jePrastevilo(int n) {
        if (n <= 1) {
            return false;
        }

        int counter = 0;
        for (int i = 1; i <= n / 2; i++) {
            if (n % i == 0) {
                counter++;
            }
        }
        return counter == 1;
    }

    public static void izrisiZastavo(int n) {
        int dolzinaZastave = 15 * n + 1;
        int visinaZastave = 5 * n;
        int dolzinaKvadrata = 4 * n;
        int visinaKvadrata = 3 * n;

        for (int i = 0; i < visinaKvadrata; i++) {
            if (i % 2 == 0) {
                System.out.print(("* ").repeat(dolzinaKvadrata / 2));
                System.out.println(("=").repeat(dolzinaZastave - dolzinaKvadrata));
            } else {
                System.out.print((" *").repeat(dolzinaKvadrata / 2 - 1) + "  ");
                System.out.println(("=").repeat(dolzinaZastave - dolzinaKvadrata));
            }
        }

        for (int j = 0; j < visinaZastave - visinaKvadrata; j++) {
            System.out.println(("=").repeat(dolzinaZastave));
        }
    }

    public static void vDesetisko(int n) {
        int desetisko = 0;
        int stevilo = n;
        int i = 0;
        while (stevilo > 0) {
            int stevka = stevilo % 10;
            stevilo /= 10;
            if (stevka == 9 || stevka == 8) {
                System.out.printf("Število %d ni število v osmiškem sistemu (števka " + stevka + ")" , n);
                return;
            }
            desetisko += (int) (stevka * Math.pow(8, i));
            i++;
        }
        System.out.printf("%d(8) = %d(10)", n, desetisko);
    }

    public static String pretvoriVDesetisko(String n, int b) {
        int desetisko = 0;

        for (int i = 0; i < n.length(); i++) {
            int stevka = (int) n.charAt(n.length() - 1 - i);
            if (stevka > 64) {
                stevka -= 55;
            } else {
                stevka -= 48;
            }

            if (stevka >= b) {
                return "Napaka pri pretvorbi sistema - števka " + n.charAt(n.length() - 1 - i);
            }

            desetisko += (int) (stevka * Math.pow(b, i));
        }

        return String.format("%s(%d)=%d(10)", n, b, desetisko);
    }


    private static int vsotaPrvih(int n) {
        int vsota = 0;
        int stevilo = 2;
        while (n > 0) {
            if (jePrastevilo(stevilo)) {
                n--;
                vsota += stevilo;
            }
            stevilo++;
        }
        return vsota;
    }

    public static void pitagoroviTrojcki(int x) {
        for (int a = 1; a <= x; a++) {
            for(int b = a; b <= x; b++) {
                int c2 = a * a + b * b;
                int c = (int) Math.sqrt(c2);

                if (c >= b && c <= x && c * c == c2) {
                    System.out.println(a + " " + b + " " + c);
                }
            }
        }
    }

    public static int[] stik(int[] tabela1, int[] tabela2) {
        int[] res = new int[tabela1.length + tabela2.length];
        System.arraycopy(tabela1, 0, res, 0, tabela1.length);
        System.arraycopy(tabela2, 0, res, tabela1.length, tabela2.length);

        return res;
    }

    public static int[] presek(int[] tabela1, int[] tabela2) {
        ArrayList<Integer> prvaTabela = new ArrayList<>();
        ArrayList<Integer> skupni = new ArrayList<>();

        for (int x: tabela2) {
            if (!prvaTabela.contains(x)) {
                prvaTabela.add(x);
            }
        }

        for (int x: tabela1) {
            if (prvaTabela.contains(x) && !skupni.contains(x)) {
                skupni.add(x);
            }
        }

        int[] res = new int[skupni.size()];
        int i = 0;

        for (int x: skupni) {
            res[i++] = x;
        }

        return res;
    }

    private static String izracunajRazliko(String prviCas, String drugiCas) {
        String[] prviSplit = prviCas.split(":");
        String[] drugiSplit = drugiCas.split(":");
        int prviSekunde = Integer.parseInt(prviSplit[2]) + Integer.parseInt(prviSplit[1]) * 60 + Integer.parseInt(prviSplit[0]) * 3600;
        int drugiSekunde = Integer.parseInt(drugiSplit[2]) + Integer.parseInt(drugiSplit[1]) * 60 + Integer.parseInt(drugiSplit[0]) * 3600;


        int razlika = drugiSekunde > prviSekunde ? drugiSekunde - prviSekunde : prviSekunde - drugiSekunde;

        return String.format("%02d:%02d:%02d", razlika / 3600, (razlika % 3600) / 60, razlika % 60);
    }

    public static void praDvojcek(int n) {
        for (int i = 4; i < n; i++) {
            if (jePrastevilo(i) && jePrastevilo(i - 2)) {
                System.out.printf("(%d, %d)\n", i-2, i);
            }
        }
    }

    public static void trikotnik(int n, int tip) {
        String output = "";
        switch (tip) {
            case 1:
                for (int i = 1; i <= n; i++) {
                    output += String.valueOf(i % 10) + " ";
                    System.out.println(output);
                }
                break;

            case 2:
                for (int i = 1; i <= n; i++) {
                    output += String.valueOf(i  % 10) + " ";
                }
                for (int j = 0; j < n; j++) {
                    String output2 = output.substring(0, output.length() - (2 * j));
                    System.out.println(("  ").repeat(j) +  output2);
                }
                break;

            case 3:
                for (int i = 1; i <= n; i++) {
                    output = String.valueOf(i  % 10) + " " + output;
                    System.out.println((" ").repeat((n - i) * 2) + output);
                }
                break;

            case 4:
                for (int i = n; i >= 1; i--) {
                    output += String.valueOf(i  % 10) + " ";
                }

                for (int j = 0; j < n; j++) {
                    System.out.println(output.substring(2 * j));
                }
                break;

            case 5:
                for (int i = 1; i <= n; i++) {
                    output = "";
                    for (int x = 1; x <= i; x++) {
                        output += String.valueOf(x  % 10) + " ";
                    }
                    for (int j = i - 1; j >= 1; j--) {
                        output += String.valueOf(j  % 10) + " ";
                    }
                    System.out.println(" ".repeat((n - i) * 2) + output);
                }
                break;

            case 6:
                for (int i = 0; i < n; i++) {
                    output = "";
                    for (int x = 1; x < n - i; x++) {
                        output += String.valueOf(x  % 10) + " ";
                    }
                    for (int j = n - i; j >= 1; j--) {
                        output += String.valueOf(j  % 10) + " ";
                    }
                    System.out.println(" ".repeat(i * 2) + output);
                }
                break;

            case 7:
                for (int i = 1; i <= n; i++) {
                    output = "";
                    for (int x = i; x < i + i; x++) {
                        output += String.valueOf(x  % 10) + " ";
                    }
                    for (int j = i + i - 2; j >= i; j--) {
                        output += String.valueOf(j  % 10) + " ";
                    }
                    System.out.println(" ".repeat((n - i) * 2) + output);
                }
                break;
        }
    }

    public static void metulj(int n, int tip) {
        String output = "";

        switch (tip) {
            case 1:
                for (int i = 1; i <= n; i++) {
                    output = "";
                    for (int j = 1; j <= i; j++) {
                        output += String.valueOf(j % 10) + " ";
                    }

                    output = output + (" ").repeat(Math.max((n - i) * 4 - 2, 0));

                    for (int x = i; x >= 1; x--) {
                        if (x != n) {
                            output += String.valueOf(x % 10) + " ";
                        }
                    }
                    System.out.println(output);
                }
                break;

            case 2:
                for (int i = 0; i < n; i++) {
                    output = "";
                    for (int j = 1; j <= n - i; j++) {
                        output += String.valueOf(j % 10) + " ";
                    }

                    output = output + (" ").repeat(Math.max(i * 4 - 2, 0));

                    for (int x = n - i; x >= 1; x--) {
                        if (x != n) {
                            output += String.valueOf(x % 10) + " ";
                        }
                    }
                    System.out.println(output);
                }
                break;

            case 3:
                for (int i = 1; i <= n; i++) {
                    output = "";
                    for (int j = 1; j <= i; j++) {
                        output += String.valueOf(j % 10) + " ";
                    }

                    output = output + (" ").repeat(Math.max((n - i) * 4 - 2, 0));

                    for (int x = i; x >= 1; x--) {
                        if (x != n) {
                            output += String.valueOf(x % 10) + " ";
                        }
                    }
                    System.out.println(output);
                }

                for (int i = 0; i < n; i++) {
                    output = "";
                    if (i != 0) {
                        for (int j = 1; j <= n - i; j++) {
                            output += String.valueOf(j % 10) + " ";
                        }

                        output = output + (" ").repeat(Math.max(i * 4 - 2, 0));

                        for (int x = n - i; x >= 1; x--) {
                            if (x != n) {
                                output += String.valueOf(x % 10) + " ";
                            }
                        }
                        System.out.println(output);
                    }
                }
                break;
        }
    }


    private static void veckratnikDelitelj(int a, int b) {
        if (a == 0 || b == 0) {
            System.out.print("Napaka: obe števili morata biti različni od nič.");
            return;
        }
        a = Math.abs(a);
        b = Math.abs(b);

        int maxDelitelj = 1;

        for (int i = 1; i <= Math.min(a, b); i++) {
            if (a % i == 0 && b % i == 0) {
                maxDelitelj = i;
            }
        }

        System.out.printf("Največji skupni delitelj je %d.%n", maxDelitelj);

        int j = Math.max(a, b);
        while (true) {
            if ((j % a == 0) && (j % b == 0)) {
                System.out.printf("Najmanjši skupni večkratnik je %d.", j);
                break;
            }
            j++;
        }

    }
}