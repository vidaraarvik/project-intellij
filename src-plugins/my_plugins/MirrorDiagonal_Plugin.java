package my_plugins;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class MirrorDiagonal_Plugin implements PlugInFilter {

    ImagePlus im;   // instance variable of the plugin object

    public int setup(String args, ImagePlus image) {
        this.im = image;    // keep a reference to the image
        return DOES_8G;
    }

    public void run(ImageProcessor ip) {
        int w = ip.getWidth();
        int h = ip.getHeight();

        ImageProcessor ip2 = new ByteProcessor(h, w);
        ImagePlus im2 = new ImagePlus("Test", ip2);

        for (int u = 0; u < w; u++) {
            for (int v = 0; v < h; v++) {
                int val = ip.getPixel(u, v);

                ip2.putPixel(h - v, u, val);
            }
        }

        im2.show();
    }
}
