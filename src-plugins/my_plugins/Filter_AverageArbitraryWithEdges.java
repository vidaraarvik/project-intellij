package my_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Filter_AverageArbitraryWithEdges implements PlugInFilter {

    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    public void run(ImageProcessor orig) {
        int M = orig.getWidth();
        int N = orig.getHeight();

        // filter matrix of size (2K + 1) x (2L + 1)
        int[][] filter = {
                {0, 0, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 0},
                {0, 0, 1 ,1 ,1 ,0, 0}
        };

        double s = 1.0/23;  // sum of filter coefficients is 23

        int K = filter[0].length/2;
        int L = filter.length/2;

        ImageProcessor copy = orig.duplicate();



        for (int v = L; v <= N-L-1; v++) {
            for (int u = K; u <= M-K-1; u++) {
                // compute filter result for position (u, v)
                int sum = 0;
                for (int j = -L; j <= L; j++) {
                    for (int i = -K; i <= K; i++) {
                        int p = copy.getPixel(u+i, v+j);
                        int c = filter[j+L][i+K];
                        sum = sum + c * p;
                    }
                }
                int q = (int) Math.round(s * sum);
                if (q < 0) q = 0;
                if (q > 255) q = 255;
                orig.putPixel(u, v, q);
            }
        }

        for (int v = 0; v < N; v++) {
            for (int u = 0; u < M; u++) {
                if (v < L || v > N-L-1 || u < K || u > M-K-1) {
                    orig.putPixel(u, v, 0);
                }

            }
        }
    }
}   // end of class
