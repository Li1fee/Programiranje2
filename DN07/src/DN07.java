import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DN07 {
    public static void main(String[] args) throws FileNotFoundException {
        String imeDatoteke = args[0];
        String iskaniPlanetiString = args[1];
        String[] iskaniPlaneti = iskaniPlanetiString.split("\\+");
        Planet[] vsiPlaneti = preberiPlanete(imeDatoteke);
        double skupnaPovrsinaPlanetov = 0;

        for (String ime : iskaniPlaneti) {
            for (Planet p : vsiPlaneti) {
                if (p.getIme().equalsIgnoreCase(ime)) {
                    skupnaPovrsinaPlanetov += p.povrsina() / 1e6;
                }
            }
        }
        System.out.printf("Povrsina planetov \"%s\" je %d milijonov km2", iskaniPlanetiString, (int) skupnaPovrsinaPlanetov);
    }

    static boolean praviPlanet(String imePlaneta, String[] imePosameznegaPlaneta) {
        for (String ime : imePosameznegaPlaneta) {
            if (ime.toLowerCase().equals(imePlaneta)) {
                return true;
            }
        }
        return false;
    }

    static Planet[] preberiPlanete(String imeDatoteke) throws FileNotFoundException {
        Planet[] planeti = new Planet[8];
        Scanner sc = new Scanner(new File(imeDatoteke));

        for (int i = 0; i < 8; i++) {
            String[] podatkiPlaneta = sc.nextLine().split(":");
            Planet planet = new Planet(podatkiPlaneta[0], Integer.parseInt(podatkiPlaneta[1]));
            planeti[i] = planet;
        }
        sc.close();
        return planeti;
    }

}

class Planet {
    private String ime;
    private int radij;

    public Planet(String ime, int radij) {
        this.ime = ime;
        this.radij = radij;
    }

    public String getIme() {
        return ime;
    }

    public double povrsina() {
        return 4 * Math.PI * Math.pow(radij, 2);
    }

}

