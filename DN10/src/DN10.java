import java.awt.Color;
import java.util.Random;

public class DN10 {
    public static void main(String[] args) {
        Random rng = new Random();

        double kotStopinje = 60.0;
        double kotRadiani = Math.toRadians(kotStopinje);

        int[][] originPoints = new int[][]{{100, 100}, {800, 100}, {450, 706}};

        StdDraw.setCanvasSize(900, 800);
        StdDraw.setXscale(0, 900);
        StdDraw.setYscale(0, 800);

        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(originPoints[0][0], originPoints[0][1]);
        StdDraw.point(originPoints[1][0], originPoints[1][1]);
        StdDraw.point(originPoints[2][0], originPoints[2][1]);


        int x = rng.nextInt(1, 350);
        int nasprotna = (int) (x * Math.tan(kotRadiani));
        nasprotna = nasprotna == 0 ? 1 : nasprotna;

        int y = rng.nextInt(0, nasprotna) + 100;

        if (rng.nextInt(1, 3) == 2) {
            x = 700 - x;
        }
        x += 100;

        int[] randomPoint = new int[]{x, y};

        for (int i = 0; i < 10000; i++) {
            int[] randomOriginPoint = originPoints[rng.nextInt(0, 3)];

            int novX = (randomPoint[0] + randomOriginPoint[0]) / 2;
            int novY = (randomPoint[1] + randomOriginPoint[1]) / 2;

            randomPoint = new int[]{novX, novY};

            StdDraw.setPenColor(Color.black);
            StdDraw.setPenRadius(0.008);
            StdDraw.point(randomPoint[0], randomPoint[1]);
        }
    }
}