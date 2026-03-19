import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DN05 {
    public static void main(String[] args) throws FileNotFoundException {
        String vrstaNaloge = args[0];
        String imeDatoteke = args[1];
        Scanner sc = new Scanner(new File(imeDatoteke));

        String vrstaDatoteke = sc.nextLine();
        sc.close();

        if (vrstaDatoteke.equals("UREJENO")) {
            preberiUrejenoDatoteko(imeDatoteke);
        } else if (vrstaDatoteke.equals("NEUREJENO")) {
            preberiNeurejenoDatoteko(imeDatoteke);
        } else {
            System.out.println("Napačna vrsta datoteke!");
        }


    }

    static char[][] preberiUrejenoDatoteko(String imeDatoteke) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(imeDatoteke));
        sc.nextLine(); // skip first line

        String[] dimenzijaStrani = sc.nextLine().split("x");
        if (dimenzijaStrani.length != 2) {
            System.out.println("Napaka: nepravilen format datoteke.");
            sc.close();
            return null;
        }
        int sirinaStrani = Integer.parseInt(dimenzijaStrani[0]);
        int visinaStrani = Integer.parseInt(dimenzijaStrani[1]);

        char[][] tabelaStrani = new char[visinaStrani][];

        for (int i = 0; i < visinaStrani; i++) {
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilne dimenzije strani.!");
                sc.close();
                return null;
            }

            String vrsticaStrani = sc.nextLine();
            if (vrsticaStrani.length() != sirinaStrani) {
                System.out.println("Napaka: nepravilne dimenzije strani.!");
                sc.close();
                return null;
            }
            tabelaStrani[i] = vrsticaStrani.toCharArray();
        }
        // preveri če je še več vrstic
        if (sc.hasNextLine()) {
            System.out.println("Napaka: nepravilne dimenzije strani.");
            sc.close();
            return null;
        }
        sc.close();

        System.out.println(Arrays.deepToString(tabelaStrani));
        return tabelaStrani;
    }

    static char[][] preberiNeurejenoDatoteko(String imeDatoteke) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(imeDatoteke));
        sc.nextLine(); // skip first line

        String[] dimenzijaStrani = sc.nextLine().split("x");
        if (dimenzijaStrani.length != 2) {
            System.out.println("Napaka: nepravilen format datoteke.");
            sc.close();
            return null;
        }
        int sirinaStrani = Integer.parseInt(dimenzijaStrani[0]);
        int visinaStrani = Integer.parseInt(dimenzijaStrani[1]);

        char[][] tabelaStrani = new char[visinaStrani][];

        String[] besedeStrani = sc.nextLine().split( " ");
        sc.close();

        int steviloVrstic = 0;
        StringBuilder trenutniStavek = new StringBuilder();

        for (String beseda : besedeStrani) {
            int dolzinaBesede = beseda.length();

            if (dolzinaBesede > sirinaStrani || steviloVrstic == visinaStrani) {
                System.out.println("Napaka: premajhne dimenzije strani.");
                return null;
            }

            int dodatno = trenutniStavek.isEmpty() ? dolzinaBesede : dolzinaBesede + 1;

            if (trenutniStavek.length() + dodatno > sirinaStrani) {
                String celStavek = trenutniStavek + ("_").repeat(sirinaStrani - trenutniStavek.length());
                tabelaStrani[steviloVrstic++] = celStavek.toCharArray();
                trenutniStavek.setLength(0); // -> ""
            }

            trenutniStavek.append(trenutniStavek.isEmpty() ? "" : "_").append(beseda);
        }

        // edge case
        if (!trenutniStavek.isEmpty()){
            if (steviloVrstic >= visinaStrani) {
                System.out.println("Napaka: premajhne dimenzije strani.");
                return null;
            }
            String celStavek = trenutniStavek + ("_").repeat(sirinaStrani - trenutniStavek.length());
            tabelaStrani[steviloVrstic++] = celStavek.toCharArray();
        }

        // manjkajoče linije
        while (steviloVrstic < visinaStrani) {
            String praznaVrstica = ("_").repeat(sirinaStrani);
            tabelaStrani[steviloVrstic++] = praznaVrstica.toCharArray();
        }

        System.out.println(Arrays.deepToString(tabelaStrani));
        return tabelaStrani;
    }
}