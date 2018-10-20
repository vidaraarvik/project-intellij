package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class CumulativeHistogram_Plugin implements PlugInFilter {

    public int setup(String args, ImagePlus image) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();
        int M = w * h;  // number of image pixels
        int K = 256;    // number of intensity values
        int histoHeight = 100;
        int scale = M / histoHeight;

        // compute the comulative histogram
        int[] H = ip.getHistogram();
        for (int j = 1; j < H.length; j++) {
            H[j] = H[j - 1] + H[j];

//            IJ.log("" + H[j]);
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
