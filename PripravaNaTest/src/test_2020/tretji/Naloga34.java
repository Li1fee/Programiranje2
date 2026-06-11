package test_2020.tretji;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Naloga34 {
    public static void main(String[] args) {
        if (args.length != 2) return;
        String imeDatotekeDrzave = args[0];
        String imeDatotekeMesta = args[1];

        ArrayList<Drzava> vseDrzave = new ArrayList<>();

        HashMap<String, String[]> drzave = new HashMap<>();

        try (Scanner sc = new Scanner(new File(imeDatotekeDrzave))) {
            while (sc.hasNextLine()) {
                String[] drzava = sc.nextLine().split(";");
                drzave.put(drzava[0], new String[]{drzava[1], drzava[2]});
            }
        } catch (FileNotFoundException e) {
            return;
        }

        try (Scanner sc = new Scanner(new File(imeDatotekeMesta))) {
            while (sc.hasNextLine()) {
                String[] mesto = sc.nextLine().split(";");
                vseDrzave.add(new Drzava(
                        drzave.get(mesto[0])[0],
                        mesto[1],
                        Integer.parseInt(drzave.get(mesto[0])[1]),
                        Integer.parseInt(mesto[2])
                        ));
            }
        } catch (FileNotFoundException e) {
            return;
        }

        Collections.sort(vseDrzave);

        for (Drzava drzava : vseDrzave) {
            System.out.printf("%s;%s;%.1f\n", drzava.getIme(), drzava.getMesto(), drzava.getRatio());
        }
    }
}

class Drzava implements Comparable<Drzava> {
    private final String ime;
    private final String mesto;
    private final int prebivalciDrzava;
    private final int prebivalciMesto;

    public Drzava(String ime, String mesto, int prebivalciDrzava, int prebivalciMesto) {
        this.ime = ime;
        this.mesto = mesto;
        this.prebivalciDrzava = prebivalciDrzava;
        this.prebivalciMesto = prebivalciMesto;
    }

    public String getIme() {
        return ime;
    }

    public String getMesto() {
        return mesto;
    }

    public float getRatio() {
        return ((float) prebivalciMesto / prebivalciDrzava) * 100;
    }

    @Override
    public int compareTo(Drzava druga) {
        return Float.compare(druga.getRatio(), this.getRatio());
    }
}