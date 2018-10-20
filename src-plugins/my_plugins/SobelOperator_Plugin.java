package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;


public class SobelOperator_Plugin implements PlugInFilter {
    int[][] fX = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    int[][] fY = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    int fw = fX[0].length/2;
    int fh = fX.length/2;

    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        filter(orig, orig.getWidth(), orig.getHeight());
    }

    private void filter(ImageProcessor orig, int w, int h) {
        ImageProcessor copy = orig.duplicate();

        for (int v = fh; v <= h-fh-1; v++) {
            for (int u = fw; u <= w-fw-1; u++) {
                int gx = 0,
                    gy = 0;
                for (int j = -fh; j <= fh; j++) {
                    for (int i = -fw; i <= fw; i++) {
                        int p = copy.getPixel(u+i, v+j);
                        gx += p * fX[j+fh][i+fw];
                        gy += p * fY[j+fh][i+fw];
                    }
                }
                double gVal = Math.sqrt((gx * gx) + (gy * gy));
//                double gOrient = Math.atan2(gy, gx);



                int g = (int) gVal;




                orig.putPixel(u, v, g);
            }
        }
    }
}   // end of class
