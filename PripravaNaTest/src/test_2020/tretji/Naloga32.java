package test_2020.tretji;

import java.util.ArrayList;
import java.util.Arrays;

public class Naloga32 {
    public static void main(String[] args) {
        int a = 32837969;
        int b = 15259182;

        int[] prvaRisba = new int[25];
        int[] drugaRisba = new int[25];

        for (int i = 0; i < 25; i++) {
            int maska = 1 << i;
            prvaRisba[24 - i] = (a & maska) >> i;
        }

        for (int i = 0; i < 25; i++) {
            int maska = 1 << i;
            drugaRisba[24 - i] = (b & maska) >> i;
        }


//        Comented out because of the pacake problems with the StdDraw it works otherwise
//        StdDraw.setCanvasSize(500, 500);
//        StdDraw.setXscale(0, 5);
//        StdDraw.setYscale(0, 5);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (prvaRisba[i * 5 + j] == 1 && drugaRisba[i * 5 + j] == 1) {
//                    StdDraw.setPenColor(0, 0,0);
                } else if (prvaRisba[i * 5 + j] == 1) {
//                    StdDraw.setPenColor(255, 0,0);
                } else if (drugaRisba[i * 5 + j] == 1) {
//                    StdDraw.setPenColor(0, 255,0);
                } else {
//                    StdDraw.setPenColor(255, 255,255);
                }
//                StdDraw.filledSquare(j + 0.5,  5 - i - 0.5, 0.5);
            }
        }
    }
}