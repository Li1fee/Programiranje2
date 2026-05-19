package kriptoborza;

import edu.princeton.cs.algs4.StdDraw;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

public class Borza {
    private String naziv;
    private ArrayList<Valuta> valute;
    private TreeMap<String, MenjalnoRazmerje> tecajnica;
    private ArrayList<Transakcija> zgodovina;
    private Map<String, Denarnica> denarnice;
    private HashMap<String, HashSet<String>> graf;

    public Borza(String naziv) {
        this.naziv = naziv;
        this.valute = new ArrayList<>();
        this.tecajnica = new TreeMap<>();
        this.zgodovina = new ArrayList<>();
        this.denarnice = new HashMap<>();
        this.graf = new HashMap<>();
    }

    public boolean preberiValute(String vir) {
        try (Scanner sc = new Scanner(new File(vir))) {
            HashSet<String> prebraneValute = new HashSet<>();

            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().split(";");
                if (vrstica.length < 4) continue;


                String vrsta = vrstica[0];
                String nazivValute = vrstica[1];
                String oznaka = vrstica[2];

                if (!prebraneValute.contains(oznaka)) {
                    if (vrsta.equals("K")) {
                        String algoritem = vrstica[3];
                        long trenutnaZaloga = Long.parseLong(vrstica[4]);
                        valute.add(new KriptoValuta(nazivValute, oznaka, algoritem, trenutnaZaloga));

                    } else if (vrsta.equals("F")) {
                        String drzavaIzdajateljica = vrstica[3];
                        valute.add(new FiatValuta(nazivValute, oznaka, drzavaIzdajateljica));

                    } else {
                        continue;
                    }
                    prebraneValute.add(oznaka);
                }
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public void izpisiValute() {
        System.out.printf("Na borzi '%s' so naslednje valute (%d):\n", naziv, valute.size());

        for (Valuta valuta : valute) {
            System.out.printf(" - %s\n", valuta.toString());
        }
    }

    public boolean preberiTecajnico(String vir) {
        try (Scanner sc = new Scanner(new File(vir))) {
            HashSet<String[]> prebraniTecaji = new HashSet<>();

            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().split("\\s+");
                if (vrstica.length < 3 || vrstica.length > 4) continue;

                String oznakaIzvor = vrstica[0];
                String oznakaCilj = vrstica[1];

                Valuta vIzvor = najdiValuto(oznakaIzvor);
                Valuta vCilj = najdiValuto(oznakaCilj);

                if (vIzvor == null || vCilj == null) continue;

                String key = String.format("%s:%s", oznakaIzvor, oznakaCilj);

                if (tecajnica.containsKey(key)) continue;

                tecajnica.put(key,
                        new MenjalnoRazmerje(vIzvor,
                            vCilj,
                            Double.parseDouble(vrstica[2]),
                            vrstica.length == 4 ? Double.parseDouble(vrstica[3]) : 0.0
                ));
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private Valuta najdiValuto(String oznaka) {
        for (Valuta valuta : valute) {
            if (valuta.getOznaka().equals(oznaka)) {
                return valuta;
            }
        }
        return null;
    }

    public void dodajPar(MenjalnoRazmerje menjalnoRazmerje) {
        tecajnica.put(menjalnoRazmerje.vrniMenjalniPar(), menjalnoRazmerje);
    }

    public void izpisiTecajnico() {
        System.out.printf("Menjalna razmerja na borzi '%s':\n", naziv);

        for (MenjalnoRazmerje mr : tecajnica.values()) {
            System.out.printf(" - %s\n", mr.toString());
        }
    }

    public boolean preberiTransakcije(String vir) {
        int i = 0;
        try (Scanner sc = new Scanner(new File(vir))) {
            while (sc.hasNextLine()) {
                String[] vrstica = sc.nextLine().split(",");
                if (vrstica.length != 5) continue;

                zgodovina.add(new Transakcija(i++,
                        vrstica[0],
                        vrstica[1],
                        vrstica[2],
                        Double.parseDouble(vrstica[3]),
                        vrstica[4]
                ));
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public void dodajTransakcijo(String izvor, String cilj, String valuta, double znesek, String cas) {
        int novId = zgodovina.size() + 1;
        zgodovina.add(new Transakcija(novId, izvor, cilj, valuta, znesek, cas));
    }

    public void izpisiTransakcije() {
        for (Transakcija transakcija : zgodovina) {
            System.out.println(transakcija.toString());
        }
    }

    public void ustvariDenarnice() {
        for (Transakcija transakcija : zgodovina) {
            String idCilja = transakcija.getCilj();

            if (!denarnice.containsKey(idCilja)) {
                denarnice.put(idCilja, new Denarnica(idCilja));
            }

            Denarnica d = denarnice.get(idCilja);
            d.dodaj(transakcija.getValuta(), transakcija.getZnesek());
        }
    }

    public void izpisiStanjeDenarnice(String idDenarnice) {
        if (denarnice.containsKey(idDenarnice)) {
            denarnice.get(idDenarnice).izpisiStanje();
        } else {
            System.out.println("Denarnica ne obstaja!.");
        }
    }

    public boolean izvediTransakcijo(Denarnica izvorna, Denarnica ciljna, String valuta, double znesek) {
        if (!izvorna.odstej(valuta, znesek)) {
            return false;
        }
        ciljna.dodaj(valuta, znesek);

        String cas = LocalDateTime.now().toString();
        dodajTransakcijo(izvorna.getId(), ciljna.getId(), valuta, znesek, cas);
        return true;
    }

    public boolean izvediMenjavo(Denarnica denarnica, String izvornaOznaka, String ciljnaOznaka, double kolicina) {
        String key = String.format("%s:%s", izvornaOznaka, ciljnaOznaka);
        if (!tecajnica.containsKey(key) || !denarnica.odstej(izvornaOznaka, kolicina)) {
            return false;
        }

        double pretvorjenKolicina = tecajnica.get(key).pretvori(kolicina);
        denarnica.dodaj(ciljnaOznaka, pretvorjenKolicina);

        String cas = LocalDateTime.now().toString();
        dodajTransakcijo(denarnica.getId(), denarnica.getId(), ciljnaOznaka, kolicina, cas);
        return true;
    }


    public Set<String> poisciCiljneDenarnice(String idDenarnice) {
        HashSet<String> najdeneDenarnice = new HashSet<>();
        najdiVsePoti(idDenarnice, najdeneDenarnice);
        najdeneDenarnice.remove(idDenarnice);
        return najdeneDenarnice;
    }

    public void ustvariGraf() {
        graf.clear();
        for (Transakcija transakcija : zgodovina) {
            String izvor = transakcija.getIzvor();
            String cilj = transakcija.getCilj();
            HashSet<String> mnozica = graf.containsKey(izvor) ? graf.get(izvor) : new HashSet<>();
            mnozica.add(cilj);
            graf.put(izvor, mnozica);
        }
    }

    private void najdiVsePoti(String idDenarnice, Set<String> najdeneDenarince) {
        if (najdeneDenarince.contains(idDenarnice)) {
            return;
        }

        najdeneDenarince.add(idDenarnice);

        for (String id : graf.getOrDefault(idDenarnice, new HashSet<>())) {
            najdiVsePoti(id, najdeneDenarince);
        }
    }

    public void izrisiGraf(String idDenarnice) {
        ustvariGraf();
        Set<String> dosegljive = poisciCiljneDenarnice(idDenarnice);
        Set<String> vsaVozlisca = pridobiVsaVozlisca();
        Map<String, double[]> koordinate = razporediVozlisca(vsaVozlisca);

        StdDraw.clear();

        StdDraw.setCanvasSize(800, 800);
        StdDraw.clear(StdDraw.WHITE);

        for (String izvor : graf.keySet()) {
            for (String cilj : graf.get(izvor)) {
                double[] s = koordinate.get(izvor);
                double[] k = koordinate.get(cilj);

                if ((izvor.equals(idDenarnice) || dosegljive.contains(izvor)) && dosegljive.contains(cilj)) {
                    StdDraw.setPenColor(StdDraw.CYAN);
                } else {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                }
                StdDraw.setPenRadius(0.01);
                StdDraw.line(s[0], s[1], k[0], k[1]);
            }
        }

        for (String v : vsaVozlisca) {
            double[] p = koordinate.get(v);

            if (v.equals(idDenarnice) || dosegljive.contains(v)) {
                StdDraw.setPenColor(StdDraw.CYAN);
            } else {
                StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
            }
            StdDraw.setPenRadius(0.005);
            StdDraw.filledCircle(p[0], p[1], 0.05);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(p[0], p[1], 0.05);
            StdDraw.text(p[0], p[1], v);
        }
    }

    public Set<String> pridobiVsaVozlisca() {
        Set<String> vsaVozlisca = new HashSet<>();

        for (Map.Entry<String, HashSet<String>> vozlisce : graf.entrySet()) {
            vsaVozlisca.add(vozlisce.getKey());
            vsaVozlisca.addAll(vozlisce.getValue());
        }

        return vsaVozlisca;
    }

    public Map<String, double[]> razporediVozlisca(Set<String> vsaVozlisca) {
        HashMap<String, double[]> razporejenaVozlisca = new HashMap<>();
        int i = 0;
        int n = vsaVozlisca.size();

        for (String vozlisce : vsaVozlisca) {
            double kot = 2 * Math.PI * i++ / n;

            double x_i = 0.5 + 0.35 * Math.cos(kot);
            double y_i = 0.5 + 0.35 * Math.sin(kot);

            razporejenaVozlisca.put(vozlisce, new double[]{x_i, y_i});
        }
        return razporejenaVozlisca;
    }
}
