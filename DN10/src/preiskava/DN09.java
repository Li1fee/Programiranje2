package preiskava;

import kriptoborza.Borza;

public class DN10 {
    public static void main(String[] args) {
        Borza borza = new Borza("Temna borza");
        borza.preberiValute("./valute.txt");
        borza.izpisiValute();

        System.out.println();

        borza.preberiTecajnico("./tecaji.txt");
        borza.izpisiTecajnico();
    }
}
