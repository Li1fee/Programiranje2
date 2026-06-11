import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Naloga13 {
    public static void main(String[] args) {
        if (args.length != 1) return;

        String imeDatoteke = args[0];

        File datoteka = new File(imeDatoteke);

        int idatStevec = 0;
        long idatSkupnaDolzina = 0;

        if (!datoteka.exists() || !jePNG(imeDatoteke)) {
            System.out.printf("Datoteka '%s' ni veljavna PNG datoteka.", datoteka.getName());
            return;
        }

         try (DataInputStream ds = new DataInputStream(new FileInputStream(imeDatoteke))) {
            ds.skipBytes(8);
            while (ds.available() > 0) {
                int dolzina = ds.readInt();

                byte[] tipBajti = new byte[4];
                ds.readFully(tipBajti);
                String tipKosa = new String(tipBajti);

                if (tipKosa.equals("IDAT")) {
                    idatStevec++;
                    idatSkupnaDolzina += dolzina;
                }

                ds.skipBytes(dolzina);
                ds.skipBytes(4);

                if (tipKosa.equals("IEND")) {
                    break;
                }
            }
         } catch (Exception e) {
             return;
         }

        System.out.printf("Datoteka '%s' vsebuje %d IDAT kosov v skupni dolzini %d", datoteka.getName(), idatStevec, idatSkupnaDolzina);
    }

    private static boolean jePNG(String imeDatoteke) {
        int[] prefix = new int[]{0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};

        try (DataInputStream ds = new DataInputStream(new FileInputStream(imeDatoteke))) {
            for (int p : prefix) {
                int bajt = ds.readUnsignedByte();
                if (bajt != p) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
