package kodirniki;

import java.io.*;
import izjeme.IzjemaDatoteke;

public class Kodiranje {
    Kodirnik kodirnik;

    public Kodiranje(Kodirnik kodirnik) {
        this.kodirnik = kodirnik;
    }

    public String zakodiranjeBesedila(String besedilo) {
        StringBuilder zakodiranoBesedilo = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            int zakodiranaCrka = kodirnik.zakodiraj((int) c);
            zakodiranoBesedilo.append((char) zakodiranaCrka);
        }

        return zakodiranoBesedilo.toString();
    }

    public String odkodiranjeBesedila(String besedilo) {
        StringBuilder odkodiranoBesedilo = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            int odkodiranaCrka = kodirnik.odkodiraj((int) c);
            odkodiranoBesedilo.append((char) odkodiranaCrka);
        }

        return odkodiranoBesedilo.toString();
    }

    private void preveriDatoteke(String vhodna, String izhodna, boolean jeBinarna) throws IzjemaDatoteke {
        File vh = new File(vhodna);
        File izh = new File(izhodna);

        if (!vh.exists()) {
            throw new IzjemaDatoteke("Vhodne datoteke '" + vhodna + "' ni mogoče najti.");
        }

        if (jeBinarna && !vh.canRead()) {
            throw new IzjemaDatoteke("Za binarno datoteko '" + vhodna + "' ni ustreznega dovoljenja za dostop.");
        }
    }

    public void zakodiranjeTekstovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        try (FileReader fr = new FileReader(new File(vhodnaDatoteka)); FileWriter fw = new FileWriter(new File(izhodnaDatoteka))) {
            int trenutenPodatek;

            while ((trenutenPodatek = fr.read()) != -1) {
                fw.write(kodirnik.zakodiraj(trenutenPodatek));
            }

        } catch (IOException e) {
            throw new IzjemaDatoteke("Pri branju/pisanju iz/v tekstovno datoteko je prišlo do napake.");
        }
    }

    public void odkodiranjeTekstovneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        try (FileReader fr = new FileReader(new File(vhodnaDatoteka)); FileWriter fw = new FileWriter(new File(izhodnaDatoteka))) {
            int trenutenPodatek;

            while ((trenutenPodatek = fr.read()) != -1) {
                fw.write(kodirnik.odkodiraj(trenutenPodatek));
            }

        } catch (IOException e) {
            throw new IzjemaDatoteke("Pri branju/pisanju iz/v tekstovno datoteko je prišlo do napake.");
        }
    }

    public void zakodiranjeBinarneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        try (FileInputStream fr = new FileInputStream(new File(vhodnaDatoteka)); FileOutputStream fw = new FileOutputStream(new File(izhodnaDatoteka))) {
            int trenutenPodatek;

            while ((trenutenPodatek = fr.read()) != -1) {
                fw.write(kodirnik.zakodiraj(trenutenPodatek));
            }

        } catch (IOException e) {
            throw new IzjemaDatoteke("Pri branju/pisanju iz/v tekstovno datoteko je prišlo do napake.");
        }
    }

    public void odkodiranjeBinarneDatoteke(String vhodnaDatoteka, String izhodnaDatoteka) throws IzjemaDatoteke {
        try (FileInputStream fr = new FileInputStream(new File(vhodnaDatoteka)); FileOutputStream fw = new FileOutputStream(new File(izhodnaDatoteka))) {
            int trenutenPodatek;

            while ((trenutenPodatek = fr.read()) != -1) {
                fw.write(kodirnik.odkodiraj(trenutenPodatek));
            }

        } catch (IOException e) {
            throw new IzjemaDatoteke("Pri branju/pisanju iz/v tekstovno datoteko je prišlo do napake.");
        }
    }
}
