package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SobelEdgeDetection implements EdgeDetectionAlgorithm {

    private static final int MAX_RANGE_SIZE = 255;
    private final ImageAlgorithm grayscaleAlgorithm;

    public SobelEdgeDetection(ImageAlgorithm grayscaleAlgorithm) {
        this.grayscaleAlgorithm = grayscaleAlgorithm;
    }

    private int[][] horizontalSobel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        final int[][] horizontalSobelKernel = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
        };
        int[][] horizontalSobel = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gradient = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int indexX = x + dx;
                        int indexY = y + dy;
                        if (indexX < 0 || indexX >= width || indexY < 0 || indexY >= height) {
                            continue;
                        }
                        int pixel = image.getRaster().getSample(indexX, indexY, 0);
                        gradient += pixel * horizontalSobelKernel[dx + 1][dy + 1];
                    }
                }
                horizontalSobel[x][y] = gradient;
            }
        }

        return horizontalSobel;
    }

    private int[][] verticalSobel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        final int[][] verticalSobelKernel = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
        };
        int[][] verticalSobel = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gradient = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int indexX = x + dx;
                        int indexY = y + dy;
                        if (indexX < 0 || indexX >= width || indexY < 0 || indexY >= height) {
                            continue;
                        }
                        int pixel = image.getRaster().getSample(indexX, indexY, 0);
                        gradient += pixel * verticalSobelKernel[dx + 1][dy + 1];
                    }
                }
                verticalSobel[x][y] = gradient;
            }
        }

        return verticalSobel;
    }

    private void validateImage(BufferedImage image) {
        if (image == null) {
            throw new IllegalArgumentException("image is null");
        }
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        validateImage(image);
        BufferedImage grayscaleImage = grayscaleAlgorithm.process(image);

        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        BufferedImage edgeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int[][] horizontalSobel = horizontalSobel(grayscaleImage);
        int[][] verticalSobel = verticalSobel(grayscaleImage);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int gradientX = horizontalSobel[x][y];
                int gradientY = verticalSobel[x][y];
                int magnitude =
                    (int) Math.min(Math.sqrt(gradientX * gradientX + gradientY * gradientY), MAX_RANGE_SIZE);
                Color color = new Color(magnitude, magnitude, magnitude);

                edgeImage.setRGB(x, y, color.getRGB());
            }
        }

        return edgeImage;
    }

}
