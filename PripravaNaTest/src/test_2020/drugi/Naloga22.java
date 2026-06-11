package test_2020.drugi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Naloga22 {
    public static void main(String[] args) {
        JFrame okno = new JFrame("100 gumbov");
        okno.setSize(1000, 1000);
        okno.setLocation(750, 250);
        okno.setLayout(new GridLayout(10, 10, 5, 5));
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton[] vsiGumbi = new JButton[100];
        Random rng = new Random();

        for (int i = 0; i < 100; i++) {
            JButton stevilkaGumb = new JButton(Integer.toString(i));
            vsiGumbi[i] = stevilkaGumb;

            stevilkaGumb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton kliknjenGumb = (JButton) e.getSource();

                    String tekstKliknjenega = kliknjenGumb.getText();
                    System.out.println(tekstKliknjenega);

                    int randomStevilka;
                    JButton randomGumb;

                    do {
                        randomStevilka = rng.nextInt(0, 100);
                        randomGumb = vsiGumbi[randomStevilka];
                    } while (kliknjenGumb == randomGumb);

                    String tekstRandom = randomGumb.getText();

                    kliknjenGumb.setText(tekstRandom);
                    randomGumb.setText(tekstKliknjenega);
                }
            });

            okno.add(stevilkaGumb);
        }

        okno.setVisible(true);
    }
}
