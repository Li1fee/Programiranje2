import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

            izpisRezultata(rezultat);
            return;
            
        } else if (args.length == 3 && "poravnaj".equals(args[0])) {
            tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = poravnajVrstice(tabela, args[2]);
                izpisRezultata(rezultat);
            }
            return;
            
        } else if (args.length == 6 && "slika".equals(args[0])) {
            tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = vstaviSliko(tabela,
                        Integer.parseInt(args[2]),
                        Integer.parseInt(args[3]),
                        Integer.parseInt(args[4]),
                        Integer.parseInt(args[5])
                );
                izpisRezultata(rezultat);
            }
            return;

        } else if (args.length == 4 && "zamenjaj".equals(args[0])) {
            tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = zamenjajBesedo(tabela,
                    args[2],
                    args[3]
                );
                izpisRezultata(rezultat);
            }
            return;

        } else if (args.length == 4 && "dimenzije".equals(args[0])) {
            tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = spremembaDimenzij(
                       Integer.parseInt(args[2]),
                       Integer.parseInt(args[3])
                );
                izpisRezultata(rezultat);
            }
            return;

        } else if (args.length > 2 && "statistike".equals(args[0])) {
            tabela = preberiUrejenoDatoteko(args[1]);
            char[][] argumenti = new char[args.length - 2][];

            for (int i = 2; i < args.length; i++) {
                argumenti[i - 2] = args[i].toCharArray();
            }

            if (tabela != null) {
                izpisiStatistikeNizov(
                    tabela,
                    argumenti
                );
            }
            return;

        } else if (args.length == 4 && "navpicno".equals(args[3])) {
            tabela = preberiUrejenoDatoteko(args[2]);
            if (tabela != null) {
                char[][] rezultat = navpicnoBesedilo(tabela);
                izpisRezultata(rezultat);
            }
            return;
        }
        System.out.println("Napaka: neustrezni argumenti.");
    }

    static char[][] tabela;

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
            // preveri, če je še več vrstic
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
                    String celStavek = trenutniStavek + povniloCrk(sirinaStrani - trenutniStavek.length(), '_');
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
                String celStavek = trenutniStavek + povniloCrk(sirinaStrani - trenutniStavek.length(), '_');
                tabelaStrani[steviloVrstic++] = celStavek.toCharArray();
            }

            // manjkajoče linije
            while (steviloVrstic < visinaStrani) {
                String praznaVrstica = povniloCrk(sirinaStrani, '_');
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
                    poravnanaTabela[i] = (besede + povniloCrk(dolzinaVrstice - besede.length(), '_')).toCharArray();
                }
            }
            case "desno" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String besede = besedeIzVrstice(tabela[i]);
                    poravnanaTabela[i] = (povniloCrk(dolzinaVrstice - besede.length(), '_') + besede).toCharArray();
                }
            }
            case "sredina" -> {
                for (int i = 0; i < dolzinaTabele; i++) {
                    String besede = besedeIzVrstice(tabela[i]);
                    double polovica = (dolzinaVrstice - besede.length()) / 2.0;
                    besede = povniloCrk((int) polovica, '_') + besede + povniloCrk((int) Math.ceil(polovica), '_');
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
                        res.append(vseBesede[j]).append(povniloCrk  (j != steviloVsehBesed - 1 || steviloVsehBesed == 1 ? (int) steviloZnakov + (dodatniZnak-- > 0 ? 1 : 0) : 0, '_'));
                    }

                    poravnanaTabela[i] = res.toString().toCharArray();
                }
            }
        }
        return poravnanaTabela;
    }


    static char[][] vstaviSliko(char[][] tabela, int x, int y, int sirinaSlike, int visinaSlike) {
        int steviloVrstic = tabela.length;
        int dolzinaVrstice = tabela[0].length;

        String[] bufferBesede = new String[0];
        int bufferIndex = 0;

        int dodatniZamik = 0;

        for (int vrsticaIndex = y; vrsticaIndex < steviloVrstic; vrsticaIndex++) {
            String[] besede = posamezneBesedeIzVrstice(tabela[vrsticaIndex]);
            int[] zacetkiBesed = zacetkiBesedIndex(tabela[vrsticaIndex], besede.length);
            zacetkiBesed = vrsticaIndex < y + visinaSlike ? vstaviPolozajSlike(zacetkiBesed, x) : zacetkiBesed;

            int trenutniPos = 0;
            StringBuilder novaVrstica = new StringBuilder();
            boolean postavljenaSlika = false;
            boolean konecSlike = vrsticaIndex >= y + visinaSlike;
            boolean a = false;


            for (int j = 0; j < zacetkiBesed.length; j++) {
                int zamikBesede = (postavljenaSlika ? 1 : 0);
                if (bufferIndex == bufferBesede.length && !a) {
                    if ((zacetkiBesed[j] >= x || zacetkiBesed[j] + besede[j - zamikBesede].length() < x + sirinaSlike) && !konecSlike) {
                        j--;
                        a = true;
                        continue;
                    }

                    novaVrstica.append(povniloCrk(zacetkiBesed[j] - trenutniPos, '_'))
                            .append(besede[j - zamikBesede]);
                    trenutniPos = zacetkiBesed[j];
                } else {
                    for (int n = j; n < zacetkiBesed.length; n++) {
                        j = zacetkiBesed.length;
                        if (zacetkiBesed[n] == x && !postavljenaSlika && !konecSlike) {
                            novaVrstica.append(povniloCrk(x - trenutniPos, '_'))
                                    .append(povniloCrk(sirinaSlike, '#'));
                            trenutniPos = x + sirinaSlike;
                            postavljenaSlika = true;
                            break;
                        } else if (trenutniPos + dodatniZamik >= zacetkiBesed[n] && (postavljenaSlika || konecSlike)) {
                            bufferBesede = besedeVBuffer(bufferBesede, besede[n - zamikBesede]);
                            zamikBesede += besede[n - zamikBesede].length() + 1;
                        } else if ((trenutniPos + dodatniZamik >= zacetkiBesed[n] || (zacetkiBesed[n] >= x)) && !postavljenaSlika) {
                            bufferBesede = besedeVBuffer(bufferBesede, besede[n - zamikBesede]);
                            zamikBesede += besede[n - zamikBesede].length() + 1;
                        } else {
                            j = n;
                            break;
                        }
                        a = false;
                    }


                    for (int b = bufferIndex; b < bufferBesede.length; b++) {
                        String trenutnaBeseda = bufferBesede[bufferIndex];

                        int dodatenPresledek = (trenutniPos == 0 || (trenutniPos == x + sirinaSlike) && !konecSlike ? 0 : 1);
                        int novaPozicija = trenutniPos + trenutnaBeseda.length() + dodatenPresledek;

                        if (novaPozicija <= dolzinaVrstice && ((!postavljenaSlika && novaPozicija < x || trenutniPos >= x + sirinaSlike) || konecSlike)) {
                            novaVrstica.append(povniloCrk(dodatenPresledek, '_'))
                                    .append(trenutnaBeseda);

                            trenutniPos = novaPozicija;

                            dodatniZamik -= trenutnaBeseda.length() + 1;
                            dodatniZamik = Math.max(dodatniZamik, 0);

                            bufferIndex++;
                        } else {
                            break;
                        }
                    }
                }
            }
            novaVrstica.append(povniloCrk(dolzinaVrstice - novaVrstica.length(), '_'));

            tabela[vrsticaIndex] = novaVrstica.toString().toCharArray();
        }

        if (bufferIndex != bufferBesede.length) {
            System.out.println("Napaka: premajhne dimenzije strani.");
            return null;
        }

        return tabela;
    }



    static char[][] zamenjajBesedo(char[][] tabela, String staraBeseda, String novaBeseda) {
        int stVrstic = tabela.length;
        int sirina = tabela[0].length;

        int dolzinaStare = staraBeseda.length();
        int dolzinaNove = novaBeseda.length();

        String[] bufferBesede = new String[0];
        int bufferIndex = 0;

        int dodatniZamik = 0;

        for (int i = 0; i < stVrstic; i++) {
            StringBuilder novaVrstica = new StringBuilder();
            String[] besede = posamezneBesedeIzVrstice(tabela[i]);

            int trenutniPos = 0;

            int[] zacetkiBesed = zacetkiBesedIndex(tabela[i], besede.length);

            boolean premikAktiven = bufferIndex != bufferBesede.length;
            boolean pregledano = false;

            int steviloBesed = besede.length != 0 ? besede.length : 1;

            for (int j = 0; j < steviloBesed; j++) {
                String cistaBeseda = odstraniLocila(besede.length != 0 ? besede[j] : "");

                if (cistaBeseda.equals(staraBeseda) && !premikAktiven) {

                    int potrebnaSirina = Math.max(trenutniPos, zacetkiBesed[j])
                            + dolzinaNove + besede[j].length() - dolzinaStare;

                    if (potrebnaSirina > sirina) {
                        System.out.println("Napaka: premajhne dimenzije strani.");
                        return null;
                    }

                    novaVrstica.append(povniloCrk(zacetkiBesed[j] - trenutniPos, '_'))
                            .append(novaBeseda)
                            .append(besede[j].length() == dolzinaStare ? "" : besede[j].charAt(dolzinaStare));
                    trenutniPos = zacetkiBesed[j] + dolzinaNove + besede[j].length() - dolzinaStare;

                    pregledano = false;

                    if (j != besede.length - 1 && zacetkiBesed[j + 1] <= trenutniPos) {
                        premikAktiven = true;
                    }

                } else if (premikAktiven) {

                    if (!pregledano) {
                        for (int n = j; n < besede.length; n++) {
                            cistaBeseda = odstraniLocila(besede[n]);
                            if (dodatniZamik + trenutniPos >= zacetkiBesed[n]) {
                                if (cistaBeseda.equals(staraBeseda)) {
                                    bufferBesede = besedeVBuffer(bufferBesede, novaBeseda + (cistaBeseda.equals(besede[n]) ? "" : besede[n].charAt(dolzinaStare)));
                                    dodatniZamik += dolzinaNove + 1 + besede[n].length() - dolzinaStare;
                                } else {
                                    bufferBesede = besedeVBuffer(bufferBesede, besede[n]);
                                    dodatniZamik += besede[n].length() + 1;
                                }
                                j = steviloBesed;
                            } else if (j == n) {
                                j--;
                                break;
                            } else {
                                j = n - 1;
                                break;
                            }
                        }
                        pregledano = true;
                    }

                    for (int x = bufferIndex; x < bufferBesede.length; x++) {
                        String trenutnaBeseda = bufferBesede[bufferIndex];

                        int dodatenPresledek = (trenutniPos == 0 ? 0 : 1);
                        int novaPozicija = trenutniPos + trenutnaBeseda.length() + dodatenPresledek;

                        if (novaPozicija <= sirina) {
                            novaVrstica.append(povniloCrk(dodatenPresledek, '_'))
                                    .append(trenutnaBeseda);

                            trenutniPos = novaPozicija;

                            dodatniZamik -= trenutnaBeseda.length() + 1;
                            dodatniZamik = Math.max(dodatniZamik, 0);

                            bufferIndex++;
                        } else {
                            break;
                        }
                    }

                    premikAktiven = dodatniZamik != 0;

                } else {
                    novaVrstica.append(povniloCrk(zacetkiBesed[j] - trenutniPos, '_'))
                            .append(besede[j]);

                    trenutniPos = zacetkiBesed[j] + besede[j].length();
                }
            }

            novaVrstica.append(povniloCrk(sirina - novaVrstica.length(), '_'));
            tabela[i] = novaVrstica.toString().toCharArray();
        }

        if (bufferIndex != bufferBesede.length) {
            System.out.println("Napaka: premajhne dimenzije strani.");
            return null;
        }

        return tabela;
    }

    static  char[][] spremembaDimenzij(int novaSirina, int novaVisina) {
        int steviloVrstic = tabela.length;
        int zadnjaCrkaPos = 0;

        String[] bufferBesede = new String[0];
        int bufferIndex = 0;

        char[][] spremembaDimenzijTabela = new char[novaVisina][];
        boolean izvenDimenzij = false;

        for (int x = steviloVrstic - 1; x > -1; x--) {
            String[] besede = posamezneBesedeIzVrstice(tabela[x]);
            if (besede.length == 0) continue;

            int[] zacetkiBesed = zacetkiBesedIndex(tabela[x], besede.length);
            zadnjaCrkaPos = Math.max(zadnjaCrkaPos, zacetkiBesed[besede.length - 1] + besede[besede.length - 1].length());


            for (int p = besede.length - 1; p > -1; p--) {
                bufferBesede = besedeVBuffer(bufferBesede, besede[p], true);
            }

            if (zadnjaCrkaPos > novaSirina ||  x >= novaVisina) {
                izvenDimenzij = true;
            }

            bufferBesede = izvenDimenzij ? bufferBesede : new String[0];
        }

        for (int i = 0; i < novaVisina; i++) {
            int trenutniPos = 0;

            StringBuilder vrstica = new StringBuilder();

            if (izvenDimenzij) {
                for (int x = bufferIndex; x < bufferBesede.length; x++) {
                    String trenutnaBeseda = bufferBesede[bufferIndex];

                    int dodatenPresledek = (trenutniPos == 0 ? 0 : 1);
                    int novaPozicija = trenutniPos + trenutnaBeseda.length() + dodatenPresledek;

                    if (novaPozicija <= novaSirina) {
                        vrstica.append(povniloCrk(dodatenPresledek, '_'))
                                .append(trenutnaBeseda);

                        trenutniPos = novaPozicija;

                        bufferIndex++;
                    } else {
                        break;
                    }
                }
            } else {
                String[] besede = i < steviloVrstic ? posamezneBesedeIzVrstice(tabela[i]) : new String[0];
                int[] zacetkiBesed = i < steviloVrstic ? zacetkiBesedIndex(tabela[i], besede.length) : new int[0];

                for (int j = 0; j < besede.length; j++) {
                    vrstica.append(povniloCrk(zacetkiBesed[j] - trenutniPos, '_'))
                            .append(besede[j]);
                    trenutniPos = zacetkiBesed[j] + besede[j].length();
                }
            }
            vrstica.append(povniloCrk(novaSirina - vrstica.length(), '_'));
            spremembaDimenzijTabela[i] = vrstica.toString().toCharArray();
        }

        if (bufferIndex != bufferBesede.length) {
            System.out.println("Zmanjšanje polja ni možno");
            return null;
        }

        return spremembaDimenzijTabela;
    }

    static void izpisiStatistikeNizov(char[][] tabela, char[][] nizi) {
        HashMap<String, ArrayList<int[]>> pozicijeBesed = new HashMap<>();
        boolean najdelBesede = false;

        for (char[] crke: nizi) {
            pozicijeBesed.put(new String(crke), new ArrayList<>());
        }

        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[0].length; j++) {
                for (char[] crke: nizi) {
                    for (int x = 0; x < crke.length; x++) {
                        if (j + x >= tabela[0].length || crke[x] != tabela[i][j + x]) {
                            break;
                        } else if (x == crke.length - 1) {
                            String beseda = new String(crke);
                            pozicijeBesed.get(beseda).add(new int[]{i, j});
                        }
                    }
                }
            }
        }

        for (char[] crke: nizi) {
            String beseda = new String(crke);
            ArrayList<int[]> pozicije = pozicijeBesed.get(beseda);

            if (pozicije.isEmpty()) continue;

            najdelBesede = true;
            System.out.print(beseda + ": ");

            for (int poz = 0; poz < pozicije.size(); poz++) {
                System.out.print(Arrays.toString(pozicije.get(poz)) + (poz != pozicije.size() - 1 ? ", " : ""));
            }

            System.out.println();
        }

        if (!najdelBesede) {
            System.out.println("V besedilu ni podanih nizov.");
        }
    }

    static char[][] navpicnoBesedilo(char[][] tabela) {
        int steviloVrstic = tabela.length;
        int dolzinaVrstice = tabela[0].length;
        char[][] res = new char[dolzinaVrstice][];
        ArrayList<Integer> prostaMesta = new ArrayList<>();


        for (int i = 0; i < dolzinaVrstice; i++) {
            StringBuilder vrstica = new StringBuilder();
            for (int j = 0; j < steviloVrstic; j++) {
                vrstica.append(tabela[j][i])
                        .append(j != steviloVrstic - 1 ? "_" : "");

            }
            res[i] = vrstica.toString().toCharArray();
        }

        for (int i = 1; i < res[0].length; i+=2) {
            for (int j = 0; j < res.length; j++) {
                if (res[j][i] != '_') {
                    break;
                } else if (res[j][Math.min(res[0].length - 1, i + 1)] != '_' && res[j][Math.max(0, i - 1)] != '_') {
                    break;
                } else if (j == res.length - 1) {
                    prostaMesta.add(i);
                }
            }
        }

        char[][] finalRes = new char[res.length][];

        for (int i = 0; i < res.length; i++) {
            StringBuilder vrstica = new StringBuilder();
            for (int j = 0; j < res[i].length; j++) {
                if (!prostaMesta.contains(j)) {
                    vrstica.append(res[i][j]);
                }
            }
            finalRes[i] = vrstica.toString().toCharArray();
        }

        return finalRes;
    }


    // pomožne funkcije
    static void izpisRezultata(char[][] rezultat) {
        if (rezultat == null) return;
        for (char[] vrstica: rezultat) {
            System.out.println(new String(vrstica));
        }
    }

    static String povniloCrk(int steviloPraznihMest, char povnilo) {
        StringBuilder praznaVrstica = new StringBuilder();

        for (int i = 0; i < steviloPraznihMest; i++) {
            praznaVrstica.append(povnilo);
        }

        return praznaVrstica.toString();
    }

    static String besedeIzVrstice(char[] vrstica) {
        StringBuilder besedilo = new StringBuilder();
        char prejsnaCrka = ' ';
        boolean novaBeseda = false;

        for (char c : vrstica) {
            if (c == '_') {
                if (!besedilo.isEmpty()) {
                    novaBeseda = true;
                }
            } else {
                if (novaBeseda && !besedilo.isEmpty()) {
                    besedilo.append('_');
                }
                besedilo.append(c);
                prejsnaCrka = c;
                novaBeseda = false;
            }
        }

        return besedilo.toString();
    }

    static String[] posamezneBesedeIzVrstice(char[] vrstica) {
        String skupajBesede = besedeIzVrstice(vrstica);
        if (skupajBesede.isEmpty()) return new String[0];
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

    static int[] vstaviPolozajSlike(int[] zacetkiBesed, int zacetekSlike) {
        int[] polozajElementov = new int[zacetkiBesed.length + 1];
        boolean vstavljenaSlika = false;

        for (int i = 0; i < zacetkiBesed.length; i++) {
            if (zacetkiBesed[i] >= zacetekSlike && !vstavljenaSlika) {
                polozajElementov[i] = zacetekSlike;
                vstavljenaSlika = true;
            }
            polozajElementov[vstavljenaSlika ? i+1 : i] = zacetkiBesed[i];
        }

        if (!vstavljenaSlika) {
            polozajElementov[zacetkiBesed.length] = zacetekSlike;
        }

        return polozajElementov;
    }

    static int[] zacetkiBesedIndex(char[] vrstica, int steviloBesed) {
        int[] zacetkiBesed = new int[steviloBesed];
        int indeksBesede = 0;

        for (int i = 0; i < vrstica.length && indeksBesede < steviloBesed; i++) {
            boolean zacetekBesede = vrstica[i] != '_' && (i == 0 || vrstica[i - 1] == '_');
            if (zacetekBesede) {
                zacetkiBesed[indeksBesede++] = i;
            }
        }

        return zacetkiBesed;
    }

    static String odstraniLocila(String vhodnaBeseda) {
        StringBuilder rezultat = new StringBuilder();

        for (char znak : vhodnaBeseda.toCharArray()) {
            if (znak != '.' && znak != '?' && znak != '!') {
                rezultat.append(znak);
            }
        }

        return rezultat.toString();
    }

    static String[] besedeVBuffer(String[] besedeBuffer, String novaBeseda) {
        String[] noveBesedeBuffer = new String[besedeBuffer.length + 1];

        for (int i = 0; i < besedeBuffer.length; i++) {
            noveBesedeBuffer[i] = besedeBuffer[i];
        }
        noveBesedeBuffer[besedeBuffer.length] = novaBeseda;

        return noveBesedeBuffer;
    }

    static String[] besedeVBuffer(String[] besedeBuffer, String novaBeseda, boolean naZacetek) {
        if (!naZacetek) {
            return besedeVBuffer(besedeBuffer, novaBeseda);
        }

        String[] noveBesedeBuffer = new String[besedeBuffer.length + 1];
        noveBesedeBuffer[0] = novaBeseda;

        for (int i = 0; i < besedeBuffer.length; i++) {
            noveBesedeBuffer[i + 1] = besedeBuffer[i];
        }

        return noveBesedeBuffer;
    }
}