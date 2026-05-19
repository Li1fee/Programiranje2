package preiskava;

import kriptoborza.Borza;
import kriptoborza.Denarnica;

public class DN09 {
    public static void main(String[] args) {
        Borza borza = new Borza("Temna borza");
        borza.preberiValute("./valute.txt");
        borza.izpisiValute();

        System.out.println();

        borza.preberiTecajnico("./tecaji.txt");
        borza.izpisiTecajnico();

        System.out.println();

        borza.preberiTransakcije("./transakcije.txt");
        borza.izpisiTransakcije();

        System.out.println();

        borza.ustvariDenarnice();
        borza.izpisiStanjeDenarnice("ACC2002");

        System.out.println();

        borza.poisciCiljneDenarnice("ACC1001");

        System.out.println();

        borza.izrisiGraf("ACC1001");
    }
}
