public class Naloga11 {
    public static void main(String[] args) {
        if (args.length != 4) return;
        String enkripcija = args[0];
        String vrsta = args[1];
        String geslo = args[2];
        String besedilo = args[3];

        String res = "";

        if (enkripcija.equals("cezar")) {
            if (vrsta.equals("code")) {
                res = cezarCode(besedilo, Integer.parseInt(geslo));
            } else {
                res = cezarDecode(besedilo, Integer.parseInt(geslo));
            }
        } else {
            res = xorCodeDecode(besedilo, geslo);
        }

        System.out.println(res);
    }


    private static String cezarCode(String besedilo, int odmik) {
        StringBuilder code = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            code.append((char) (c + odmik));
        }

        return code.toString();
    }

    private static String cezarDecode(String besedilo, int odmik) {
        StringBuilder decode = new StringBuilder();

        for (char c : besedilo.toCharArray()) {
            decode.append((char) (c - odmik));
        }

        return decode.toString();
    }

    private static String xorCodeDecode(String besedilo, String geslo) {
        StringBuilder codeDecode = new StringBuilder();
        int i = 0;
        for (char c : besedilo.toCharArray()) {
            codeDecode.append((char) (c ^ geslo.charAt(i++ % geslo.length())));
        }

        return codeDecode.toString();
    }
}
