package bg.sofia.uni.fmi.mjt.imagekit.algorithm.grayscale;

import bg.sofia.uni.fmi.mjt.imagekit.algorithm.ImageAlgorithm;
import bg.sofia.uni.fmi.mjt.imagekit.algorithm.detection.SobelEdgeDetection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LuminosityGrayscaleTest {

    ImageAlgorithm algorithm;

    @BeforeEach
    void setUp() {
        algorithm = new LuminosityGrayscale();
    }

    @Test
    void testProcessWithNullImage() {
        assertThrows(IllegalArgumentException.class, () -> algorithm.process(null),
            "Process luminosity should trow IllegalArgumentException with null image");
    }

    @Test
    void testProcessImageWithMockBufferedImage() {
        BufferedImage mockImage = mock(BufferedImage.class);

        int width = 2;
        int height = 2;

        when(mockImage.getWidth()).thenReturn(width);
        when(mockImage.getHeight()).thenReturn(height);

        when(mockImage.getRGB(0, 0)).thenReturn(new Color(100, 150, 200).getRGB());
        when(mockImage.getRGB(0, 1)).thenReturn(new Color(50, 50, 50).getRGB());
        when(mockImage.getRGB(1, 0)).thenReturn(new Color(255, 0, 0).getRGB());
        when(mockImage.getRGB(1, 1)).thenReturn(new Color(0, 255, 0).getRGB());

        BufferedImage result = algorithm.process(mockImage);

        verify(mockImage, times(width * height)).getRGB(anyInt(), anyInt());

        int expectedLuminosity00 = (int) (0.21 * 100 + 0.72 * 150 + 0.07 * 200);
        int expectedLuminosity01 = (int) (0.21 * 50 + 0.72 * 50 + 0.07 * 50);
        int expectedLuminosity10 = (int) (0.21 * 255 + 0.72 * 0 + 0.07 * 0);
        int expectedLuminosity11 = (int) (0.21 * 0 + 0.72 * 255 + 0.07 * 0);

        Color expectedColor00 = new Color(expectedLuminosity00, expectedLuminosity00, expectedLuminosity00);
        Color expectedColor01 = new Color(expectedLuminosity01, expectedLuminosity01, expectedLuminosity01);
        Color expectedColor10 = new Color(expectedLuminosity10, expectedLuminosity10, expectedLuminosity10);
        Color expectedColor11 = new Color(expectedLuminosity11, expectedLuminosity11, expectedLuminosity11);

        assertEquals(expectedColor00.getRGB(), result.getRGB(0, 0), "Calculating index 0, 0 is incorrect in luminosity grayscale");
        assertEquals(expectedColor01.getRGB(), result.getRGB(0, 1), "Calculating index 0, 1 is incorrect in luminosity grayscale");
        assertEquals(expectedColor10.getRGB(), result.getRGB(1, 0), "Calculating index 1, 0 is incorrect in luminosity grayscale");
        assertEquals(expectedColor11.getRGB(), result.getRGB(1, 1), "Calculating index 1, 1 is incorrect in luminosity grayscale");
    }

}
