package my_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

public class XY_Plugin implements PlugInFilter {

    ImagePlus im;   // instance variable of the plugin object

    public int setup(String args, ImagePlus image) {
        this.im = image;    // keep a reference to the image
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();


        for (int x = 0; x < w; x++) {
            for (int u = 0; u < w; u++) {
                for (int v = 0; v < h; v++) {

                    if (u < (w - 1)) {
                        int val1 = ip.getPixel(u, v);
                        int val2 = ip.getPixel(u + 1, v);

                        ip.putPixel(u, v, val2);
                        ip.putPixel(u + 1, v, val1);

                        im.updateAndDraw();
                    } else {
                        int val1 = ip.getPixel(u, v);
                        int val2 = ip.getPixel(0, v);

                        ip.putPixel(u, v, val2);
                        ip.putPixel(0, v, val1);

                        im.updateAndDraw();
                    }

                }
            }
        }

    }
}
