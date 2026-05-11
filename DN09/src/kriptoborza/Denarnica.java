package kriptoborza;

import java.util.Map;
import java.util.HashMap;

public class Denarnica {
    private String id;
    private Map<String, Double> stanja;

    public Denarnica(String id) {
        this.id = id;
        stanja = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public double stanje(String oznakaValute) {
        return stanja.getOrDefault(oznakaValute, 0.0);
    }

    public boolean dodaj(String oznakaValute, double znesek) {
        if (znesek <= 0) {
            return false;
        }
        stanja.put(oznakaValute, stanja.getOrDefault(oznakaValute, 0.0) + znesek);
        return true;
    }

    public boolean odstej(String oznakaValute, double znesek) {
        double trenutnoStanje = stanja.getOrDefault(oznakaValute, 0.0);
        if (znesek <= 0 || trenutnoStanje == 0) {
            return false;
        }

        double novoStanje = trenutnoStanje - znesek;

        if (novoStanje < 0) {
            return false;
        } else if (novoStanje == 0) {
            stanja.remove(oznakaValute);
        } else {
            stanja.put(oznakaValute, novoStanje);
        }

        return true;
    }

    public void izpisiStanje() {
        System.out.printf("Stanje denarnice %s:\n", id);
        for (Map.Entry<String, Double> stanje : stanja.entrySet()){
            System.out.printf(" - %s: %11.2f\n", stanje.getKey(), stanje.getValue());
        }
    }
}
