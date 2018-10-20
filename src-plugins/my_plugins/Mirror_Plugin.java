package my_plugins;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class Mirror_Plugin implements PlugInFilter {

    public int setup(String args, ImagePlus image) {
        return DOES_ALL;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();


        for (int u = 0; u < (w / 2); u++) {
            for (int v = 0; v < h; v++) {
                int val1 = ip.getPixel(u, v);
                int val2 = ip.getPixel(w - u, v);

                ip.putPixel(u, v, val2);
                ip.putPixel(w - u, v, val1);
            }
        }
    }
}
