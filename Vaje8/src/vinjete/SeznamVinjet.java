package vinjete;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SeznamVinjet {
    private Vinjeta[] prodaneVinjete;

    public SeznamVinjet() {

    }

    public boolean preberiPodatke(String vir) {
        Scanner sc = null;
        try {
            sc = new Scanner(new File(vir));
            int steviloVrstic = Integer.parseInt(sc.nextLine());
            prodaneVinjete = new Vinjeta[steviloVrstic];
            for (int i = 0; i < steviloVrstic; i++) {
                String[] vinjetaPodatki = sc.nextLine().split(";");
                Vinjeta vinjeta = new Vinjeta(vinjetaPodatki[0], vinjetaPodatki[1], vinjetaPodatki[2], vinjetaPodatki[3]);
                prodaneVinjete[i] = vinjeta;
            }

            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public void izpisiVinjete() {
        if (prodaneVinjete == null || prodaneVinjete.length == 0) {
            System.out.println("V sistemu ni vinjet.");
            return;
        }
        System.out.printf("V sistemu so zabeležene prodane vinjete (%d):\n", prodaneVinjete.length);
        for (Vinjeta vinjeta : prodaneVinjete) {
            System.out.println(vinjeta.toString());
        }
    }

    public boolean preveriVinjeto(String registrska) {
        for (Vinjeta vinjeta : prodaneVinjete) {
            if (vinjeta.getRegisterska().equals(registrska)) return true;
        }
        return false;
    }


    public void izpisiVinjete(String vrsta) {
        int count = 0;
        for (Vinjeta vinjeta : prodaneVinjete) {
            if (vinjeta.getVrsta().equals(vrsta)) {
                if (count++ == 0) {
                    System.out.printf("V sistemu je %s vinjeta za naslednja vozila:\n", vrsta);
                }
                System.out.println(vinjeta.toString());
            }
        }
        if (count == 0) {
            System.out.println("Nobena vinjeta ni podane vrste");
        } else {
            System.out.printf("Skupaj - %s vinjeta: %d\n", vrsta ,count);
        }
    }

    public void izpisiVeljavneLetne(String datum) {
        System.out.printf("Letne vinjete, veljavne dne %s (z datumi veljavnosti):\n", datum);
        for (Vinjeta vinjeta : prodaneVinjete) {
            String veljavnaDo = vinjetaVeljavnost(vinjeta.getZacetekVeljavnosti(), datum);
            if (vinjeta.getVrsta().equals("letna") && !veljavnaDo.isEmpty()) {
                System.out.printf("-- %s [%s] (veljavna od %s do %s)\n", vinjeta.getRegisterska(), vinjeta.getRazred(), vinjeta.getZacetekVeljavnosti(), veljavnaDo);
            }
        }
    }

    String vinjetaVeljavnost(String datumVinjete, String datum) {
        String[] dmlV = datumVinjete.split("\\.");
        int danV = Integer.parseInt(dmlV[0]);
        int mesecV = Integer.parseInt(dmlV[1]);
        int letoV = Integer.parseInt(dmlV[2]);

        int startKljuc = (letoV * 10000) + (mesecV * 100) + danV;

        int potekKljuc = ((letoV + 1) * 10000) + (mesecV * 100) + danV;

        String[] dmlD = datum.split("\\.");
        int danD = Integer.parseInt(dmlD[0]);
        int mesecD = Integer.parseInt(dmlD[1]);
        int letoD = Integer.parseInt(dmlD[2]);

        int danesKljuc = (letoD * 10000) + (mesecD * 100) + danD;

        if (danesKljuc >= startKljuc && danesKljuc <= potekKljuc) {
            return String.format("%02d.%02d.%04d", danV, mesecV, letoV + 1);
        }

        return "";
    }

    public void statistika() {
        HashMap<String, HashMap<String, Integer>> razrediProdanost = new HashMap<>();

        for (Vinjeta vinjeta : prodaneVinjete) {
            razrediProdanost.putIfAbsent(vinjeta.getRazred(), new HashMap<>());
            HashMap<String, Integer> vrste = razrediProdanost.get(vinjeta.getRazred());
            vrste.put(vinjeta.getVrsta(), vrste.getOrDefault(vinjeta.getVrsta(), 0) + 1);
        }

        System.out.println("Največ prodanih vinjet po razredih.");

        for (String razred : razrediProdanost.keySet()) {
            ArrayList<String> vrsteImena = new ArrayList<>();
            HashMap<String, Integer> vrste = razrediProdanost.get(razred);
            int max = 0;

            for (Map.Entry<String, Integer> entry : vrste.entrySet()) {
                if (entry.getValue() == max) {
                    vrsteImena.add(entry.getKey());
                }
                if (entry.getValue() > max)  {
                    max = entry.getValue();
                    vrsteImena.clear();
                    vrsteImena.add(entry.getKey());
                }
            }
            System.out.printf("-- Razred %s: %d (%s)\n", razred, max,  String.join(", ", vrsteImena));
        }
    }
}
