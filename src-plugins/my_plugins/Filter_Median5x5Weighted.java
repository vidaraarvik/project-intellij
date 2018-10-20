package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filter_Median5x5Weighted implements PlugInFilter {
    int[][] matrix = {  // filter matrix of size (2fw + 1) x (2fh + 1)
            {1, 1, 1, 1, 1},
            {1, 2, 2, 2, 1},
            {1, 2, 3, 2, 1},
            {1, 2, 2, 2, 1},
            {1, 1, 1 ,1 ,1}
    };
    int fw = matrix[0].length/2;
    int fh = matrix.length/2;

    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        filter(orig, orig.getWidth(), orig.getHeight());
    }

    private void filter(ImageProcessor orig, int w, int h) {
        ImageProcessor copy = orig.duplicate();
        List<Integer> P = new ArrayList();  //vector to hold pixels from 5x5 neighborhood

        for (int v = fh; v <= h-fh-1; v++) {
            for (int u = fw; u <= w-fw-1; u++) {
                // fill the pixel vector P for filter position u,v
                for (int j = -fh; j <= fh; j++) {
                    for (int i = -fw; i <= fw; i++) {

                        for (int y = 0; y < matrix[j+fh][i+fw]; y++) {
                            P.add(copy.getPixel(u+i, v+j));
                        }

                    }
                }
                // sort pixel vector and take the center element
                Collections.sort(P);
                orig.putPixel(u, v, P.get(P.size()/2));
                P = new ArrayList();
            }
        }
    }
}   // end of class
