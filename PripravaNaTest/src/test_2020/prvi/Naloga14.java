package test_2020.prvi;

import java.net.BindException;
import java.util.ArrayList;

public class Naloga14 {
    static char [][] krizanka = {
            {'D', 'F', 'T', 'O', 'P'},
            {'L', 'I', 'P', 'A', 'M'},
            {'C', 'K', 'O', 'L', 'T'},
            {'F', 'E', 'S', 'G', 'T'},
            {'S', 'S', 'M', 'E', 'T'}
    };
    static String [] besede =
            {"LOPAR","SONCE","KOL","KOLO","TOP","LIPA","SMET","ORODJE"};
    public static void main(String[] args) {
        System.out.printf(
            "Prva najdaljša beseda se nahaja na lokaciji %s\n",
            poisciNajdaljso(krizanka, besede)
        );
    }

    private static Lokacija poisciNajdaljso(char [][] krizanka, String [] besede) {
        Lokacija najdaljsaPrvaBeseda = new Lokacija('a', -1);
        int dolzinaNajdaljseBesede = 0;

        for (int i = 0; i < krizanka.length; i++) {
            for (String beseda : besede) {
                int x = 0;
                if (beseda.length() <= dolzinaNajdaljseBesede) continue;
                for (int j = 0; j < krizanka[i].length; j++) {
                    if (krizanka[i][j] == beseda.charAt(x)) {
                        x++;
                        if (x == beseda.length()) {
                            najdaljsaPrvaBeseda = new Lokacija((char) (97 + i), j - x + 2);
                            dolzinaNajdaljseBesede = beseda.length();
                            break;
                        }
                    } else if (krizanka[i][j] == beseda.charAt(0)) {
                        x = 1;
                    } else {
                        x = 0;
                    }
                }
            }
        }
        return najdaljsaPrvaBeseda.getStolpec() == -1 ? null : najdaljsaPrvaBeseda;
    }
}

class Lokacija {
    private char vrstica;
    private int stolpec;

    public Lokacija(char vrstica, int stolpec) {
        this.vrstica = vrstica;
        this.stolpec = stolpec;
    }

    public int getStolpec() {
        return stolpec;
    }

    public char getVrstica() {
        return vrstica;
    }

    @Override
    public String toString() {
        return String.format("(%s, %d)", vrstica, stolpec);
    }
}
