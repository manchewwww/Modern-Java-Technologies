package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LuminosityGrayscale implements GrayscaleAlgorithm {

    private static final double LUMINOSITY_RED = 0.21;
    private static final double LUMINOSITY_BLUE = 0.07;
    private static final double LUMINOSITY_GREEN = 0.72;

    public LuminosityGrayscale() {
    }

    private void validateImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        validateImage(image);
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rGB = image.getRGB(x, y);
                Color color = new Color(rGB);
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();

                int luminosity = (int) (LUMINOSITY_RED * r + LUMINOSITY_GREEN * g + LUMINOSITY_BLUE * b);

                Color newColor = new Color(luminosity, luminosity, luminosity);
//                Color newColor = new Color(luminosity);
                newImage.setRGB(x, y, newColor.getRGB());
            }
        }

        return newImage;
    }

}
