package my_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.Arrays;

public class Filter_Median3x3 implements PlugInFilter {
    final int K = 4;    // filter size

    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        int w = orig.getWidth();
        int h = orig.getHeight();
        ImageProcessor copy = orig.duplicate();

        //vector to hold pixels from 3x3 neighborhood
        int[] P = new int[2*K+1];

        for (int v = 1; v <= h-2; v++) {
            for (int u = 1; u <= w-2; u++) {
                // fill the pixel vector P for filter position u,v
                int k = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        P[k] = copy.getPixel(u+i, v+j);
                        k++;
                    }
                }
                // sort pixel vector and take the center element
                Arrays.sort(P);
                orig.putPixel(u, v, P[K]);
            }
        }
    }
}   // end of class
