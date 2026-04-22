public class DN08 {
    public static void main(String[] args) {
        Lik[] liki = new Lik[args.length];

        for (int i = 0; i < args.length; i++) {
            String[] deli = args[i].split(":");
            String vrsta = deli[0];

            if (vrsta.equals("kvadrat")) {
                int a = Integer.parseInt(deli[1]);
                liki[i] = new Kvadrat(a);
            } else if (vrsta.equals("pravokotnik")) {
                int a = Integer.parseInt(deli[1]);
                int b = Integer.parseInt(deli[2]);
                liki[i] = new Pravokotnik(a, b);
            } else {
                int n = Integer.parseInt(deli[1]);
                int a = Integer.parseInt(deli[2]);
                liki[i] = new NKotnik(n, a);
            }
        }

        double rezultat = skupniObseg(liki);
        System.out.println((int) rezultat);
    }

    static double skupniObseg(Lik[] tabela) {
        double vsota = 0;
        for (Lik lik : tabela) {
            vsota += lik.obseg();
        }
        return vsota;
    }
}

abstract class Lik {
    abstract public double obseg();
}

class Kvadrat extends Lik {
    private int aDolzina;

    public Kvadrat(int aDolzina) {
        this.aDolzina = aDolzina;
    }

    public double obseg() {
        return 4 * aDolzina;
    }
}

class Pravokotnik extends Lik {
    private int aDolzina;
    private int bDolzina;

    public Pravokotnik(int aDolzina, int bDolzina) {
        this.aDolzina = aDolzina;
        this.bDolzina = bDolzina;
    }

    public double obseg() {
        return 2 * aDolzina + 2 * bDolzina;
    }
}

class NKotnik extends Lik {
    private int steviloStranic;
    private int dolzinaStranice;

    public NKotnik(int steviloStranic, int dolzinaStranice) {
        this.steviloStranic = steviloStranic;
        this.dolzinaStranice = dolzinaStranice;
    }

    public double obseg() {
        return steviloStranic * dolzinaStranice;
    }
}