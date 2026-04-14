public class BinarnoIskalnoDrevo {
    private Vozlisce koren;

    public Vozlisce vrniKoren() {
        return this.koren;
    }

    void dodaj(int stevilo) {
        if (koren == null) {
            koren = new Vozlisce(stevilo);
            return;
        }

        Vozlisce trenutno = koren;

        while (true) {
            if (stevilo < trenutno.vrniVrednost()) {
                if (trenutno.vrniLevoPoddrevo() == null) {
                    trenutno.dodajLevo(new Vozlisce(stevilo));
                    break;
                }
                trenutno = trenutno.vrniLevoPoddrevo();
            } else if (stevilo > trenutno.vrniVrednost()) {
                if (trenutno.vrniDesnoPoddrevo() == null) {
                    trenutno.dodajDesno(new Vozlisce(stevilo));
                    break;
                }
                trenutno = trenutno.vrniDesnoPoddrevo();
            } else {
                break; // duplikati
            }
        }
    }

    void dodajR(int stevilo) {
        koren = dodajRekurzivno(koren, stevilo);
    }

    private Vozlisce dodajRekurzivno(Vozlisce trenutno, int stevilo) {
        if (trenutno == null) {
            return new Vozlisce(stevilo);
        }

        if (stevilo < trenutno.vrniVrednost()) {
            trenutno.dodajLevo(dodajRekurzivno(trenutno.vrniLevoPoddrevo(), stevilo));
        } else if (stevilo > trenutno.vrniVrednost()) {
            trenutno.dodajDesno(dodajRekurzivno(trenutno.vrniDesnoPoddrevo(), stevilo));
        }

        return trenutno;
    }

    static BinarnoIskalnoDrevo zgradiDrevo(String imeDatoteke) {
        BinarnoIskalnoDrevo drevo = new BinarnoIskalnoDrevo();
        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(imeDatoteke));
            while (sc.hasNextInt()) {
                drevo.dodajR(sc.nextInt());
            }
        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return drevo;
    }

    boolean poisci(int stevilo) {
        return poisciR(koren, stevilo);
    }

    boolean poisciR(Vozlisce trenutno, int stevilo) {
        if (trenutno == null) return false;
        if (trenutno.vrniVrednost() == stevilo) return true;

        if (stevilo < trenutno.vrniVrednost()) {
            return poisciR(trenutno.vrniLevoPoddrevo(), stevilo);
        } else {
            return poisciR(trenutno.vrniDesnoPoddrevo(), stevilo);
        }
    }

    int vrniNajmanjsoVrednost() {
        return vrniNajmanjsoVrednostR(koren);
    }

    int vrniNajmanjsoVrednostR(Vozlisce trenutno) {
        if (trenutno == null) return -1;

        if (trenutno.vrniLevoPoddrevo() == null) {
            return trenutno.vrniVrednost();
        }
        return vrniNajmanjsoVrednostR(trenutno.vrniLevoPoddrevo());
    }

    void izpisi(int nacin) {
        if (nacin == 0) {
            System.out.println("Infiksna oblika:");
        } else if (nacin == 1) {
            System.out.println("Prefiksna oblika:");
        } else {
            System.out.println("Postfiksna oblika:");
        }

        izpisiR(koren, nacin);
        System.out.println();
    }

    private void izpisiR(Vozlisce trenutno, int nacin) {
        if (trenutno == null) return;

        if (nacin == 1) System.out.print(trenutno.vrniVrednost() + "  "); // Prefiksni (V-L-D)

        izpisiR(trenutno.vrniLevoPoddrevo(), nacin);

        if (nacin == 0) System.out.print(trenutno.vrniVrednost() + "  "); // Infiksni (L-V-D)

        izpisiR(trenutno.vrniDesnoPoddrevo(), nacin);

        if (nacin == 2) System.out.print(trenutno.vrniVrednost() + "  "); // Postfiksni (L-D-V)
    }
}