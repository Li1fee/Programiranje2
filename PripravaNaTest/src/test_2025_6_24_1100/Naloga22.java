package test_2025_6_24_1100;

public class Naloga22 {
    public static void main(String[] args) {
        if (args.length != 2) return;
        Sklad sklad = new Sklad(Integer.parseInt(args[0]));

        for (char c : args[1].toCharArray()) {
            if (c == '-') {
                sklad.pop();
            } else {
                sklad.push(c);
            }
        }

        while (!sklad.isEmpty()) {
            System.out.print(sklad.pop());
        }
    }
}

interface SkladVmesnik {
    void push(char c);
    char pop();
    int size();
    boolean isEmpty();
}

class Sklad implements SkladVmesnik {
    private final char[] sklad;
    private int i = 0;

    @Override
    public void push(char c) {
        if (i >= sklad.length) {
            throw new RuntimeException("Sklad je poln");
        }
        sklad[i++] = c;
    }

    public Sklad(int n) {
        this.sklad = new char[n];
    }

    @Override
    public char pop() {
        if (i <= 0) {
            throw new RuntimeException("Sklad je prazen");
        }

        return sklad[--i];
    }

    @Override
    public int size() {
        return i;
    }

    @Override
    public boolean isEmpty() {
        return i == 0;
    }
}