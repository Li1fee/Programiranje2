import java.util.Arrays;

public class Kviz2 {
    public static void main(String[] args) {
        System.out.println(vsotaStevk("1abc2"));
        System.out.println(preveriRep("DAN", "Dan na dan"));
        System.out.println(Arrays.toString(range(420, 9001, 420)));
        rotiraj(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1}, 5);
        System.out.println(Arrays.toString(duplikati(new int[]{1, 5, 8, 56, 3, 9, 1, 43, 1, 2, 56, 12, 1, 3})));
        System.out.printf("%.3f\n", koren(9, 3));
        System.out.printf("%.2f\n", koren(10, 2));
        System.out.printf("%.5f\n", koren(15, 5));
        System.out.printf("%.2f\n", koren(4, 2));
        System.out.println(binarnoSestej("10011010010", "1000011100001"));
        System.out.println(vsotaSkupnihCifer(13, 30));
        System.out.println(prevod("Popamlapad pripahapajapa"));
        System.out.println(prevod("Napaka"));
        System.out.println(prevod("Dapanepas jepa lepap dapan"));
        System.out.println(prepleti("pomlad", "JESEN"));
        System.out.println(prepleti("december", "maj"));
        odpleti("dmeacje m b e r ");
        System.out.println(vMorse("Kako si kaj?"));
        System.out.println(fibo(1));
        pascal(7);
        izpisKoledarja(2019, 3);
        posebnaStevila(100);
        kodiraj("Danes je res lep dan", 5);
        nicleSpredaj(new int[]{1, 2, 3, 0, 0, 0});
        System.out.println(strStej("JanezJanezCeljski", "Janez"));
        System.out.println("JDKFGJFSDKJF");
        int[] tabela = {1,2,1};
        System.out.println(zrcalnoMaxZaporedje(tabela));

        double[][] a4 = {{1.0, 2.0}, {3.0, 4.0}, {5.0, 6.0}};
        double[][] b4 = {{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}};
        double[][] c4 = zmnoziMatriki(a4, b4);
        System.out.println(Arrays.deepToString(c4));


        double[][] a = {{1.0, 2.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}};
        double[][] b = {{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}};
        double[][] c = zmnoziMatriki(a, b);

        double[][] a2 = {{1.0,1.0}, {1.0, 1.0}};
        double[][] b2 = {{1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}};
        double[][] c2 = zmnoziMatriki(a2, b2);


