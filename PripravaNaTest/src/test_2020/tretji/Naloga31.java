package test_2020.tretji;

public class Naloga31 {
    public static void main(String[] args) {
        if (args.length != 2) return;

        System.out.println("Najdaljsi skupni podniz: " + new Naloga31().poisciUjemanje(args[0], args[1]));
    }

    private String poisciUjemanje(String prvi, String drugi) {
        for (int i = prvi.length(); i > 0; i--) {
            for (int j = 0; j < prvi.length() - i; j++) {
                String podNiz = prvi.substring(j, i + j);
                if (drugi.contains(podNiz)) {
                    return podNiz;
                }
            }
        }
        return "";
    }
}
