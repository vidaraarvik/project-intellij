package my_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class RandomImage_Plugin implements PlugInFilter {

    public int setup(String args, ImagePlus image) {
        return NO_IMAGE_REQUIRED;
    }

    public void run(ImageProcessor ip) {
        int w = 256;
        int h = 100;

        ip = new ByteProcessor(w, h);
        ip.setValue(255);
        for (int u = 0; u < w; u++) {
            for (int v = 0; v < h; v++) {
                ip.putPixel(u, v, (int) (Math.random() * 255));
            }
        }
        ImagePlus img = new ImagePlus("RandomPixels", ip);
        img.show();

        int[] hist = ip.getHistogram();

        ImageProcessor histIp = new ByteProcessor(w, h);
        histIp.setValue(255);   // white = 255
        histIp.fill();  // clear this image

        for (int u = 0; u < w; u++) {
            if (hist[u] > 0) {
                for (int v = h; v > (h - hist[u]); v--) {
                    histIp.putPixel(u, v, 0);
                }
            }
        }

        // display the histogram image
        String hTitle = "Histogram";
        ImagePlus histIm = new ImagePlus(hTitle, histIp);
        histIm.show();
        // histIm.updateAndDraw();

    }
} // end of class