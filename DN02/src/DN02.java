public class DN02 {
    public static void main(String[] args) {
        String besedilo = args[0];
        int n = (int) Math.ceil(Math.sqrt(besedilo.length()));

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int charPointer = n * i + j;

                System.out.print(" ");
                if (charPointer >= besedilo.length()) {
                    System.out.print(". ");
                } else {
                    System.out.print(besedilo.charAt(charPointer) + " ");
                }
            }
            System.out.println();
        }

    }
}