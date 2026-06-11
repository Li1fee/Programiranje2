package test_2020.prvi;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Naloga12 {
    private static int r = 255;
    private static int g = 255;
    private static int b = 255;
    private static int newR = 255;
    private static int newG = 255;
    private static int newB = 255;
    private static Color barvaSredinskegaOkna = new Color(r, g, b);

    public static void main(String[] args) {
        JFrame okno = new JFrame("Okno z datumom in miško");
        okno.setSize(500, 500);
        okno.setLocation(500, 500);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel sredinskoOkno = new JPanel();
        sredinskoOkno.setBackground(barvaSredinskegaOkna);


        JPanel zgornoOkno = new JPanel();
        zgornoOkno.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        JTextArea datumIzpis = new JTextArea();
        datumIzpis.setText("00.00.0000");
        datumIzpis.setBackground(new Color(200, 200, 200));
        datumIzpis.enableInputMethods(false);

        JButton datumGumb = new JButton("Datum");

        datumGumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd. MM. yyyy");
                String datum = sdf.format(new Date());
                datumIzpis.setText(datum);
            }
        });

        JButton barvaGumb = new JButton("Barva");

        barvaGumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random rng = new Random();
                do {
                    newR = rng.nextInt(0, 255);
                    newG = rng.nextInt(0, 255);
                    newB = rng.nextInt(0, 255);
                } while (r == newR && g == newG && b == newB);

                r = newR;
                g = newG;
                b = newB;
                barvaSredinskegaOkna = new Color(r, g, b);
                sredinskoOkno.setBackground(barvaSredinskegaOkna);
            }
        });

        zgornoOkno.add(datumGumb);
        zgornoOkno.add(datumIzpis);
        zgornoOkno.add(barvaGumb);

        JPanel spodnjeOkno = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JTextArea miskaKordinate = new JTextArea();
        miskaKordinate.setText("(0, 0)");
        miskaKordinate.enableInputMethods(false);

        sredinskoOkno.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                miskaKordinate.setText(String.format("(%d, %d)", e.getX(), e.getY()));
            }
        });

        spodnjeOkno.add(miskaKordinate);

        okno.add(zgornoOkno, BorderLayout.PAGE_START);
        okno.add(sredinskoOkno, BorderLayout.CENTER);
        okno.add(spodnjeOkno, BorderLayout.PAGE_END);
        okno.setVisible(true);
    }
}
