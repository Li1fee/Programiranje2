package aplikacija;

import banka.Banka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Upravljanje {

    // Iz podane datoteke prebere podatke o računih in ustvari račune v podani banki.
    private static void dodajRacune(String vir, Banka banka) throws FileNotFoundException {
        Scanner vhod = new Scanner(new File(vir));
        while (vhod.hasNextLine()) {
            String[] podatki = vhod.nextLine().split(";");
            if (podatki[0].equalsIgnoreCase("tekoci")) { // ustvari tekoči račun
                banka.dodajTekociRacun(podatki[1], Double.parseDouble(podatki[2]));
            } else { // ustvari varčevalni račun
                banka.dodajVarcevalniRacun(podatki[1], Double.parseDouble(podatki[2]));
            }
            banka.polog(podatki[1], Double.parseDouble(podatki[3]));
        }
        vhod.close();
    }

    public static void main(String[] args) throws FileNotFoundException {

        // ustvarimo novo banko
        Banka bankaFRI = new Banka();

        // v banki naredimo račune z določenimi stanji, vse podatke preberemo iz datoteke
        dodajRacune("src/racuni.txt", bankaFRI);

        bankaFRI.izpisiRacune();

        System.out.println("--- TEKOČI RAČUNI ---");
        bankaFRI.izpisiRacune(false);


        String stevilkaRacuna = "SI56 1234 4321 1234 126"; // tekoči z limitom 100 €
        // String stevilkaRacuna = "SI56 7823 4563 8346 123"; // varčevalni z 0,1% obrestmi

        System.out.println("Dodajam obresti...");
        bankaFRI.dodajObresti();
        bankaFRI.izpisiRacune(true); // Izpiši varčevalne, da vidiš razliko


        // Uredi po stanju padajoče (najbogatejši najprej)
        bankaFRI.sortirajRacune("stanje", true);
        bankaFRI.izpisiRacune();

        // Uredi po številki računa naraščajoče (po abecedi)
        bankaFRI.sortirajRacune("stevilka", false);
        bankaFRI.izpisiRacune();
    }
}