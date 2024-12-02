package bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SobelEdgeDetectionTest {

    private SobelEdgeDetection sobelEdgeDetection;
    private ImageAlgorithm mockGrayscaleAlgorithm;

    @BeforeEach
    void setUp() {
        // Mock the grayscale algorithm
        mockGrayscaleAlgorithm = mock(ImageAlgorithm.class);
        sobelEdgeDetection = new SobelEdgeDetection(mockGrayscaleAlgorithm);
    }

    @Test
    void testProcessWithNullImage() {
        assertThrows(IllegalArgumentException.class, () -> sobelEdgeDetection.process(null),
            "Processing a null image should throw IllegalArgumentException");
    }

    @Test
    void testProcessWithMockedBufferedImage() {
        BufferedImage mockInputImage = mock(BufferedImage.class);
        BufferedImage mockGrayscaleImage = mock(BufferedImage.class);
        WritableRaster mockRaster = mock(WritableRaster.class);

        int width = 3;
        int height = 3;

        when(mockInputImage.getWidth()).thenReturn(width);
        when(mockInputImage.getHeight()).thenReturn(height);
        when(mockGrayscaleAlgorithm.process(mockInputImage)).thenReturn(mockGrayscaleImage);
        when(mockGrayscaleImage.getWidth()).thenReturn(width);
        when(mockGrayscaleImage.getHeight()).thenReturn(height);
        when(mockGrayscaleImage.getRaster()).thenReturn(mockRaster);

        when(mockRaster.getSample(0, 0, 0)).thenReturn(10);
        when(mockRaster.getSample(1, 0, 0)).thenReturn(20);
        when(mockRaster.getSample(2, 0, 0)).thenReturn(30);

        when(mockRaster.getSample(0, 1, 0)).thenReturn(40);
        when(mockRaster.getSample(1, 1, 0)).thenReturn(50);
        when(mockRaster.getSample(2, 1, 0)).thenReturn(60);

        when(mockRaster.getSample(0, 2, 0)).thenReturn(70);
        when(mockRaster.getSample(1, 2, 0)).thenReturn(80);
        when(mockRaster.getSample(2, 2, 0)).thenReturn(90);

        BufferedImage result = sobelEdgeDetection.process(mockInputImage);

        assertNotNull(result, "Resulting image should not be null");
        assertEquals(width, result.getWidth(), "Resulting image width should match input width");
        assertEquals(height, result.getHeight(), "Resulting image height should match input height");

        int expectedMagnitude = 252;
        Color centerPixelColor = new Color(result.getRGB(1, 1));
        assertEquals(expectedMagnitude, centerPixelColor.getRed(),
            "The gradient magnitude at the center pixel should match the expected value");
    }

}
