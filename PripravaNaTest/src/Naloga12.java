import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Naloga12 {
    public static void main(String[] args) {
        if (args.length != 1) return;

        TreeMap<String, ArrayList<String>> kraji = new TreeMap<>();
        boolean zamenjava = false;

        try (Scanner sc = new Scanner(new File(args[0]))) {
            while (sc.hasNextLine()) {
                String vrstica = sc.nextLine();

                if (vrstica.isEmpty()) {
                    zamenjava = true;
                    continue;
                }

                if (!zamenjava) {
                    String[] splitVrstica = vrstica.split(";");
                    kraji.put(splitVrstica[0], new ArrayList<>());
                } else {
                    String[] splitVrstica = vrstica.split(";");

                    ArrayList<String> mesta1 = kraji.get(splitVrstica[0]);
                    mesta1.add(splitVrstica[1]);
                    kraji.put(splitVrstica[0], mesta1);

                    ArrayList<String> mesta2 = kraji.get(splitVrstica[1]);
                    mesta2.add(splitVrstica[0]);
                    kraji.put(splitVrstica[1], mesta2);
                }
            }
        } catch (FileNotFoundException e) {
            return;
        }
        System.out.println("Prebrani kraji in povezave:");
        for (Map.Entry<String, ArrayList<String>> kraj : kraji.entrySet()) {
            System.out.printf("  %s --> ", kraj.getKey());
            Collections.sort(kraj.getValue());
            System.out.printf("%s\n", String.join(", ", kraj.getValue()));
        }
    }
}
