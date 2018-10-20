package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class AutoContrast_Plugin implements PlugInFilter {

    public int setup(String args, ImagePlus image) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();
        int M = w * h;  // number of image pixels
        int K = 256;    // number of intensity values
        int histoHeight = 100;
        int scale = M/histoHeight;

        double q = 0.2;    // quantile of pixels to be saturated



        int aLow = 0;
        int aHigh = 0;
        int aMin = 0;
        int aMax = 255;







        // compute the comulative histogram
        int[] H = ip.getHistogram();
        for (int j = 1; j < H.length; j++) {
            H[j] = H[j-1] + H[j];
        }



        for (int j = 0; j < H.length; j++) {
            if (aLow == 0 && H[j] >= M * q) {
                aLow = j;
            }

            if (H[j] <= M * (1 - q)) {
                aHigh = j;
            }
        }
        IJ.log("aLow=" + aLow + " aHigh=" + aHigh);






        for (int u = 0; u < w; u++) {
            for (int v = 0; v < h; v++) {
                int a = ip.get(u, v);

                if (a <= aLow) {
                    ip.set(u, v, (int) aMin);
                } else if (a >= aHigh) {
                    ip.set(u, v, (int) aMax);
                } else {
                    int val = (int) (aMin + (a - aLow) * (aMax - aMin)/(aHigh - aLow));
                    ip.set(u, v, val);
                }
            }
        }


        // compute the comulative histogram again
        //(after contrast adjustment)
        H = ip.getHistogram();
        for (int j = 1; j < H.length; j++) {
            H[j] = H[j-1] + H[j];
        }




        ImageProcessor histIp = new ByteProcessor(K, histoHeight);
        histIp.setValue(255);   // white = 255
        histIp.fill();  // clear this image

        histIp.setValue(0);
        for (int i = 0; i < K; i++) {
            histIp.drawLine(i, histoHeight, i, histoHeight - (H[i] / scale));
        }

        // display the histogram image
        String hTitle = "C-Histogram";
        ImagePlus histIm = new ImagePlus(hTitle, histIp);
        histIm.show();
        // histIm.updateAndDraw();
    }
}
