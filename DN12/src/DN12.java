import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DN12 {
    public static void main(String[] args) {
        JFrame okno = new JFrame();

        okno.setTitle("VELIKE ČRKE");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okno.setLocation(200, 200);
        okno.setSize(750, 750);

        okno.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;

        JTextArea levoBesedilo = new JTextArea();
        levoBesedilo.setLineWrap(true);
        c.weightx = 0.45;
        c.gridx = 0;
        okno.add(levoBesedilo, c);

        JTextArea desnoBesedilo = new JTextArea();
        desnoBesedilo.setLineWrap(true);
        desnoBesedilo.setEditable(false);
        c.weightx = 0.45;
        c.gridx = 2;
        okno.add(desnoBesedilo, c);

        JButton gumb = new JButton("--> Pretvori");

        gumb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String besedilo = levoBesedilo.getText();
                String velikoBesedilo = besedilo.toUpperCase();
                desnoBesedilo.setText(velikoBesedilo);
            }
        });

        JPanel sredinskiPanel = new JPanel(new GridBagLayout());
        sredinskiPanel.add(gumb);
        c.weightx = 0.1;
        c.gridx = 1;
        okno.add(sredinskiPanel, c);

        okno.setVisible(true);
    }
}
