package test_2020.drugi;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UstvariTestnoDatoteko {
    public static void main(String[] args) {
        String imeDatoteke = "./src/test_2020/drugi/medved.bin";

        // DataOutputStream dela ravno obratno: spremenljivke spremeni v bajte in jih zapiše
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(imeDatoteke))) {

            // 1. vrstica iz primera
            dos.writeInt(2009); dos.writeInt(5); dos.writeInt(23);
            dos.writeInt(22); dos.writeInt(12);
            dos.writeFloat(46.495098f); dos.writeFloat(15.508233f);

            // 2. vrstica iz primera
            dos.writeInt(2009); dos.writeInt(5); dos.writeInt(23);
            dos.writeInt(22); dos.writeInt(12);
            dos.writeFloat(46.495216f); dos.writeFloat(15.508200f);

            // 3. vrstica iz primera
            dos.writeInt(2009); dos.writeInt(5); dos.writeInt(23);
            dos.writeInt(22); dos.writeInt(12);
            dos.writeFloat(46.495251f); dos.writeFloat(15.508200f);

            // 4. vrstica iz primera
            dos.writeInt(2009); dos.writeInt(5); dos.writeInt(23);
            dos.writeInt(22); dos.writeInt(13); // Spremenjena minuta na 13
            dos.writeFloat(46.495716f); dos.writeFloat(15.507850f);

            System.out.println("Uspšno ustvarjena binarna datoteka: " + imeDatoteke);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}