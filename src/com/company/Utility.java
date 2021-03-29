package com.company;

import java.util.Random;

public class Utility {
    DataManager dm;

    public Utility(DataManager dm){
        this.dm = dm;
    }

    int[] neighbours(int width, int height) {
        int[] neighbours = new int[8];
        int k=0;
        for (int i = width-1; i <= width+1; i++) {
            for (int j = height-1; j <= height+1; j++) {
                if(i==width && j==height)
                    continue;
                else{
                    neighbours[k]=dm.pixels[i][j];
                    k++;
                }
            }
        }
        return neighbours;
    }

    void gameOfLife() {
        for (int i = 0; i < dm.width; i++) {
            for (int j = 0; j < dm.height; j++) {
                dm.pixels[i][j] = dm.img.getRaster().getSample(i, j, 0);
            }
        }

        while (true) {
            for (int i = 1; i < dm.height - 1; i++) {
                for (int j = 1; j < dm.width - 1; j++) {

                    int[] middlePixelNeighbour = neighbours(j, i);

                    int middlePixel = dm.img.getRaster().getSample(j, i, 0);

                    int alive = 0;

                    switch(middlePixel){
                        case 0:
                            for (int k : middlePixelNeighbour) {
                                if (k == 0)
                                    alive++;
                            }
                            if (alive == 3 || alive == 2)
                                continue;
                            else if (alive > 3 || alive == 0 || alive == 1)
                                dm.img.getRaster().setSample(j, i, 0, 255);
                            break;
                        case 255:
                            for (int l : middlePixelNeighbour) {
                                if (l == 0)
                                    alive++;
                            }
                            if (alive == 3)
                                dm.img.getRaster().setSample(j, i, 0, 0);
                            break;
                    }
                }
            }
            for (int m = 0; m < dm.width; m++) {
                for (int n = 0; n < dm.height; n++) {
                    dm.pixels[m][n] = dm.img.getRaster().getSample(m, n, 0);
                }
            }
        }
    }

    void generateRandom() {
        Random random=new Random();
        for (int i = 0; i < dm.width; i++) {
            for (int j = 0; j < dm.height; j++) {
                if (random.nextInt((256-0)+1)+0 < 128)
                    dm.img.getRaster().setSample(i, j, 0, 0);
                else
                    dm.img.getRaster().setSample(i, j, 0, 255);
            }
        }
    }
}
