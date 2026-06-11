package test_2020.drugi;

import java.io.*;

public class Naloga23 {
    public static void main(String[] args) {
        if (args.length != 2) return;
        String imeVhodneDatoteke = args[0];
        String imeIzhodneDatoteke = args[1];

        StringBuilder izpis = new StringBuilder();
        izpis.append("year month day hour minute latitude  longitude\n");
        try (DataInputStream ds = new DataInputStream(new FileInputStream(imeVhodneDatoteke))) {
            while (true) {
                int leto = ds.readInt();
                int mesec = ds.readInt();
                int dan = ds.readInt();
                int ura = ds.readInt();
                int minuta = ds.readInt();
                float lat = ds.readFloat();
                float lon = ds.readFloat();

                izpis.append(String.format("%-4d %-5d %-3d %-4d %-6d %.6f %.6f\n", leto, mesec, dan, ura, minuta, lat, lon));
            }
        } catch (Exception e) {
        }

        try (PrintWriter pw = new PrintWriter(new File(imeIzhodneDatoteke))) {
            pw.println(izpis.toString());
        } catch (FileNotFoundException e) {
            return;
        }
    }
}