        double[][] a3 = {{0.384333, 0.664819, 0.483477, 0.367717}, {0.111515, 0.40472,
                0.0637651, 0.712918}, {0.0973583, 0.510747, 0.420177, 0.42907}};
        double[][] b3 = {{0.490275, 0.777118, 0.247288, 0.686515, 0.304823}, {0.935864,
                0.118776, 0.113646, 0.418578, 0.654628}, {0.570683, 0.572742,
                0.0342458, 0.989809, 0.0872066}, {0.205025, 0.922731, 0.585089,
                0.0234414, 0.492108}};
        double[][] c3 = zmnoziMatriki(a3, b3);
        System.out.println(Arrays.deepToString(c3));
    }

    public static int vsotaStevk(String str) {
        int sum = 0;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                sum += c - '0';
            }
        }
        return sum;
    }


    public static boolean preveriRep(String a, String b) {
        String lowerA = a.toLowerCase();
        String lowerB = b.toLowerCase();

        return a.length() >= b.length() ?
                lowerA.endsWith(lowerB) :
                lowerB.endsWith(lowerA);
    }

    public static int[] range(int a, int b, int c) {
        int n = (b - a + c - 1) / c;
        int[] tabelaStevil = new int[n];
        for (int i = 0; i < n; i++) {
            tabelaStevil[i] = a + c * i;
        }
        return tabelaStevil;
    }

    public static void rotiraj(int[] tabela, int k) {
        int dolzinaTabele = tabela.length;
        int rotacija = k % dolzinaTabele;

        int[] kopiranaTabela = tabela.clone();

        for (int i = 0; i < dolzinaTabele; i++) {
            tabela[(i - rotacija + dolzinaTabele) % dolzinaTabele] = kopiranaTabela[i];
        }
    }

    public static int[] duplikati(int[] tabela) {
        boolean[] jeDuplikat = new boolean[tabela.length];
        int x = 0;

        for (int i = 0; i < tabela.length; i++) {
            boolean duplikat = false;

            for (int j = 0; j < i; j++) {
                if (tabela[i] == tabela[j]) {
                    duplikat = true;
                    break;
                }
            }

            if (!duplikat) {
                jeDuplikat[i] = true;
                x++;
            }
        }

        int[] odstranjeniDuplikati = new int[x];
        int k = 0;
        for (int i = 0; i < tabela.length; i++) {
            if (jeDuplikat[i]) {
                odstranjeniDuplikati[k++] = tabela[i];
            }
        }
        return odstranjeniDuplikati;
    }

    public static double koren(int x, int d) {
        double priblizekKorena = 0;

        // osnova
        while ((priblizekKorena + 1) * (priblizekKorena + 1) <= x) {
            priblizekKorena += 1;
        }

        // priblizek
        for (int i = 1; i <= d; i++) {
            double pristevek = 1.0 / Math.pow(10, i);
            while ((priblizekKorena + pristevek) * (priblizekKorena + pristevek) <= x) {
                priblizekKorena = priblizekKorena + pristevek;
            }
        }

        return priblizekKorena;
    }


    public static String binarnoSestej(String s, String b) {
        boolean carried = false;
        StringBuilder res = new StringBuilder();

        if (s.length() >= b.length()) {
            b = "0".repeat(s.length() - b.length()) + b;
        } else {
            s = "0".repeat(b.length() - s.length()) + s;
        }

        for (int i = s.length() - 1; i > -1; i--) {
            int vsota = (s.charAt(i) - '0') + (b.charAt(i) - '0') + (carried ? 1 : 0);
            res.insert(0, vsota % 2);
            carried = vsota >= 2;
        }

        if (carried) {
            res.insert(0, '1');
        }

        return res.toString();
    }

    public static int vsotaSkupnihCifer(int a, int b) {
        boolean[] cifraA = new boolean[10]; // 0 - 9
        boolean[] cifraB = new boolean[10]; // 0 - 9

        while (a > 0) {
            cifraA[a % 10] = true;
            a /= 10;
        }

        while (b > 0) {
            cifraB[b % 10] = true;
            b /= 10;
        }

        int sum = 0;
        for (int i = 0; i < cifraA.length; i++) {
            if (cifraA[i] && cifraB[i]) {
                sum += i;
            }
        }

        return sum;
    }

    static boolean jePapajscina(String niz) {
        for (int i = 0; i < niz.length(); i++) {
            if ("aeiouAEIOU".contains(String.valueOf(niz.charAt(i)))) {
                if (i + 2 >= niz.length() || niz.charAt(i + 1) != 'p' || niz.charAt(i + 2) != 'a') {
                    return false;
                }
                i += 2;
            }
        }
        return true;
    }

    static String vPapajscino(String niz) {
        StringBuilder res = new StringBuilder();
        for (char c : niz.toCharArray()) {
            res.append(c);
            if ("aeiouAEIOU".contains(String.valueOf(c))) {
                res.append("pa");
            }
        }
        return res.toString();
    }

    static String izPapajscine(String niz) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < niz.length(); i++) {
            res.append(niz.charAt(i));
            if ("aeiouAEIOU".contains(String.valueOf(niz.charAt(i)))) {
                i += 2;
            }
        }
        return res.toString();
    }

    public static String prevod(String niz) {
        return jePapajscina(niz) ? izPapajscine(niz) : vPapajscino(niz);
    }

    public static String prepleti(String niz1, String niz2) {
        StringBuilder res = new StringBuilder();
        int dolzinaNiza1 = niz1.length();
        int dolzinaNiza2 = niz2.length();

        if (dolzinaNiza1 >= dolzinaNiza2) {
            niz2 += " ".repeat(dolzinaNiza1 - dolzinaNiza2);
        } else {
            niz1 += " ".repeat(dolzinaNiza2 - dolzinaNiza1);
        }

        for (int i = 0; i < niz1.length(); i++) {
            res.append(niz1.charAt(i));
            res.append(niz2.charAt(i));
        }

        return res.toString();
    }

    public static void odpleti(String niz) {
        StringBuilder niz1 = new StringBuilder();
        StringBuilder niz2 = new StringBuilder();

        for (int i = 0; i < niz.length(); i++) {
            if (i % 2 == 0) {
                niz1.append(niz.charAt(i));
            } else {
                niz2.append(niz.charAt(i));
            }
        }

        System.out.println(niz1.toString().trim());
        System.out.println(niz2.toString().trim());
    }

    public static String vMorse(String niz) {
        StringBuilder nizVMorseCode = new StringBuilder();
        String[] morseCode = {
                ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....",
                "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
                "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--.."
        };
        String[] morseNumbers = {
                "-----", ".----", "..---", "...--", "....-",
                ".....", "-....", "--...", "---..", "----."
        };

        char[] locilaZnaki = {
                '.', ',', '?', '!', '/', '(', ')', '&',
                ':', ';', '=', '+', '-', '_', '"', '$', '@'
        };

        String[] morseLocila = {
                ".-.-.-", "--..--", "..--..", "-.-.--", "-..-.",
                "-.--.", "-.--.-", ".-...", "---...", "-.-.-.",
                "-...-", ".-.-.", "-....-", "..--.-", ".-..-.",
                "...-..-", ".--.-."
        };


        for (char c : niz.toUpperCase().toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                nizVMorseCode.append(morseCode[Character.toUpperCase(c) - 'A']).append(" ");
            } else if (c >= '0' && c <= '9') {
                nizVMorseCode.append(morseNumbers[c - '0']).append(" ");
            } else if (c == ' ') {
                nizVMorseCode.append(" ");
            } else {
                for (int i = 0; i < locilaZnaki.length; i++) {
                    if (locilaZnaki[i] == c) {
                        nizVMorseCode.append(morseLocila[i]).append(" ");
                        break;
                    }
                }
            }
        }

        return nizVMorseCode.toString();
    }

    public static int fibo(int n) {
        int[] fibonaccijevaStevila = new int[n * n];
        fibonaccijevaStevila[0] = 1;
        int a = 1;
        int b = 1;

        for (int i = 1; i < n * n; i++) {
            int next = a + b;
            a = b;
            b = next;
            fibonaccijevaStevila[i] = a;
        }

        int sum = 0;

        for (int i = 0; i < n; i++) {
            sum += fibonaccijevaStevila[i * n + i] + fibonaccijevaStevila[(i + 1) * n - 1 - i];
        }

        return sum;
    }

    public static int[] pascal(int n) {
        int[][] pascalovTrikotnik = new int[n][];
        pascalovTrikotnik[0] = new int[]{1};

        for (int i = 1; i < n; i++) {
            pascalovTrikotnik[i] = new int[i + 1];
            pascalovTrikotnik[i][0] = 1;
            pascalovTrikotnik[i][i] = 1;

            for (int j = 1; j < i; j++) {
                pascalovTrikotnik[i][j] = pascalovTrikotnik[i - 1][j - 1] + pascalovTrikotnik[i - 1][j];
            }
        }

        return pascalovTrikotnik[n - 1];
    }

    public static void izpisKoledarja(int leto, int mesec) {
        java.time.YearMonth yearMonth = java.time.YearMonth.of(leto, mesec);
        int steviloDni = yearMonth.lengthOfMonth();
        int prviDan = java.time.LocalDate.of(leto, mesec, 01).getDayOfWeek().getValue() - 1;

        System.out.println("PO  TO  SR  ČE  PE  SO  NE");
        System.out.print("  ".repeat(prviDan * 2));
        int dan = 1;
        for (int i = prviDan; i < steviloDni + prviDan; i++) {
            System.out.printf("%2d", dan++);
            System.out.print("  ");
            if (i % 7 >= 6) {
                System.out.println();
            }
        }
    }

    public static void posebnaStevila(long meja) {

        for (int i = 1; i < meja; i++) {
            int kvadratStevila = i * i;
            String steviloVString = Long.toString(kvadratStevila);

            if (steviloVString.length() == 1) {
                steviloVString = "0" + steviloVString;
            }

            int polovica = steviloVString.length() / 2;

            String levaPolovica = steviloVString.substring(0, polovica);
            int levoStevilo = Integer.parseInt(levaPolovica);

            String desnaPolovica = steviloVString.substring(polovica);
            int desnoStevilo = Integer.parseInt(desnaPolovica);


            if (levoStevilo + desnoStevilo == i) {
                System.out.printf("%-11d%-11d%8s + %s\n", i, kvadratStevila, levaPolovica, desnaPolovica);
            }
        }
    }

    public static String kodiraj(String vhod, int odmik) {
        StringBuilder kodiranZapis = new StringBuilder();

        for (char c : vhod.toCharArray()) {
            kodiranZapis.append((char) (c + odmik));
        }

        System.out.println(kodiranZapis);

        return kodiranZapis.toString();
    }

    public static String dekodiraj(String vhod, int odmik) {
        StringBuilder dekodiranZapis = new StringBuilder();

        for (char c : vhod.toCharArray()) {
            dekodiranZapis.append((char) (c - odmik));
        }

        System.out.println(dekodiranZapis);

        return dekodiranZapis.toString();
    }

    public static int[] nicleSpredaj(int[] tabela) {
        int tabelaPointer = tabela.length - 1;

        for (int i = tabela.length - 1; i > -1; i--) {
            if (tabela[i] != 0) {
                tabela[tabelaPointer--] = tabela[i];
            }
        }

        for (int i = 0; i <= tabelaPointer; i++) {
            tabela[i] = 0;
        }

        return tabela;
    }

    public static int strStej(String str, String sub) {
        if (sub.isEmpty() || str.length() < sub.length()) {
            return 0;
        }

        if (str.startsWith(sub)) {
            return 1 + strStej(str.substring(1), sub);
        } else {
            return strStej(str.substring(1), sub);
        }
    }

    public static int zrcalnoMaxZaporedje(int[] x) {
        for (int i = x.length; i > 0; i--) {
            for (int j = 0; j + i <= x.length; j++) {
                for (int k = 0; k + i <= x.length; k++) {
                    if (jeZrcalno(x, i, j, k)) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    public static boolean jeZrcalno(int[] x, int i, int j, int k) {
        for (int o = 0; o < i; o++) {
            if (x[j + o] != x[k + i - 1 - o]) {
                return false;
            }
        }
        return true;
    }


    public static double[][] zmnoziMatriki(double [][] a, double[][] b) {
        double[][] zmnozenaMatrika = new double[a.length][b[0].length];

        if (a[0].length != b.length) {
            System.out.println("Tabeli nemoremo zmnožiti!");
            return null;
        }

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                for (int k = 0; k < b[j].length; k++) {
                    zmnozenaMatrika[i][k] += a[i][j] * b[j][k];
                }
            }
        }

        return zmnozenaMatrika;
    }
}