import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
                        Integer.parseInt(args[5])
                );
                izpisRezultat(rezultat);
            }
            return;

        } else if (args.length == 4 && "zamenjaj".equals(args[0])) {
            char[][] tabela = preberiUrejenoDatoteko(args[1]);
            if (tabela != null) {
                char[][] rezultat = zamenjajBesedo(tabela,
                    args[2],
                    args[3]
                );
                izpisRezultat(rezultat);
            }
            return;

        }
        else if (args.length == 2 && "navpicno".equals(args[1])) {
            char[][] tabela = preberiUrejenoDatoteko(args[0]);
            if (tabela != null) {
                char[][]rezultat = navpicnoBesedilo(tabela);
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
        if (!besedilo.isEmpty() && besedilo.charAt(besedilo.length() - 1) == '_') {
            return besedilo.substring(0, besedilo.length() - 1);
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
    static char[][] vstaviSliko(char[][] tabela, int x, int y, int sirinaSlike, int visinaSlike) {
        int steviloVrstic = tabela.length;
        int dolzinaVrstice = tabela[0].length;

        ArrayList<String> preostaleBesede = new ArrayList<>();
        int indeksPreostalih = 0;

        for (int vrsticaIndex = y; vrsticaIndex < steviloVrstic; vrsticaIndex++) {
            String[] besede = posamezneBesedeIzVrstice(tabela[vrsticaIndex]);
            StringBuilder novaVrstica = new StringBuilder();

            int[] zacetkiBesed = new int[besede.length + 1];
            zacetkiBesed[besede.length] = -1;

            int indeksBesede = 0;

            String vrsticaZMejo = "_" + new String(tabela[vrsticaIndex]);
            for (int i = 1; i < vrsticaZMejo.length(); i++) {
                if (vrsticaZMejo.charAt(i - 1) == '_' && vrsticaZMejo.charAt(i) != '_') {
                    zacetkiBesed[indeksBesede++] = i - 1;
                }
            }

            indeksBesede = 0;

            for (int stolpec = 0; stolpec < dolzinaVrstice; stolpec++) {
                boolean jeVSlici = stolpec >= x && stolpec < x + sirinaSlike && vrsticaIndex < y + visinaSlike;
                boolean jeDesnoOdSlike = stolpec >= x + sirinaSlike || vrsticaIndex >= y + visinaSlike;

                if (jeVSlici) {
                    novaVrstica.append("#");

                    if (stolpec == zacetkiBesed[indeksBesede]) {
                        preostaleBesede.add(besede[indeksBesede++]);
                    }

                } else {
                    if (indeksPreostalih == preostaleBesede.size()) {

                        if (stolpec == zacetkiBesed[indeksBesede]) {
                            int dolzinaBesede = besede[indeksBesede].length();

                            if (dolzinaBesede + stolpec <= x && vrsticaIndex < y + visinaSlike) {
                                stolpec += dolzinaBesede - 1;
                                novaVrstica.append(besede[indeksBesede++]);

                                if (stolpec + 1 < x) {
                                    novaVrstica.append("_");
                                    stolpec++;
                                }

                            } else if (jeDesnoOdSlike && dolzinaBesede + stolpec <= dolzinaVrstice) {
                                stolpec += dolzinaBesede - 1;
                                novaVrstica.append(besede[indeksBesede++]);

                                if (stolpec + 1 < dolzinaVrstice) {
                                    novaVrstica.append("_");
                                    stolpec++;
                                }

                            } else {
                                preostaleBesede.add(besede[indeksBesede++]);

                                int kolikoDodati = stolpec < x ? x - stolpec : dolzinaVrstice - stolpec;
                                novaVrstica.append("_".repeat(kolikoDodati));
                                stolpec += kolikoDodati - 1;
                            }

                        } else {
                            novaVrstica.append("_");
                        }

                    } else {
                        String trenutnaBeseda = preostaleBesede.get(indeksPreostalih);
                        int dolzinaBesede = trenutnaBeseda.length();

                        if (dolzinaBesede + stolpec <= x && vrsticaIndex < y + visinaSlike) {

                            for (int poz : zacetkiBesed) {
                                if (poz >= stolpec && poz < dolzinaBesede + stolpec) {
                                    preostaleBesede.add(besede[indeksBesede++]);
                                }
                            }

                            stolpec += dolzinaBesede - 1;
                            novaVrstica.append(trenutnaBeseda);
                            indeksPreostalih++;

                            if (stolpec + 1 < x) {
                                if (stolpec + 1 == zacetkiBesed[indeksBesede]) {
                                    preostaleBesede.add(besede[indeksBesede++]);
                                }
                                novaVrstica.append("_");
                                stolpec++;
                            }

                        } else if (jeDesnoOdSlike && dolzinaBesede + stolpec <= dolzinaVrstice) {

                            for (int poz : zacetkiBesed) {
                                if (poz >= stolpec && poz < dolzinaBesede + stolpec) {
                                    preostaleBesede.add(besede[indeksBesede++]);
                                }
                            }

                            stolpec += dolzinaBesede - 1;
                            novaVrstica.append(trenutnaBeseda);
                            indeksPreostalih++;

                            if (stolpec + 1 < dolzinaVrstice) {
                                if (stolpec + 1 == zacetkiBesed[indeksBesede]) {
                                    preostaleBesede.add(besede[indeksBesede++]);
                                }
                                novaVrstica.append("_");
                                stolpec++;
                            }

                        } else {
                            if (stolpec == zacetkiBesed[indeksBesede]) {
                                preostaleBesede.add(besede[indeksBesede++]);
                            }
                            novaVrstica.append("_");
                        }
                    }
                }
            }

            tabela[vrsticaIndex] = novaVrstica.toString().toCharArray();
        }

        if (preostaleBesede.size() != indeksPreostalih) {
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
        boolean novaDaljsa = dolzinaNove > dolzinaStare;

        ArrayList<String> bufferBesede = new ArrayList<>();
        int bufferIndex = 0;

        int dodatniZamik = 0;

        for (int i = 0; i < stVrstic; i++) {
            StringBuilder novaVrstica = new StringBuilder();
            String[] besede = posamezneBesedeIzVrstice(tabela[i]);

            int trenutniPos = 0;

            int[] zacetkiBesed = new int[besede.length];
            int idx = 0;

            String vrsticaZMejo = "_" + new String(tabela[i]);
            for (int k = 1; k < vrsticaZMejo.length(); k++) {
                if (vrsticaZMejo.charAt(k - 1) == '_' && vrsticaZMejo.charAt(k) != '_') {
                    zacetkiBesed[idx++] = k - 1;
                }
            }

            if (!novaDaljsa) {
                for (int j = 0; j < besede.length; j++) {
                    String cistaBeseda = odstraniLocila(besede[j]);

                    if (cistaBeseda.equals(staraBeseda)) {
                        int razlika = dolzinaStare - dolzinaNove - (besede[j].length() - dolzinaStare);

                        novaVrstica.append("_".repeat(zacetkiBesed[j] - trenutniPos))
                                .append(novaBeseda)
                                .append(besede[j].length() == dolzinaStare ? "" : besede[j].charAt(dolzinaStare))
                                .append("_".repeat(razlika));

                        trenutniPos = zacetkiBesed[j] + dolzinaStare;
                    } else {
                        novaVrstica.append("_".repeat(zacetkiBesed[j] - trenutniPos))
                                .append(besede[j]);

                        trenutniPos = zacetkiBesed[j] + besede[j].length();
                    }
                }

                novaVrstica.append("_".repeat(sirina - trenutniPos));

            } else {
                boolean premikAktiven = bufferIndex != bufferBesede.size();
                boolean pregledano = false;

                for (int j = 0; j < besede.length; j++) {
                    String cistaBeseda = odstraniLocila(besede[j]);

                    if (cistaBeseda.equals(staraBeseda) && !premikAktiven) {

                        int potrebnaSirina = Math.max(trenutniPos, zacetkiBesed[j])
                                + dolzinaNove + besede[j].length() - dolzinaStare;

                        if (potrebnaSirina > sirina) {
                            System.out.println("Napaka: premajhne dimenzije strani.");
                            return null;
                        }

                        novaVrstica.append("_".repeat(zacetkiBesed[j] - trenutniPos))
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
                                        bufferBesede.add(novaBeseda + (cistaBeseda.equals(besede[n]) ? "" : besede[n].charAt(dolzinaStare)));
                                        dodatniZamik += dolzinaNove + (dodatniZamik == 0 ? 0 : 1) + besede[n].length() - cistaBeseda.length();
                                    } else {
                                        bufferBesede.add(besede[n]);
                                        dodatniZamik += besede[n].length() + (dodatniZamik == 0 ? 0 : 1);
                                    }
                                    j = besede.length;
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

                        for (int x = bufferIndex; x < bufferBesede.size(); x++) {
                            String trenutnaBeseda = bufferBesede.get(bufferIndex);

                            int dodatenPresledek = (trenutniPos == 0 ? 0 : 1);
                            int novaPozicija = trenutniPos + trenutnaBeseda.length() + dodatenPresledek;

                            if (novaPozicija <= sirina) {
                                novaVrstica.append("_".repeat(dodatenPresledek))
                                        .append(trenutnaBeseda);

                                trenutniPos = novaPozicija;

                                dodatniZamik -= trenutnaBeseda.length() + 1;
                                dodatniZamik = Math.max(dodatniZamik, 0);

                                bufferIndex++;
                            }
                        }

                        premikAktiven = dodatniZamik != 0;

                    } else {
                        novaVrstica.append("_".repeat(zacetkiBesed[j] - trenutniPos))
                                .append(besede[j]);

                        trenutniPos = zacetkiBesed[j] + besede[j].length();
                    }
                }

                novaVrstica.append("_".repeat(sirina - novaVrstica.length()));
            }

            tabela[i] = novaVrstica.toString().toCharArray();
        }

        if (bufferIndex != bufferBesede.size()) {
            System.out.println("Napaka: premajhne dimenzije strani.");
            return null;
        }

        return tabela;
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
                } else if (res[j][i] != '_' && res[j][Math.min(res[0].length - 1, i + 1)] != '_') {
                    break;
                } else if (j == res.length - 1) {
                    prostaMesta.add(i);
                }
            }
        }

        char[][] trueRes = new char[res.length][];

        for (int i = 0; i < res.length; i++) {
            StringBuilder vrstica = new StringBuilder();
            for (int j = 0; j < res[i].length; j++) {
                if (!prostaMesta.contains(j)) {
                    vrstica.append(res[i][j]);
                }
            }
            trueRes[i] = vrstica.toString().toCharArray();
        }

        return trueRes;
    }
}

// pregled zakaj lahko bypassaš limit na vrstico, zakaj mora biti kdaj max uporabljen, pozabljanje besed