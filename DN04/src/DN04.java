public class DN04 {
    public static void main(String[] args) {
        String sporocilo = args[0];
        StringBuilder pretvorbaSporocila = new StringBuilder();

        for (int i = 0; i < sporocilo.length(); i+=8) {
            String delSporocila = sporocilo.substring(i, i + 8);
            char crka = (char) Integer.parseInt(delSporocila, 2);
            pretvorbaSporocila.append(crka);
        }

        System.out.println(pretvorbaSporocila);
    }
}