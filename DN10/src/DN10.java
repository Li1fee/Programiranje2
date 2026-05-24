import java.awt.Color;
import java.util.Random;

public class DN10 {
    public static void main(String[] args) {
        Random rng = new Random();

        double kotStopinje = 60.0;
        double kotRadiani = Math.toRadians(kotStopinje);

        Tocka[] originPoints = new Tocka[]{new Tocka(100, 100), new Tocka(800, 100), new Tocka(450, 706)};

        StdDraw.setCanvasSize(900, 800);
        StdDraw.setXscale(0, 900);
        StdDraw.setYscale(0, 800);

        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(originPoints[0].getX(), originPoints[0].getY());
        StdDraw.point(originPoints[1].getX(), originPoints[1].getY());
        StdDraw.point(originPoints[2].getX(), originPoints[2].getY());


        int x = rng.nextInt(1, 350);
        int nasprotna = (int) (x * Math.tan(kotRadiani));
        nasprotna = nasprotna == 0 ? 1 : nasprotna;

        int y = rng.nextInt(0, nasprotna) + 100;

        if (rng.nextInt(1, 3) == 2) {
            x = 700 - x;
        }
        x += 100;

        Tocka randomPoint = new Tocka(x, y);

        for (int i = 0; i < 10000; i++) {
            Tocka randomOriginPoint = originPoints[rng.nextInt(0, 3)];

            randomPoint = sredinskaPozicija(randomPoint, randomOriginPoint);

            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.008);
            StdDraw.point(randomPoint.getX(), randomPoint.getY());
        }
    }

    private static Tocka sredinskaPozicija(Tocka t1, Tocka t2) {
        return new Tocka((t1.getX() + t2.getX()) / 2,
                        (t1.getY() + t2.getY()) / 2);
    }
}