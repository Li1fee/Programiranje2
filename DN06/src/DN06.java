public class DN06 {
    public static void main(String[] args) {
        String sudokuPoljaStevilke = args[0];

        int poljeSize = 900;
        int celicaSize = poljeSize / 9;
        int margin = 100;

        StdDraw.setCanvasSize(1100, 1100);
        StdDraw.setXscale(0, 1100);
        StdDraw.setYscale(0, 1100);
        StdDraw.clear();

        int leva = margin;
        int desna = margin + poljeSize;
        int zgoraj = margin + poljeSize;
        int spodaj = margin;

        StdDraw.setPenRadius(0.002);
        for (int i = 0; i < 9; i++) {
            int x = leva + i * celicaSize;
            int y = spodaj + i * celicaSize;
            StdDraw.line(x, spodaj, x, zgoraj);
            StdDraw.line(leva, y, desna, y);
        }

        StdDraw.setPenRadius(0.006);
        for (int i = 0; i <= 9; i += 3) {
            int x = leva + i * celicaSize;
            int y = spodaj + i * celicaSize;
            StdDraw.line(x, spodaj, x, zgoraj);
            StdDraw.line(leva, y, desna, y);
        }

        StdDraw.setFont(StdDraw.getFont().deriveFont(36f));
        for (int i = 0; i < 81; i++) {
            char c = sudokuPoljaStevilke.charAt(i);
            if (c == '0') continue;

            int row = i / 9;
            int col = i % 9;

            double x = leva + col * celicaSize + celicaSize / 2.0;
            double y = zgoraj - row * celicaSize - celicaSize / 2.0;

            StdDraw.text(x, y, String.valueOf(c));
        }
    }
}