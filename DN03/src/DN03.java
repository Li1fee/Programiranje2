import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class DN03 {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        int dolzinaGesla = Integer.parseInt(args[1]);
        int seed = Integer.parseInt(args[2]);

        Scanner sc = new Scanner(new File(fileName));
        int stBesed = Integer.parseInt(sc.nextLine());
        String[] besede = new String[stBesed];

        for (int i = 0; i < stBesed; i++) {
            besede[i] = sc.nextLine();
        }
        sc.close();

        StringBuilder geslo = new StringBuilder();
        Random rnd = new Random(seed);

        for (int i = 0; i < dolzinaGesla; i++) {
            String randomBeseda = besede[rnd.nextInt(stBesed)];
            char randomCrka = randomBeseda.charAt(rnd.nextInt(randomBeseda.length()));
            geslo.append(randomCrka);
        }

        System.out.println(geslo);
    }
}