package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Frame_Plugin implements PlugInFilter {

    public int setup(String args, ImagePlus image) {
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();


        for (int u = 0; u < w; u++) {
            for (int v = 0; v < h; v++) {

                if (v < 10 || v > h - 11 || u < 10 || u > w - 11) {
                    ip.putPixel(u, v, 255);
                }

            }
        }
    }
}