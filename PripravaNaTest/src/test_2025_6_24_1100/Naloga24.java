package test_2025_6_24_1100;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Naloga24 {
    private static File izbranaDatoteka = null;

    public static void main(String[] args) {
        JFrame okno = new JFrame();
        okno.setTitle("Urejevalnik");
        okno.setSize(500, 750);
        okno.setLocation(500, 500);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea urejanjeBesedila = new JTextArea();

        JPanel spodnjeOkno = new JPanel();
        spodnjeOkno.setLayout(new FlowLayout());
        spodnjeOkno.setBackground(new Color(200, 200, 200));

        JButton odpriGumb = new JButton("Odpri...");
        spodnjeOkno.add(odpriGumb);

        odpriGumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser izbiranjeDatotek = new JFileChooser();
                int odgvor = izbiranjeDatotek.showOpenDialog(null);

                if (odgvor == izbiranjeDatotek.APPROVE_OPTION) {
                    izbranaDatoteka = izbiranjeDatotek.getSelectedFile();
                }

                StringBuilder vsebina = new StringBuilder();
                try (Scanner sc = new Scanner(izbranaDatoteka)) {
                    while (sc.hasNextLine()) {
                        vsebina.append(sc.nextLine()).append("\n");
                    }
                } catch (FileNotFoundException E) {
                    return;
                }
                urejanjeBesedila.setText(vsebina.toString());
            }
        });

        JButton shraniGumb = new JButton("Shrani");
        spodnjeOkno.add(shraniGumb);

        shraniGumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (izbranaDatoteka == null) {
                    return;
                }

                try (PrintWriter pw = new PrintWriter(izbranaDatoteka)) {
                    pw.println(urejanjeBesedila.getText());
                } catch (FileNotFoundException E) {
                    return;
                }
            }
        });


        okno.add(urejanjeBesedila, BorderLayout.CENTER);
        okno.add(spodnjeOkno, BorderLayout.PAGE_END);
        okno.setVisible(true);
    }
}
