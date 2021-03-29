package com.company;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DataManager {
    BufferedImage img;
    int[][] pixels;

    int width;
    int height;

    public DataManager(int a, int b) {
        width=a;
        height=b;
        pixels = new int [width][height];

    }

}
