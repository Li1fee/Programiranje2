import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class DN05 {
    public static void main(String[] args) {
        if (args.length == 2 && "branje".equals(args[0])) {
            String imeDatoteke = args[1];

            String tipDatoteke;
            try (Scanner sc = new Scanner(new File(imeDatoteke))) {
                if (!sc.hasNextLine()) {
                    System.out.println("Napaka: nepravilen format datoteke.");
                    return;
                }
                tipDatoteke = sc.nextLine();
            } catch (FileNotFoundException e) {
                System.out.println("Napaka: datoteka ne obstaja.");
                return;
            }
            char[][] rezultat;
            if (tipDatoteke.equals("UREJENO")) {
                rezultat = preberiUrejenoDatoteko(imeDatoteke);
            } else if (tipDatoteke.equals("NEUREJENO")) {
                rezultat = preberiNeurejenoDatoteko(imeDatoteke);
            } else {
                System.out.println("Napačna vrsta datoteke.");
                return;
            }
            izpisRezultat(rezultat);
            return;
            
        } else if (args.length == 3 && "poravnaj".equals(args[0])) {
            char[][] tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = poravnajVrstice(tabela, args[2]);
                izpisRezultat(rezultat);
            }
            return;
            
        } else if (args.length == 6 && "slika".equals(args[0])) {
            char[][] tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = vstaviSliko(tabela,
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]),
                        Integer.parseInt(args[5]));
                izpisRezultat(rezultat);
            }
            return;
        }

        System.out.println("Napaka: neustrezni argumenti.");
        return;
    }

    static void izpisRezultat(char[][] rezultat) {
        if (rezultat == null) return;
        for (char[] vrstica: rezultat) {
            System.out.println(new String(vrstica));
        }
    }

    static char[][] preberiUrejenoDatoteko(String imeDatoteke) {
        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            // tip datoteke check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            if (!sc.nextLine().equals("UREJENO")) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            // dimenzija check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

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
                    System.out.println("Napaka: nepravilne dimenzije strani.");
                    return null;
                }

                String vrsticaStrani = sc.nextLine();
                if (vrsticaStrani.length() != sirinaStrani) {
                    System.out.println("Napaka: nepravilne dimenzije strani.");
                    return null;
                }
                tabelaStrani[i] = vrsticaStrani.toCharArray();
            }
            // preveri če je še več vrstic
            if (sc.hasNextLine()) {
                System.out.println("Napaka: nepravilne dimenzije strani.");
                return null;
            }

            return tabelaStrani;

        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            return null;
        }
    }

    static char[][] preberiNeurejenoDatoteko(String imeDatoteke) {
        try (Scanner sc = new Scanner(new File(imeDatoteke))) {
            // tip datoteke check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            if (!sc.nextLine().equals("NEUREJENO")) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            // dimenzija check
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            String[] dimenzijaStrani = sc.nextLine().split("x");
            if (dimenzijaStrani.length != 2) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }
            int sirinaStrani = Integer.parseInt(dimenzijaStrani[0]);
            int visinaStrani = Integer.parseInt(dimenzijaStrani[1]);

            // vsebina chech
            if (!sc.hasNextLine()) {
                System.out.println("Napaka: nepravilen format datoteke.");
                return null;
            }

            char[][] tabelaStrani = new char[visinaStrani][];

            String[] besedeStrani = sc.nextLine().split(" ");

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
            if (!trenutniStavek.isEmpty()) {
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

            return tabelaStrani;

        } catch (FileNotFoundException e) {
            System.out.println("Napaka: datoteka ne obstaja.");
            return null;
        }
    }

    static  char[][] poravnajVrstice(char[][] tabela, String nacin) {
        int dolzinaTabele = tabela.length;
        int dolzinaVrstice = tabela[0].length;
        char[][] poravnanaTabela = new char[dolzinaTabele][];

        switch (nacin) {
            case "levo" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String besede = besedeIzVrstice(tabela[i]);
                    poravnanaTabela[i] = (besede + ("_").repeat(dolzinaVrstice - besede.length())).toCharArray();
                }
            }
            case "desno" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String besede = besedeIzVrstice(tabela[i]);
                    poravnanaTabela[i] = (("_").repeat(dolzinaVrstice - besede.length()) + besede).toCharArray();
                }
            }
            case "sredina" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String besede = besedeIzVrstice(tabela[i]);
                    double polovica = (dolzinaVrstice - besede.length()) / 2.0;

                    besede = ("_").repeat((int) polovica) + besede + ("_").repeat((int) Math.ceil(polovica));
                    poravnanaTabela[i] = besede.toCharArray();
                }
            }
            case "obojestransko" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String[] vseBesede = posamezneBesedeIzVrstice(tabela[i]);
                    int steviloVsehBesed = vseBesede.length;

                    int steviloProstora = dolzinaVrstice;
                    for (String beseda: vseBesede) {
                        steviloProstora -= beseda.length();
                    }

                    double steviloPresledkov = (Math.max(1.00, steviloVsehBesed - 1.00));

                    double steviloZnakov = steviloProstora / steviloPresledkov;
                    int dodatniZnak = steviloProstora % (int) steviloPresledkov;

                    StringBuilder res = new StringBuilder();
                    for (int j = 0; j < steviloVsehBesed; j++) {
                        res.append(vseBesede[j]).append(("_").repeat(j != steviloVsehBesed - 1 || steviloVsehBesed == 1 ? (int) steviloZnakov + (dodatniZnak-- > 0 ? 1 : 0) : 0));
                    }

                    poravnanaTabela[i] = res.toString().toCharArray();
                }
            }
        }
        return poravnanaTabela;
    }

    static String besedeIzVrstice(char[] vrstica) {
        StringBuilder besedilo = new StringBuilder();
        int dolzinaVrstice = vrstica.length;
        int tabelaPointer = 0;
        for (char c : vrstica) {
            if (c != '_' || (!besedilo.isEmpty() && besedilo.charAt(besedilo.length() - 1) != '_')) {
                besedilo.append(c);
            }
        }
        if (besedilo.charAt(besedilo.length() - 1) == '_') {
            return besedilo.substring(0, besedilo.length() - 1);
        }
        return besedilo.toString();
    }


    static String[] posamezneBesedeIzVrstice(char[] vrstica) {
        String skupajBesede = besedeIzVrstice(vrstica);
        int steviloBesed = 1;

        for (char c : skupajBesede.toCharArray()) {
            if (c == '_') {
                steviloBesed++;
            }
        }

        String[] splitedBesede = new String[steviloBesed];
        StringBuilder beseda = new StringBuilder();
        int steviloVsehBesed = splitedBesede.length;

        for (char c : skupajBesede.toCharArray()) {
            if (c == '_') {
                splitedBesede[steviloVsehBesed - steviloBesed--] = beseda.toString();
                beseda.setLength(0);
            } else {
                beseda.append(c);
            }
        }
        splitedBesede[steviloVsehBesed - 1] = beseda.toString();

        return splitedBesede;
    }

    static char[][] vstaviSliko(char[][] tabela, int x, int y, int s, int v) {
        int dolzinaTabele = tabela.length;
        int dolzinaVrstice = tabela[0].length;

        ArrayList<String> ostaleBesede = new ArrayList<>();
        int ostalePointer = 0;

        for (int i = y; i < dolzinaTabele; i++) {
            String[] posamezneBesede = posamezneBesedeIzVrstice(tabela[i]);
            StringBuilder vrstica = new StringBuilder();

            int[] pozicijeBesed = new int[posamezneBesede.length + 1];
            pozicijeBesed[posamezneBesede.length] = -1;
            int pos = 0;

            String tabela2 = "_" + new String(tabela[i]);
            for (int c = 1; c < tabela2.length(); c++) {
                if (tabela2.charAt(c - 1) == '_' && tabela2.charAt(c) != '_') {
                    pozicijeBesed[pos++] = c - 1;
                }
            }

            pos = 0;

            for (int j = 0; j < dolzinaVrstice; j++) {
                if (j >= x && j < x + s && i < y + v) {
                    vrstica.append("#");
                    if (j == pozicijeBesed[pos]) {
                        ostaleBesede.add(posamezneBesede[pos++]);
                    }
                } else {
                    if (ostalePointer == ostaleBesede.size()) {
                        if (j == pozicijeBesed[pos]) {
                            if (posamezneBesede[pos].length() + j <= x) {
                                j += posamezneBesede[pos].length() - 1;
                                vrstica.append(posamezneBesede[pos++]);
                                if (j + 1 < x) {
                                    vrstica.append("_");
                                    j++;
                                }
                            } else if ((j >= x + s || i >= y + v) && posamezneBesede[pos].length() + j <= dolzinaVrstice) {
                                j += posamezneBesede[pos].length() - 1;
                                vrstica.append(posamezneBesede[pos++]);
                                if (j + 1 < dolzinaVrstice) {
                                    vrstica.append("_");
                                    j++;
                                }
                            } else {
                                ostaleBesede.add(posamezneBesede[pos++]);
                                int steviloAppenda = j < x ? x - j : dolzinaVrstice - j;
                                vrstica.append("_".repeat(steviloAppenda));
                                j += steviloAppenda - 1;
                            }
                        }
                        else {
                            vrstica.append("_");
                        }
                    } else {
                        if (ostaleBesede.get(ostalePointer).length() + j <= x) {
                            for (int st: pozicijeBesed) {
                                if (st >= j &&  st < ostaleBesede.get(ostalePointer).length() + j) {
                                    ostaleBesede.add(posamezneBesede[pos++]);
                                }
                            }
                            j += ostaleBesede.get(ostalePointer).length() - 1;
                            vrstica.append(ostaleBesede.get(ostalePointer++));
                            if (j + 1 < x) {
                                if (j + 1 == pozicijeBesed[pos]) {
                                    ostaleBesede.add(posamezneBesede[pos++]);
                                }
                                vrstica.append("_");
                                j++;
                            }

                        } else if ((j >= x + s || i >= y + v) && ostaleBesede.get(ostalePointer).length() + j <= dolzinaVrstice) {
                            for (int st: pozicijeBesed) {
                                if (st >= j &&  st < ostaleBesede.get(ostalePointer).length() + j) {
                                    ostaleBesede.add(posamezneBesede[pos++]);
                                }
                            }
                            j += ostaleBesede.get(ostalePointer).length() - 1;
                            vrstica.append(ostaleBesede.get(ostalePointer++));
                            if (j + 1 < dolzinaVrstice) {
                                if (j + 1 == pozicijeBesed[pos]) {
                                    ostaleBesede.add(posamezneBesede[pos++]);
                                }
                                vrstica.append("_");
                                j++;
                            }
                        } else {
                            if (j == pozicijeBesed[pos]) {
                                ostaleBesede.add(posamezneBesede[pos++]);
                            }
                            vrstica.append("_");
                        }
                    }
                }
            }
            tabela[i] = vrstica.toString().toCharArray();
        }

        if (ostaleBesede.size() != ostalePointer) {
            System.out.println("Napaka: premajhne dimenzije strani.");
            return null;
        }

        return tabela;
    }
}