package test_2025_6_12_1000;

public class Naloga11 {

    public static void main(String[] args) {
        if (args.length != 1) return;
        String args1 = args[0];
        System.out.println(new Naloga11().pretvoriZnake(args1));
    }

    int pretvoriZnak(String znak) {
        int stevilo = 0;

        for (int i = 0; i < znak.length(); i++) {
            char c = znak.charAt(i);
            if (c == '1') {
                stevilo += (int) Math.pow(2, znak.length() - i - 1);
            }
        }
        return stevilo;
    }

    String pretvoriZnake(String znaki) {
        StringBuffer res = new StringBuffer();
        if (znaki.length() % 8 != 0) return null;

        for (int i = 0; i < znaki.length(); i += 8) {
            res.append((char) pretvoriZnak(znaki.substring(i, i + 8)));
        }
        return res.toString();
    }
}
